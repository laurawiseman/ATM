package det.atm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Objects;


@Entity
class Transaction {
    enum Type {
        DEPOSIT, 
        WITHDRAWAL
    }

    private @Id @GeneratedValue Long num;
    private Long id; //Account id number
    private Transaction.Type type; 
    private Double amount;

    // Transaction(Long num, Long id, Type type, Double amount) {
    //     this.num = num;
    //     this.id = id;
    //     this.type = type;
    //     this.amount = amount;
    // }

    Transaction(Long id, Type type, Double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Long getNum() {
        return this.num;
    }

    public Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public void setAccountId(Long id) {
        this.id = id;
    }

    // This sets both the type (withdrawal or deposit) and amount together 
    public void setTransaction(Type type, Double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction transaction = (Transaction) o;
        return Objects.equals(this.num, transaction.num) && Objects.equals(this.id, transaction.id) && Objects.equals(this.type, transaction.type) && this.amount == transaction.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.num, this.id, this.type, this.amount);
    }

    @Override
    public String toString() {
        return "Transaction{" + "account=" + this.id + "transaction number=" + this.num + ", type='" + this.type + '\'' + ", amount=$" + String.format("%.2f", this.amount) + '}';
    }
}
