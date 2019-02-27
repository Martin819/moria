package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import moria.server.model.PaymentAdditionalInfo;
import moria.server.model.PaymentRecuringPayment;
import moria.server.model.PaymentValue;
import moria.server.model.TransactionPartyAccount;
import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Payment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class Payment   {
  @JsonProperty("value")
  private PaymentValue value = null;

  @JsonProperty("partyAccount")
  private TransactionPartyAccount partyAccount = null;

  @JsonProperty("dueDate")
  private OffsetDateTime dueDate = null;

  @JsonProperty("recuringPayment")
  private PaymentRecuringPayment recuringPayment = null;

  @JsonProperty("payeeMessage")
  private String payeeMessage = null;

  @JsonProperty("payerMessage")
  private String payerMessage = null;

  @JsonProperty("categoryId")
  private BigDecimal categoryId = null;

  @JsonProperty("additionalInfo")
  private PaymentAdditionalInfo additionalInfo = null;

  @JsonProperty("id")
  private BigDecimal id = null;

  @JsonProperty("accountId")
  private BigDecimal accountId = null;

  @JsonProperty("editableByUser")
  private Boolean editableByUser = null;

  /**
   * payment order realization status
   */
  public enum RealizationStatusEnum {
    EDITED("RTS_EDITED"),
    
    NOT_REALISED("RTS_NOT_REALISED"),
    
    NOT_FULLY_REALISED("RTS_NOT_FULLY_REALISED"),
    
    REALISED("RTS_REALISED"),
    
    SUSPENDED("RTS_SUSPENDED"),
    
    ENDED("RTS_ENDED"),
    
    WAIT_FOR_AUTHORISATION("RTS_WAIT_FOR_AUTHORISATION"),
    
    FAULTY_PARAMS("RTS_FAULTY_PARAMS"),
    
    READY_TO_SEND("RTS_READY_TO_SEND"),
    
    SENT("RTS_SENT"),
    
    REFUSED_BY_COUNTERPARTY("RTS_REFUSED_BY_COUNTERPARTY"),
    
    REFUSED_ERROR("RTS_REFUSED_ERROR"),
    
    INPROC("RTS_INPROC"),
    
    WAITS_FOR_APPROVAL("RTS_WAITS_FOR_APPROVAL"),
    
    PARTLYSIGNED("RTS_PARTLYSIGNED"),
    
    SIGNED("RTS_SIGNED"),
    
    PARTLYEDITED("RTS_PARTLYEDITED"),
    
    CANCELLED("RTS_CANCELLED"),
    
    FOR_EXT_PROCESSING("RTS_FOR_EXT_PROCESSING"),
    
    WAIT_FOR_CNDPRECEDENT("RTS_WAIT_FOR_CNDPRECEDENT");

    private String value;

    RealizationStatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RealizationStatusEnum fromValue(String text) {
      for (RealizationStatusEnum b : RealizationStatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("realizationStatus")
  private RealizationStatusEnum realizationStatus = null;

  public Payment value(PaymentValue value) {
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

  public PaymentValue getValue() {
    return value;
  }

  public void setValue(PaymentValue value) {
    this.value = value;
  }

  public Payment partyAccount(TransactionPartyAccount partyAccount) {
    this.partyAccount = partyAccount;
    return this;
  }

  /**
   * Get partyAccount
   * @return partyAccount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TransactionPartyAccount getPartyAccount() {
    return partyAccount;
  }

  public void setPartyAccount(TransactionPartyAccount partyAccount) {
    this.partyAccount = partyAccount;
  }

  public Payment dueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * payment order due date
   * @return dueDate
  **/
  @ApiModelProperty(required = true, value = "payment order due date")
  @NotNull

  @Valid

  public OffsetDateTime getDueDate() {
    return dueDate;
  }

  public void setDueDate(OffsetDateTime dueDate) {
    this.dueDate = dueDate;
  }

  public Payment recuringPayment(PaymentRecuringPayment recuringPayment) {
    this.recuringPayment = recuringPayment;
    return this;
  }

  /**
   * Get recuringPayment
   * @return recuringPayment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public PaymentRecuringPayment getRecuringPayment() {
    return recuringPayment;
  }

  public void setRecuringPayment(PaymentRecuringPayment recuringPayment) {
    this.recuringPayment = recuringPayment;
  }

  public Payment payeeMessage(String payeeMessage) {
    this.payeeMessage = payeeMessage;
    return this;
  }

  /**
   * message for payee
   * @return payeeMessage
  **/
  @ApiModelProperty(value = "message for payee")

@Size(min=0) 
  public String getPayeeMessage() {
    return payeeMessage;
  }

  public void setPayeeMessage(String payeeMessage) {
    this.payeeMessage = payeeMessage;
  }

  public Payment payerMessage(String payerMessage) {
    this.payerMessage = payerMessage;
    return this;
  }

  /**
   * message for payer
   * @return payerMessage
  **/
  @ApiModelProperty(value = "message for payer")

@Size(min=0) 
  public String getPayerMessage() {
    return payerMessage;
  }

  public void setPayerMessage(String payerMessage) {
    this.payerMessage = payerMessage;
  }

  public Payment categoryId(BigDecimal categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * id of category for payment order. More info about category can be retrieved using /openapi/banking/categories resource.
   * @return categoryId
  **/
  @ApiModelProperty(value = "id of category for payment order. More info about category can be retrieved using /openapi/banking/categories resource.")

  @Valid

  public BigDecimal getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(BigDecimal categoryId) {
    this.categoryId = categoryId;
  }

  public Payment additionalInfo(PaymentAdditionalInfo additionalInfo) {
    this.additionalInfo = additionalInfo;
    return this;
  }

  /**
   * Get additionalInfo
   * @return additionalInfo
  **/
  @ApiModelProperty(value = "")

  @Valid

  public PaymentAdditionalInfo getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(PaymentAdditionalInfo additionalInfo) {
    this.additionalInfo = additionalInfo;
  }

  public Payment id(BigDecimal id) {
    this.id = id;
    return this;
  }

  /**
   * internal domestic payment order identifier
   * @return id
  **/
  @ApiModelProperty(required = true, value = "internal domestic payment order identifier")
  @NotNull

  @Valid

  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public Payment accountId(BigDecimal accountId) {
    this.accountId = accountId;
    return this;
  }

  /**
   * account to that payment belongs (to which it is accounted)
   * @return accountId
  **/
  @ApiModelProperty(required = true, value = "account to that payment belongs (to which it is accounted)")
  @NotNull

  @Valid

  public BigDecimal getAccountId() {
    return accountId;
  }

  public void setAccountId(BigDecimal accountId) {
    this.accountId = accountId;
  }

  public Payment editableByUser(Boolean editableByUser) {
    this.editableByUser = editableByUser;
    return this;
  }

  /**
   * editable flag; true if user can modify payment order
   * @return editableByUser
  **/
  @ApiModelProperty(required = true, value = "editable flag; true if user can modify payment order")
  @NotNull


  public Boolean isEditableByUser() {
    return editableByUser;
  }

  public void setEditableByUser(Boolean editableByUser) {
    this.editableByUser = editableByUser;
  }

  public Payment realizationStatus(RealizationStatusEnum realizationStatus) {
    this.realizationStatus = realizationStatus;
    return this;
  }

  /**
   * payment order realization status
   * @return realizationStatus
  **/
  @ApiModelProperty(required = true, value = "payment order realization status")
  @NotNull


  public RealizationStatusEnum getRealizationStatus() {
    return realizationStatus;
  }

  public void setRealizationStatus(RealizationStatusEnum realizationStatus) {
    this.realizationStatus = realizationStatus;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.value, payment.value) &&
        Objects.equals(this.partyAccount, payment.partyAccount) &&
        Objects.equals(this.dueDate, payment.dueDate) &&
        Objects.equals(this.recuringPayment, payment.recuringPayment) &&
        Objects.equals(this.payeeMessage, payment.payeeMessage) &&
        Objects.equals(this.payerMessage, payment.payerMessage) &&
        Objects.equals(this.categoryId, payment.categoryId) &&
        Objects.equals(this.additionalInfo, payment.additionalInfo) &&
        Objects.equals(this.id, payment.id) &&
        Objects.equals(this.accountId, payment.accountId) &&
        Objects.equals(this.editableByUser, payment.editableByUser) &&
        Objects.equals(this.realizationStatus, payment.realizationStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, partyAccount, dueDate, recuringPayment, payeeMessage, payerMessage, categoryId, additionalInfo, id, accountId, editableByUser, realizationStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    partyAccount: ").append(toIndentedString(partyAccount)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    recuringPayment: ").append(toIndentedString(recuringPayment)).append("\n");
    sb.append("    payeeMessage: ").append(toIndentedString(payeeMessage)).append("\n");
    sb.append("    payerMessage: ").append(toIndentedString(payerMessage)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    additionalInfo: ").append(toIndentedString(additionalInfo)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    editableByUser: ").append(toIndentedString(editableByUser)).append("\n");
    sb.append("    realizationStatus: ").append(toIndentedString(realizationStatus)).append("\n");
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

