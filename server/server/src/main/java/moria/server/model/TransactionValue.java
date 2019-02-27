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
 * amount &amp; currency used for bookkeeping (e.g. amount in account&#39;s currency). Can be negative e.g. if something was withdrawn from an account. Original transaction amount and currency will be specified in &#x60;additionalInfo&#x60; object if applicable
 */
@ApiModel(description = "amount & currency used for bookkeeping (e.g. amount in account's currency). Can be negative e.g. if something was withdrawn from an account. Original transaction amount and currency will be specified in `additionalInfo` object if applicable")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class TransactionValue   {
  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("currency")
  private String currency = null;

  public TransactionValue amount(BigDecimal amount) {
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

  public TransactionValue currency(String currency) {
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
    TransactionValue transactionValue = (TransactionValue) o;
    return Objects.equals(this.amount, transactionValue.amount) &&
        Objects.equals(this.currency, transactionValue.currency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionValue {\n");
    
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

