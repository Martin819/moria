package moria.model.transactions;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TransactionPartyAccount {

    @Column(name = "partyPrefix")
    private String prefix;
    @Column(name = "partyAccountNumber")
    private String accountNumber;
    @Column(name = "partyBankCode")
    private String bankCode;

}
