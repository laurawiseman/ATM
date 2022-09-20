package det.atm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;


@Entity
class Account {
    private @Id @GeneratedValue Long id;
    private String name;
    private Double balance;
    private ArrayList<Long> transactions; 

    Account() {} 

    Account(String name, Double balance, ArrayList<Long> transactions) {
        this.name = name;
        this.balance = balance;
        this.transactions = transactions;
    }

    // Account(Long id, String name, Double balance, List<Transaction> transactions) { 
    //     this.id = id;
    //     this.name = name;
    //     this.balance = balance;
    //     this.transactions = transactions;
    // }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBalance() {
        return "$" + String.format("%.2f", this.balance);
    }

    public ArrayList<Long> getTransactions() {
        return this.transactions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstBalance() {
        this.balance = 0D;
    }

    public void setBalance(Transaction.Type type, Double amount) {
        switch(type) {
            case DEPOSIT:
                this.balance += amount;
                break;
            case WITHDRAWAL:
                this.balance -= amount;
                break;
            default:
                throw new TransactionTypeError();
        }
    }

    public void addTransaction(Long transaction) {
        this.transactions.add(transaction);
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;
        return Objects.equals(this.id, account.id) && Objects.equals(this.name, account.name) && (this.balance == account.balance) && (this.transactions == account.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.balance, this.transactions);
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + this.id + ", name='" + this.name + '\'' + ", balance=$" + String.format("%.2f", this.balance) + ", transactions=" + this.transactions + '}';
    }
}
