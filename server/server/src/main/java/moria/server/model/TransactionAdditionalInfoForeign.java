package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import moria.server.model.TransactionAdditionalInfoForeignOriginalValue;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * foreign payments additional info (original value and exchange rate)
 */
@ApiModel(description = "foreign payments additional info (original value and exchange rate)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class TransactionAdditionalInfoForeign   {
  @JsonProperty("originalValue")
  private TransactionAdditionalInfoForeignOriginalValue originalValue = null;

  @JsonProperty("exchangeRate")
  private BigDecimal exchangeRate = null;

  public TransactionAdditionalInfoForeign originalValue(TransactionAdditionalInfoForeignOriginalValue originalValue) {
    this.originalValue = originalValue;
    return this;
  }

  /**
   * Get originalValue
   * @return originalValue
  **/
  @ApiModelProperty(value = "")

  @Valid

  public TransactionAdditionalInfoForeignOriginalValue getOriginalValue() {
    return originalValue;
  }

  public void setOriginalValue(TransactionAdditionalInfoForeignOriginalValue originalValue) {
    this.originalValue = originalValue;
  }

  public TransactionAdditionalInfoForeign exchangeRate(BigDecimal exchangeRate) {
    this.exchangeRate = exchangeRate;
    return this;
  }

  /**
   * exchange rate valid at the time of transfer
   * @return exchangeRate
  **/
  @ApiModelProperty(value = "exchange rate valid at the time of transfer")

  @Valid

  public BigDecimal getExchangeRate() {
    return exchangeRate;
  }

  public void setExchangeRate(BigDecimal exchangeRate) {
    this.exchangeRate = exchangeRate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionAdditionalInfoForeign transactionAdditionalInfoForeign = (TransactionAdditionalInfoForeign) o;
    return Objects.equals(this.originalValue, transactionAdditionalInfoForeign.originalValue) &&
        Objects.equals(this.exchangeRate, transactionAdditionalInfoForeign.exchangeRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(originalValue, exchangeRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionAdditionalInfoForeign {\n");
    
    sb.append("    originalValue: ").append(toIndentedString(originalValue)).append("\n");
    sb.append("    exchangeRate: ").append(toIndentedString(exchangeRate)).append("\n");
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

