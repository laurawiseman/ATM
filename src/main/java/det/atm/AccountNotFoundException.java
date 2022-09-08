package det.atm;

class AccountNotFoundException extends RuntimeException {
    AccountNotFoundException(Long id) {
        super("Could not find account " + id);
    }
}
