package det.atm;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/api/")
class AccountController {
    private final AccountRepository accountRepository;
    private final AccountModelAssembler assembler;

    AccountController(AccountRepository accountRepository, AccountModelAssembler assembler) {
        this.accountRepository = accountRepository;
        this.assembler = assembler;
    }

    // Get all accounts
    @GetMapping("/accounts")
    CollectionModel<EntityModel<Account>> getAllAccounts() {
        List<EntityModel<Account>> accounts = accountRepository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(accounts, 
            linkTo(methodOn(AccountController.class).getAllAccounts()).withSelfRel());
    }

    // Get a particular users account 
    // Note: In this scenario, there is no reason for a user to get the full list of all accounts in our DB
    @GetMapping("/accounts/{id}")
    EntityModel<Account> getAccount(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new AccountNotFoundException(id));

        return assembler.toModel(account);
    }

    // Create a brand new account, setting the initial balance to 0 with no initial transactions
    // Assigns an id to the new account
    @PostMapping("/accounts") 
    ResponseEntity<EntityModel<Account>> newAccount(@RequestBody String name) {
        ArrayList<Long> lst = new ArrayList<Long>();
        Account newAccount = new Account(name, 0D, lst);

        EntityModel<Account> entityModel = assembler.toModel(accountRepository.save(newAccount));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    // Update the name of an account, or create a new one if the account does not exist
    // This does not update the balance or transactions of an account
    // if a new account is created, it is initialized to 0 balance with no transactions
    @PutMapping("/accounts/{id}")
    ResponseEntity<?> updateAccount (@RequestBody String newName, @PathVariable Long id) {
        Account updatedAccount = accountRepository.findById(id)
            .map(account -> { 
                account.setName(newName);
                return accountRepository.save(account);
            })
            .orElseGet(() -> {
                ArrayList<Long> lst = new ArrayList<Long>();
                Account newAccount = new Account(newName, 0D, lst);
                newAccount.setId(id);
                return accountRepository.save(newAccount);
            });
        
        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    // Delete an existing account
    @DeleteMapping("/accounts/{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
