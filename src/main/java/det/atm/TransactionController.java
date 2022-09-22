package det.atm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

// import java.util.ArrayList;
// import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// import javax.persistence.Entity;


@RestController
@RequestMapping("/api/")
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
    @GetMapping("/transactions/{id}")
    EntityModel<Transaction> getTransaction(@PathVariable Long id, Long accountId) {
        accountRepository.findById(accountId)
            .orElseThrow(() -> new AccountNotFoundException(accountId));

        Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new TransactionNotFoundException(id));

        if (transaction.getId() != accountId) {
            throw new TransactionNotFoundException(id);
        }

        return transactionAssembler.toModel(transaction);
    }

    // Get all transactions for a specific account
    // @GetMapping("/transactions") 
    // CollectionModel<EntityModel<Transaction>> getAccountTransactions(@PathVariable Long id) {
    //     Account account = accountRepository.findById(id)
    //         .orElseThrow(() -> new AccountNotFoundException(id));

    //     ArrayList<EntityModel<Transaction>> transactions = new ArrayList<EntityModel<Transaction>>();

    //     ArrayList<Long> trs = account.getTransactions();
    //     Iterator<Long> iterator = trs.iterator();
    //     while(iterator.hasNext()) {
    //         Long num = iterator.next();
    //         Transaction t = transactionRepository.findById(num)
    //             .orElseThrow(() -> new TransactionNotFoundException(num));
            
    //         if (t.getId() != id) {
    //             throw new TransactionNotFoundException(num);
    //         }
                
    //         transactions.add(transactionAssembler.toModel(t));
            
    //     }
        
    //     return CollectionModel.of(transactions, 
    //         linkTo(methodOn(TransactionController.class).getAccountTransactions(id)).withSelfRel());
    // }


    // Get all transaction items
    // Don't think we need this, if all transactions are tied to an account
    @GetMapping("/transactions") 
    CollectionModel<EntityModel<Transaction>> getAllTransactions() {
        List<EntityModel<Transaction>> transactions = transactionRepository.findAll().stream()
            .map(transactionAssembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(transactions, 
            linkTo(methodOn(TransactionController.class).getAllTransactions()).withSelfRel());
    }


    // Add a new transaction and update the corresponding account balance and transactions list
    @PostMapping("/transactions")
    ResponseEntity<EntityModel<Transaction>> newTransaction(@RequestBody String t) {
        Object o1 = JSONValue.parse(t);
        JSONObject jsonObj = (JSONObject) o1;
        String type = (String) jsonObj.get("type");
        Integer a = (Integer) jsonObj.get("amount");
        Double amount = Double.valueOf(a);
        Integer i = (Integer) jsonObj.get("id");
        Long id = Long.valueOf(i);
        Transaction.Type tt;
        switch(type) {
            case "deposit": 
                tt = Transaction.Type.DEPOSIT;
                break;
            case "withdrawal":
                tt = Transaction.Type.WITHDRAWAL;
                break;
            default:
                throw new TransactionTypeError();
        }

        Transaction transaction = new Transaction(id, tt, amount);

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
