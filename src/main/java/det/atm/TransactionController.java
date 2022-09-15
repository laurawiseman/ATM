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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// import java.util.stream.Collectors;

// import javax.persistence.Entity;


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
    // Throws an error if the transaction does not exist, if the associated account does not exist, or if the association is incorrect
    @GetMapping("/transactions/{num}")
    EntityModel<Transaction> getTransaction(@PathVariable Long num, Long id) {
        accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        Transaction transaction = transactionRepository.findById(num)
            .orElseThrow(() -> new TransactionNotFoundException(num));

        if (transaction.getId() != id) {
            throw new TransactionNotFoundException(num);
        }

        return transactionAssembler.toModel(transaction);
    }

    // Get all transactions for a specific account
    @GetMapping("/transactions") 
    CollectionModel<EntityModel<Transaction>> getAccountTransactions(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        List<EntityModel<Transaction>> transactions = new ArrayList<EntityModel<Transaction>>();

        List<Long> trs = account.getTransactions();
        Iterator<Long> iterator = trs.iterator();
        while(iterator.hasNext()) {
            Long num = iterator.next();
            Transaction t = transactionRepository.findById(num)
                .orElseThrow(() -> new TransactionNotFoundException(num));
            
            if (t.getId() != id) {
                throw new TransactionNotFoundException(num);
            }
                
            transactions.add(transactionAssembler.toModel(t));
            
        }
        
        // List<EntityModel<Transaction>> transactions = account.getTransactions().stream()
        //     .map(transactionAssembler::toModel)
        //     .collect(Collectors.toList());
        
        return CollectionModel.of(transactions, 
            linkTo(methodOn(TransactionController.class).getAccountTransactions(id)).withSelfRel());
    }


    // Get all transaction items
    // Don't think we need this, if all transactions are tied to an account
    // @GetMapping("/transactions") 
    // CollectionModel<EntityModel<Transaction>> getAllTransactions() {
    //     List<EntityModel<Transaction>> transactions = transactionRepository.findAll().stream()
    //         .map(transactionAssembler::toModel)
    //         .collect(Collectors.toList());
        
    //     return CollectionModel.of(transactions, 
    //         linkTo(methodOn(TransactionController.class).getAllTransactions()).withSelfRel());
    // }


    // Add a new transaction and update the corresponding account balance and transactions list
    @PutMapping("/transactions")
    ResponseEntity<EntityModel<Transaction>> newTransaction(@RequestBody Transaction transaction, Long id) {
        if (!(transaction.getType() instanceof Transaction.Type)) 
            throw new TransactionTypeError();

        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        account.setBalance(transaction.getType(), transaction.getAmount());
        account.addTransaction(transaction.getNum());

        Transaction newTransaction = transactionRepository.save(transaction);

        return ResponseEntity
            .created(linkTo(methodOn(TransactionController.class).getTransaction(newTransaction.getNum(), id)).toUri())
            .body(transactionAssembler.toModel(newTransaction));
    }

}
