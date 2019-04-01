package moria.model.transactions;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Data
@SuppressWarnings("DefaultAnnotationParam")
@Entity(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements Serializable {

    @Id
    @SerializedName("_id")
    private String id;
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
    private Integer categoryId;
    private BigDecimal transactionFee;
    @Column(nullable = true)
    private Boolean transactionFeeCanceled;
    @Embedded
    private TransactionAdditionalInfoDomestic additionalInfoDomestic;
    @Embedded
    private TransactionAdditionalInfoForeign additionalInfoForeign;
    @Embedded
    private TransactionAdditionalInfoCard additionalInfoCard;
    private Boolean isCategoryManuallyAssigned;
    private String parentId;
    private BigDecimal originalValue;
    @Transient
    private Map<Integer, BigDecimal> categories;

}

