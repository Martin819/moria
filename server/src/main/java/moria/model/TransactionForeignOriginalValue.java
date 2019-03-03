package moria.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class TransactionForeignOriginalValue implements Serializable {

    @Column(name = "foreignAmount")
    private BigDecimal amount;
    @Column(name = "foreignCurrency")
    private String currency;

    public TransactionForeignOriginalValue() {
    }

    public TransactionForeignOriginalValue(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
