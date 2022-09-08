package det.atm;

class TransactionNotFoundException extends RuntimeException {
    TransactionNotFoundException(Long num) {
        super("Could not find transaction " + num);
    }
}
