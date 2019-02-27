package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * card payemnts additional info (card number, mcc and merchant name)
 */
@ApiModel(description = "card payemnts additional info (card number, mcc and merchant name)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class TransactionAdditionalInfoCard   {
  @JsonProperty("mcc")
  private String mcc = null;

  @JsonProperty("merchantName")
  private String merchantName = null;

  @JsonProperty("cardNumber")
  private String cardNumber = null;

  public TransactionAdditionalInfoCard mcc(String mcc) {
    this.mcc = mcc;
    return this;
  }

  /**
   * Merchant Category code
   * @return mcc
  **/
  @ApiModelProperty(value = "Merchant Category code")

@Size(min=0) 
  public String getMcc() {
    return mcc;
  }

  public void setMcc(String mcc) {
    this.mcc = mcc;
  }

  public TransactionAdditionalInfoCard merchantName(String merchantName) {
    this.merchantName = merchantName;
    return this;
  }

  /**
   * Get merchantName
   * @return merchantName
  **/
  @ApiModelProperty(value = "")

@Size(min=0) 
  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public TransactionAdditionalInfoCard cardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
    return this;
  }

  /**
   * masked card number used for transaction
   * @return cardNumber
  **/
  @ApiModelProperty(required = true, value = "masked card number used for transaction")
  @NotNull

@Size(min=0) 
  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionAdditionalInfoCard transactionAdditionalInfoCard = (TransactionAdditionalInfoCard) o;
    return Objects.equals(this.mcc, transactionAdditionalInfoCard.mcc) &&
        Objects.equals(this.merchantName, transactionAdditionalInfoCard.merchantName) &&
        Objects.equals(this.cardNumber, transactionAdditionalInfoCard.cardNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mcc, merchantName, cardNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionAdditionalInfoCard {\n");
    
    sb.append("    mcc: ").append(toIndentedString(mcc)).append("\n");
    sb.append("    merchantName: ").append(toIndentedString(merchantName)).append("\n");
    sb.append("    cardNumber: ").append(toIndentedString(cardNumber)).append("\n");
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

