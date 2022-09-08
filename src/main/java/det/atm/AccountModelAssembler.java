package det.atm;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
class AccountModelAssembler implements RepresentationModelAssembler<Account, EntityModel<Account>> {
    @Override
    public EntityModel<Account> toModel(Account account) {
        return EntityModel.of(account, 
            linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
            // linkTo(methodOn(AccountController.class).all()).withRel("accounts")); // keep this?? 
    }
}
