package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAdditionalInfoForeign implements Serializable {

    @Embedded
    private TransactionForeignOriginalValue originalValue;
    private BigDecimal exchangeRate;

}
