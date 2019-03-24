package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TransactionValue {

    private BigDecimal amount;
    private String currency;

}
