package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAdditionalInfoDomestic implements Serializable {

    private String constantSymbol;
    private String variableSymbol;
    private String specificSymbol;

}
