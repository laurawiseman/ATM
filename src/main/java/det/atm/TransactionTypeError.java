package det.atm;

class TransactionTypeError extends RuntimeException {
    TransactionTypeError() {
        super("Not a valid transaction type. Transactions may be DEPOSIT or WITHDRAWAL");
    }
}
