package moria.model;

public class Transaction {

    private String id;
    private String accountId;
    private TransactionValue value;
    private TransactionPartyAccount partyAccount;
    private String partyDescription;
    private String direction;
    private String transactionType;
    private String valueDate;
    private String bookingDate;
    private String userDescription;
    private String payerMessage;
    private String payeeMessage;
    private String categoryId;
    private String transactionFee;
    private String transactionFeeCanceled;
    private TransactionAdditionalInfoDomestic additionalInfoDomestic;
    private TransactionAdditionalInfoForeign additionalInfoForeign;
    private TransactionAdditionalInfoCard additionalInfoCard;

    public Transaction() {
    }

    public Transaction(String id, String accountId, TransactionValue value, TransactionPartyAccount partyAccount, String partyDescription, String direction, String transactionType, String valueDate, String bookingDate, String userDescription, String payerMessage, String payeeMessage, String categoryId, String transactionFee, String transactionFeeCanceled, TransactionAdditionalInfoDomestic additionalInfoDomestic, TransactionAdditionalInfoForeign additionalInfoForeign, TransactionAdditionalInfoCard additionalInfoCard) {
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
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

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(String transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getTransactionFeeCanceled() {
        return transactionFeeCanceled;
    }

    public void setTransactionFeeCanceled(String transactionFeeCanceled) {
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
