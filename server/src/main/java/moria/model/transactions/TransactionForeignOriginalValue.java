package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Embeddable
public class TransactionForeignOriginalValue implements Serializable {

    @Column(name = "foreignAmount")
    private BigDecimal amount;
    @Column(name = "foreignCurrency")
    private String currency;

}
