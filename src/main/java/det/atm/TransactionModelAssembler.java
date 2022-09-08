package det.atm;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
class TransactionModelAssembler implements RepresentationModelAssembler<Transaction, EntityModel<Transaction>> {
    @Override
    public EntityModel<Transaction> toModel(Transaction transaction) {
        Long id = transaction.getId();
        
        EntityModel<Transaction> transactionModel = EntityModel.of(transaction, 
            linkTo(methodOn(AccountController.class).getAccount(id)).withSelfRel());

        transactionModel.add(linkTo(methodOn(TransactionController.class).getTransaction(transaction.getNum())).withSelfRel());
        transactionModel.add(linkTo(methodOn(TransactionController.class).getAllTransactions()).withRel("transactions"));
        transactionModel.add(linkTo(methodOn(TransactionController.class).getAccountTransactions(id)).withRel("accounts/{id}/transactions")); // is this correct? 

        return transactionModel;
    };
}
