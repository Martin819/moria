package moria.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class TransactionAdditionalInfoForeign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private TransactionForeignOriginalValue originalValue;
    private BigDecimal exchangeRate;

    public TransactionAdditionalInfoForeign() {
    }

    public TransactionAdditionalInfoForeign(int id, TransactionForeignOriginalValue originalValue, BigDecimal exchangeRate) {
        this.id = id;
        this.originalValue = originalValue;
        this.exchangeRate = exchangeRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int it) {
        this.id = it;
    }

    public TransactionForeignOriginalValue getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(TransactionForeignOriginalValue originalValue) {
        this.originalValue = originalValue;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
