package moria.dto;

import lombok.Data;
import moria.model.transactions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ParentTransaction {

    private String id;

    private int accountId;

    private TransactionValue value;

    private TransactionPartyAccount partyAccount;
    private String partyDescription;
    private String direction;
    private String transactionType;

    private Date valueDate;

    private Date bookingDate;
    private String userDescription;
    private String payerMessage;
    private String payeeMessage;

    private Integer categoryId;
    private BigDecimal transactionFee;

    private Boolean transactionFeeCanceled;

    private TransactionAdditionalInfoDomestic additionalInfoDomestic;

    private TransactionAdditionalInfoForeign additionalInfoForeign;

    private TransactionAdditionalInfoCard additionalInfoCard;
    private Boolean isCategoryManuallyAssigned;
    private String parentId;
    private BigDecimal originalValue;

    private List<ChildTransaction> childTransactionsList;

    public ParentTransaction(Transaction transaction, List<ChildTransaction> categories) {
        id = transaction.getId();
        accountId = transaction.getAccountId();
        value = transaction.getValue();

        partyAccount = transaction.getPartyAccount();
        partyDescription = transaction.getPartyDescription();
        direction = transaction.getDirection();
        transactionType = transaction.getTransactionType();
        valueDate = transaction.getValueDate();
        bookingDate = transaction.getBookingDate();
        userDescription = transaction.getUserDescription();
        payerMessage = transaction.getPayerMessage();
        payeeMessage = transaction.getPayeeMessage();
        categoryId = transaction.getCategoryId();
        transactionFee =transaction.getTransactionFee();
        transactionFeeCanceled = transaction.getTransactionFeeCanceled();
        additionalInfoDomestic = transaction.getAdditionalInfoDomestic();
        additionalInfoForeign = transaction.getAdditionalInfoForeign();
        additionalInfoCard = transaction.getAdditionalInfoCard();
        isCategoryManuallyAssigned = transaction.getIsCategoryManuallyAssigned();
        parentId = transaction.getParentId();
        originalValue = transaction.getOriginalValue();
        childTransactionsList = categories;

    }
}
