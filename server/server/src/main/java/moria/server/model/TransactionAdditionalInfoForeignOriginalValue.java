package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * amount &amp; currency in which transaction was originated
 */
@ApiModel(description = "amount & currency in which transaction was originated")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class TransactionAdditionalInfoForeignOriginalValue   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("currency")
  private String currency = null;

  public TransactionAdditionalInfoForeignOriginalValue amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * amount of money
   * @return amount
  **/
  @ApiModelProperty(required = true, value = "amount of money")
  @NotNull

  @Valid

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public TransactionAdditionalInfoForeignOriginalValue currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * currency of money
   * @return currency
  **/
  @ApiModelProperty(required = true, value = "currency of money")
  @NotNull

@Size(min=0) 
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionAdditionalInfoForeignOriginalValue transactionAdditionalInfoForeignOriginalValue = (TransactionAdditionalInfoForeignOriginalValue) o;
    return Objects.equals(this.amount, transactionAdditionalInfoForeignOriginalValue.amount) &&
        Objects.equals(this.currency, transactionAdditionalInfoForeignOriginalValue.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionAdditionalInfoForeignOriginalValue {\n");
    
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
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

