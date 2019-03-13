package moria.model.transactions;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@SuppressWarnings("DefaultAnnotationParam")
@Entity(name = "transactions")
public class Transaction implements Serializable {

    @Id
    private int id;
    @Column(nullable = true)
    private int accountId;
    @Embedded
    private TransactionValue value;
    @Embedded
    private TransactionPartyAccount partyAccount;
    private String partyDescription;
    private String direction;
    private String transactionType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDate;
    private String userDescription;
    private String payerMessage;
    private String payeeMessage;
    @Column(nullable = true)
    private int categoryId;
    private BigDecimal transactionFee;
    @Column(nullable = true)
    private boolean transactionFeeCanceled;
    @Embedded
    private TransactionAdditionalInfoDomestic additionalInfoDomestic;
    @Embedded
    private TransactionAdditionalInfoForeign additionalInfoForeign;
    @Embedded
    private TransactionAdditionalInfoCard additionalInfoCard;
}
