package moria.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "transactions")
public class Transaction implements Serializable {

    @Id
    private String id;
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
    private int categoryId;
    private BigDecimal transactionFee;
    private boolean transactionFeeCanceled;
    @Embedded
    private TransactionAdditionalInfoDomestic additionalInfoDomestic;
    @Embedded
    private TransactionAdditionalInfoForeign additionalInfoForeign;
    @Embedded
    private TransactionAdditionalInfoCard additionalInfoCard;

    public Transaction() {
    }

    public Transaction(String id, int accountId, TransactionValue value, TransactionPartyAccount partyAccount, String partyDescription, String direction, String transactionType, Date valueDate, Date bookingDate, String userDescription, String payerMessage, String payeeMessage, int categoryId, BigDecimal transactionFee, boolean transactionFeeCanceled, TransactionAdditionalInfoDomestic additionalInfoDomestic, TransactionAdditionalInfoForeign additionalInfoForeign, TransactionAdditionalInfoCard additionalInfoCard) {
        this.id = id;
        this.accountId = accountId;
        this.value = value;
        this.partyAccount = partyAccount;
        this.partyDescription = partyDescription;
        this.direction = direction;
        this.transactionType = transactionType;
        this.valueDate = valueDate;
        this.bookingDate = bookingDate;
        this.userDescription = userDescription;
        this.payerMessage = payerMessage;
        this.payeeMessage = payeeMessage;
        this.categoryId = categoryId;
        this.transactionFee = transactionFee;
        this.transactionFeeCanceled = transactionFeeCanceled;
        this.additionalInfoDomestic = additionalInfoDomestic;
        this.additionalInfoForeign = additionalInfoForeign;
        this.additionalInfoCard = additionalInfoCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public TransactionValue getValue() {
        return value;
    }

    public void setValue(TransactionValue value) {
        this.value = value;
    }

    public TransactionPartyAccount getPartyAccount() {
        return partyAccount;
    }

    public void setPartyAccount(TransactionPartyAccount partyAccount) {
        this.partyAccount = partyAccount;
    }

    public String getPartyDescription() {
        return partyDescription;
    }

    public void setPartyDescription(String partyDescription) {
        this.partyDescription = partyDescription;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public String getPayerMessage() {
        return payerMessage;
    }

    public void setPayerMessage(String payerMessage) {
        this.payerMessage = payerMessage;
    }

    public String getPayeeMessage() {
        return payeeMessage;
    }

    public void setPayeeMessage(String payeeMessage) {
        this.payeeMessage = payeeMessage;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(BigDecimal transactionFee) {
        this.transactionFee = transactionFee;
    }

    public boolean isTransactionFeeCanceled() {
        return transactionFeeCanceled;
    }

    public void setTransactionFeeCanceled(boolean transactionFeeCanceled) {
        this.transactionFeeCanceled = transactionFeeCanceled;
    }

    public TransactionAdditionalInfoDomestic getAdditionalInfoDomestic() {
        return additionalInfoDomestic;
    }

    public void setAdditionalInfoDomestic(TransactionAdditionalInfoDomestic additionalInfoDomestic) {
        this.additionalInfoDomestic = additionalInfoDomestic;
    }

    public TransactionAdditionalInfoForeign getAdditionalInfoForeign() {
        return additionalInfoForeign;
    }

    public void setAdditionalInfoForeign(TransactionAdditionalInfoForeign additionalInfoForeign) {
        this.additionalInfoForeign = additionalInfoForeign;
    }

    public TransactionAdditionalInfoCard getAdditionalInfoCard() {
        return additionalInfoCard;
    }

    public void setAdditionalInfoCard(TransactionAdditionalInfoCard additionalInfoCard) {
        this.additionalInfoCard = additionalInfoCard;
    }
}
