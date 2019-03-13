package moria.model.transactions;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class TransactionAdditionalInfoCard implements Serializable {

    private String mcc;
    private String merchantName;
    private String cardNumber;

}
