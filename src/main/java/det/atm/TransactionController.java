package det.atm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
class TransactionController {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionModelAssembler transactionAssembler;

    TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionModelAssembler transactionAssembler) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionAssembler = transactionAssembler;
    }

    // Get single transaction item
    @GetMapping("/transactions/{num}") // unsure abt this ??? 
    EntityModel<Transaction> getTransaction(@PathVariable Long num) {
        Transaction transaction = transactionRepository.findById(num)
            .orElseThrow(() -> new TransactionNotFoundException(num));

        return transactionAssembler.toModel(transaction);
    }

    // Get all transactions for a specific account
    // should this be based off of the account id or transaction num?? i think account
    @GetMapping("/accounts/{id}/transactions") // what would this "" be ???
    CollectionModel<EntityModel<Transaction>> getAccountTransactions(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        List<EntityModel<Transaction>> transactions = account.getTransactions().stream()
            .map(transactionAssembler::toModel)
            .collect(Collectors.toList());
        // needed??
        
        return CollectionModel.of(transactions, 
            linkTo(methodOn(TransactionController.class).getAccountTransactions(id)).withSelfRel());
    }


    // Get all transaction items
    @GetMapping("/transactions") 
    CollectionModel<EntityModel<Transaction>> getAllTransactions() {
        List<EntityModel<Transaction>> transactions = transactionRepository.findAll().stream()
            .map(transactionAssembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(transactions, 
            linkTo(methodOn(TransactionController.class).getAllTransactions()).withSelfRel());
    }


    // Add a new transaction to the transactions repository and update the corresponding account balance 
    @PutMapping("/transactions")
    ResponseEntity<EntityModel<Transaction>> newTransaction(@RequestBody Transaction transaction) {
        if (!(transaction.getType() instanceof Transaction.Type)) 
            throw new TransactionTypeError();

        Account account = accountRepository.findById(transaction.getId())
            .orElseThrow(() -> new AccountNotFoundException(transaction.getId()));

        account.setBalance(transaction.getType(), transaction.getAmount());
        account.addTransaction(transaction);

        Transaction newTransaction = transactionRepository.save(transaction);

        return ResponseEntity
            .created(linkTo(methodOn(TransactionController.class).getTransaction(newTransaction.getNum())).toUri())
            .body(transactionAssembler.toModel(newTransaction));
    }

}
