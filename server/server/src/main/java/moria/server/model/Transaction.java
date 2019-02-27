package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import moria.server.model.TransactionAdditionalInfoCard;
import moria.server.model.TransactionAdditionalInfoDomestic;
import moria.server.model.TransactionAdditionalInfoForeign;
import moria.server.model.TransactionPartyAccount;
import moria.server.model.TransactionValue;
import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class Transaction   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("accountId")
  private BigDecimal accountId = null;

  @JsonProperty("value")
  private TransactionValue value = null;

  @JsonProperty("partyAccount")
  private TransactionPartyAccount partyAccount = null;

  @JsonProperty("partyDescription")
  private String partyDescription = null;

  /**
   * transaction direction
   */
  public enum DirectionEnum {
    INCOMING("INCOMING"),
    
    OUTGOING("OUTGOING"),
    
    BOTH("BOTH");

    private String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("direction")
  private DirectionEnum direction = null;

  /**
   * transaction type
   */
  public enum TransactionTypeEnum {
    PAYMENT_HOME("PAYMENT_HOME"),
    
    PAYMENT_ABROAD("PAYMENT_ABROAD"),
    
    PAYMENT_PERSONAL("PAYMENT_PERSONAL"),
    
    PAYMENT_ACCOUNT("PAYMENT_ACCOUNT"),
    
    STANDING_ORDER("STANDING_ORDER"),
    
    SAVING("SAVING"),
    
    DIRECT_DEBIT("DIRECT_DEBIT"),
    
    DIRECT_DEBIT_SIPO("DIRECT_DEBIT_SIPO"),
    
    CARD("CARD"),
    
    CASH("CASH"),
    
    FEE("FEE"),
    
    TAX("TAX"),
    
    INTEREST("INTEREST"),
    
    INSURANCE("INSURANCE"),
    
    LOAN("LOAN"),
    
    MORTGAGE("MORTGAGE"),
    
    SAZKA("SAZKA"),
    
    OTHER("OTHER"),
    
    BLOCKING("BLOCKING");

    private String value;

    TransactionTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TransactionTypeEnum fromValue(String text) {
      for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("transactionType")
  private TransactionTypeEnum transactionType = null;

  @JsonProperty("valueDate")
  private OffsetDateTime valueDate = null;

  @JsonProperty("bookingDate")
  private OffsetDateTime bookingDate = null;

  @JsonProperty("userDescription")
  private String userDescription = null;

  @JsonProperty("payerMessage")
  private String payerMessage = null;

  @JsonProperty("payeeMessage")
  private String payeeMessage = null;

  @JsonProperty("categoryId")
  private BigDecimal categoryId = null;

  @JsonProperty("transactionFee")
  private BigDecimal transactionFee = null;

  @JsonProperty("transactionFeeCanceled")
  private Boolean transactionFeeCanceled = null;

  @JsonProperty("additionalInfoDomestic")
  private TransactionAdditionalInfoDomestic additionalInfoDomestic = null;

  @JsonProperty("additionalInfoForeign")
  private TransactionAdditionalInfoForeign additionalInfoForeign = null;

  @JsonProperty("additionalInfoCard")
  private TransactionAdditionalInfoCard additionalInfoCard = null;

  public Transaction id(String id) {
    this.id = id;
    return this;
  }

  /**
   * internal transaction identified
   * @return id
  **/
  @ApiModelProperty(required = true, value = "internal transaction identified")
  @NotNull

@Size(min=0) 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Transaction accountId(BigDecimal accountId) {
    this.accountId = accountId;
    return this;
  }

  /**
   * account to that transaction belongs (to which it is accounted)
   * @return accountId
  **/
  @ApiModelProperty(required = true, value = "account to that transaction belongs (to which it is accounted)")
  @NotNull

  @Valid

  public BigDecimal getAccountId() {
    return accountId;
  }

  public void setAccountId(BigDecimal accountId) {
    this.accountId = accountId;
  }

  public Transaction value(TransactionValue value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TransactionValue getValue() {
    return value;
  }

  public void setValue(TransactionValue value) {
    this.value = value;
  }

  public Transaction partyAccount(TransactionPartyAccount partyAccount) {
    this.partyAccount = partyAccount;
    return this;
  }

  /**
   * Get partyAccount
   * @return partyAccount
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionPartyAccount getPartyAccount() {
    return partyAccount;
  }

  public void setPartyAccount(TransactionPartyAccount partyAccount) {
    this.partyAccount = partyAccount;
  }

  public Transaction partyDescription(String partyDescription) {
    this.partyDescription = partyDescription;
    return this;
  }

  /**
   * party description
   * @return partyDescription
  **/
  @ApiModelProperty(value = "party description")

@Size(min=0) 
  public String getPartyDescription() {
    return partyDescription;
  }

  public void setPartyDescription(String partyDescription) {
    this.partyDescription = partyDescription;
  }

  public Transaction direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * transaction direction
   * @return direction
  **/
  @ApiModelProperty(required = true, value = "transaction direction")
  @NotNull


  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public Transaction transactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * transaction type
   * @return transactionType
  **/
  @ApiModelProperty(required = true, value = "transaction type")
  @NotNull


  public TransactionTypeEnum getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
  }

  public Transaction valueDate(OffsetDateTime valueDate) {
    this.valueDate = valueDate;
    return this;
  }

  /**
   * transaction value date, e.g. the day transaction \"happened\"
   * @return valueDate
  **/
  @ApiModelProperty(required = true, value = "transaction value date, e.g. the day transaction \"happened\"")
  @NotNull

  @Valid

  public OffsetDateTime getValueDate() {
    return valueDate;
  }

  public void setValueDate(OffsetDateTime valueDate) {
    this.valueDate = valueDate;
  }

  public Transaction bookingDate(OffsetDateTime bookingDate) {
    this.bookingDate = bookingDate;
    return this;
  }

  /**
   * transaction booking date, e.g. the day transaction was bookkeeped
   * @return bookingDate
  **/
  @ApiModelProperty(required = true, value = "transaction booking date, e.g. the day transaction was bookkeeped")
  @NotNull

  @Valid

  public OffsetDateTime getBookingDate() {
    return bookingDate;
  }

  public void setBookingDate(OffsetDateTime bookingDate) {
    this.bookingDate = bookingDate;
  }

  public Transaction userDescription(String userDescription) {
    this.userDescription = userDescription;
    return this;
  }

  /**
   * user transaction description
   * @return userDescription
  **/
  @ApiModelProperty(value = "user transaction description")

@Size(min=0) 
  public String getUserDescription() {
    return userDescription;
  }

  public void setUserDescription(String userDescription) {
    this.userDescription = userDescription;
  }

  public Transaction payerMessage(String payerMessage) {
    this.payerMessage = payerMessage;
    return this;
  }

  /**
   * message for payer. Empty for incoming transacionts.
   * @return payerMessage
  **/
  @ApiModelProperty(value = "message for payer. Empty for incoming transacionts.")

@Size(min=0) 
  public String getPayerMessage() {
    return payerMessage;
  }

  public void setPayerMessage(String payerMessage) {
    this.payerMessage = payerMessage;
  }

  public Transaction payeeMessage(String payeeMessage) {
    this.payeeMessage = payeeMessage;
    return this;
  }

  /**
   * message for payee (e.g. for client receiving transaction)
   * @return payeeMessage
  **/
  @ApiModelProperty(value = "message for payee (e.g. for client receiving transaction)")

@Size(min=0) 
  public String getPayeeMessage() {
    return payeeMessage;
  }

  public void setPayeeMessage(String payeeMessage) {
    this.payeeMessage = payeeMessage;
  }

  public Transaction categoryId(BigDecimal categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * id of category for transaction. More info about category can be retrieved using /openapi/banking/categories resource.
   * @return categoryId
  **/
  @ApiModelProperty(value = "id of category for transaction. More info about category can be retrieved using /openapi/banking/categories resource.")

  @Valid

  public BigDecimal getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(BigDecimal categoryId) {
    this.categoryId = categoryId;
  }

  public Transaction transactionFee(BigDecimal transactionFee) {
    this.transactionFee = transactionFee;
    return this;
  }

  /**
   * fee related to transaction, in account's currency
   * @return transactionFee
  **/
  @ApiModelProperty(value = "fee related to transaction, in account's currency")

  @Valid

  public BigDecimal getTransactionFee() {
    return transactionFee;
  }

  public void setTransactionFee(BigDecimal transactionFee) {
    this.transactionFee = transactionFee;
  }

  public Transaction transactionFeeCanceled(Boolean transactionFeeCanceled) {
    this.transactionFeeCanceled = transactionFeeCanceled;
    return this;
  }

  /**
   * set to true if transaction fee is canceled.
   * @return transactionFeeCanceled
  **/
  @ApiModelProperty(value = "set to true if transaction fee is canceled.")


  public Boolean isTransactionFeeCanceled() {
    return transactionFeeCanceled;
  }

  public void setTransactionFeeCanceled(Boolean transactionFeeCanceled) {
    this.transactionFeeCanceled = transactionFeeCanceled;
  }

  public Transaction additionalInfoDomestic(TransactionAdditionalInfoDomestic additionalInfoDomestic) {
    this.additionalInfoDomestic = additionalInfoDomestic;
    return this;
  }

  /**
   * Get additionalInfoDomestic
   * @return additionalInfoDomestic
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionAdditionalInfoDomestic getAdditionalInfoDomestic() {
    return additionalInfoDomestic;
  }

  public void setAdditionalInfoDomestic(TransactionAdditionalInfoDomestic additionalInfoDomestic) {
    this.additionalInfoDomestic = additionalInfoDomestic;
  }

  public Transaction additionalInfoForeign(TransactionAdditionalInfoForeign additionalInfoForeign) {
    this.additionalInfoForeign = additionalInfoForeign;
    return this;
  }

  /**
   * Get additionalInfoForeign
   * @return additionalInfoForeign
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionAdditionalInfoForeign getAdditionalInfoForeign() {
    return additionalInfoForeign;
  }

  public void setAdditionalInfoForeign(TransactionAdditionalInfoForeign additionalInfoForeign) {
    this.additionalInfoForeign = additionalInfoForeign;
  }

  public Transaction additionalInfoCard(TransactionAdditionalInfoCard additionalInfoCard) {
    this.additionalInfoCard = additionalInfoCard;
    return this;
  }

  /**
   * Get additionalInfoCard
   * @return additionalInfoCard
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionAdditionalInfoCard getAdditionalInfoCard() {
    return additionalInfoCard;
  }

  public void setAdditionalInfoCard(TransactionAdditionalInfoCard additionalInfoCard) {
    this.additionalInfoCard = additionalInfoCard;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.accountId, transaction.accountId) &&
        Objects.equals(this.value, transaction.value) &&
        Objects.equals(this.partyAccount, transaction.partyAccount) &&
        Objects.equals(this.partyDescription, transaction.partyDescription) &&
        Objects.equals(this.direction, transaction.direction) &&
        Objects.equals(this.transactionType, transaction.transactionType) &&
        Objects.equals(this.valueDate, transaction.valueDate) &&
        Objects.equals(this.bookingDate, transaction.bookingDate) &&
        Objects.equals(this.userDescription, transaction.userDescription) &&
        Objects.equals(this.payerMessage, transaction.payerMessage) &&
        Objects.equals(this.payeeMessage, transaction.payeeMessage) &&
        Objects.equals(this.categoryId, transaction.categoryId) &&
        Objects.equals(this.transactionFee, transaction.transactionFee) &&
        Objects.equals(this.transactionFeeCanceled, transaction.transactionFeeCanceled) &&
        Objects.equals(this.additionalInfoDomestic, transaction.additionalInfoDomestic) &&
        Objects.equals(this.additionalInfoForeign, transaction.additionalInfoForeign) &&
        Objects.equals(this.additionalInfoCard, transaction.additionalInfoCard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, accountId, value, partyAccount, partyDescription, direction, transactionType, valueDate, bookingDate, userDescription, payerMessage, payeeMessage, categoryId, transactionFee, transactionFeeCanceled, additionalInfoDomestic, additionalInfoForeign, additionalInfoCard);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    partyAccount: ").append(toIndentedString(partyAccount)).append("\n");
    sb.append("    partyDescription: ").append(toIndentedString(partyDescription)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
    sb.append("    valueDate: ").append(toIndentedString(valueDate)).append("\n");
    sb.append("    bookingDate: ").append(toIndentedString(bookingDate)).append("\n");
    sb.append("    userDescription: ").append(toIndentedString(userDescription)).append("\n");
    sb.append("    payerMessage: ").append(toIndentedString(payerMessage)).append("\n");
    sb.append("    payeeMessage: ").append(toIndentedString(payeeMessage)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    transactionFee: ").append(toIndentedString(transactionFee)).append("\n");
    sb.append("    transactionFeeCanceled: ").append(toIndentedString(transactionFeeCanceled)).append("\n");
    sb.append("    additionalInfoDomestic: ").append(toIndentedString(additionalInfoDomestic)).append("\n");
    sb.append("    additionalInfoForeign: ").append(toIndentedString(additionalInfoForeign)).append("\n");
    sb.append("    additionalInfoCard: ").append(toIndentedString(additionalInfoCard)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

