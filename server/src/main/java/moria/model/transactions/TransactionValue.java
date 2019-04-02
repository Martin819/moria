package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TransactionValue implements Cloneable {

    private BigDecimal amount;
    private String currency;


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
