package moria.model.transactions;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class TransactionAdditionalInfoForeign implements Serializable {

    @Embedded
    private TransactionForeignOriginalValue originalValue;
    private BigDecimal exchangeRate;

    public TransactionAdditionalInfoForeign() {
    }

    public TransactionAdditionalInfoForeign(TransactionForeignOriginalValue originalValue, BigDecimal exchangeRate) {
        this.originalValue = originalValue;
        this.exchangeRate = exchangeRate;
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
