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
 * payment order additional info (symbols)
 */
@ApiModel(description = "payment order additional info (symbols)")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class PaymentAdditionalInfo   {
  @JsonProperty("constantSymbol")
  private String constantSymbol = null;

  @JsonProperty("variableSymbol")
  private String variableSymbol = null;

  @JsonProperty("specificSymbol")
  private String specificSymbol = null;

  public PaymentAdditionalInfo constantSymbol(String constantSymbol) {
    this.constantSymbol = constantSymbol;
    return this;
  }

  /**
   * Get constantSymbol
   * @return constantSymbol
  **/
  @ApiModelProperty(value = "")

@Size(min=0) 
  public String getConstantSymbol() {
    return constantSymbol;
  }

  public void setConstantSymbol(String constantSymbol) {
    this.constantSymbol = constantSymbol;
  }

  public PaymentAdditionalInfo variableSymbol(String variableSymbol) {
    this.variableSymbol = variableSymbol;
    return this;
  }

  /**
   * Get variableSymbol
   * @return variableSymbol
  **/
  @ApiModelProperty(value = "")

@Size(min=0) 
  public String getVariableSymbol() {
    return variableSymbol;
  }

  public void setVariableSymbol(String variableSymbol) {
    this.variableSymbol = variableSymbol;
  }

  public PaymentAdditionalInfo specificSymbol(String specificSymbol) {
    this.specificSymbol = specificSymbol;
    return this;
  }

  /**
   * Get specificSymbol
   * @return specificSymbol
  **/
  @ApiModelProperty(value = "")

@Size(min=0) 
  public String getSpecificSymbol() {
    return specificSymbol;
  }

  public void setSpecificSymbol(String specificSymbol) {
    this.specificSymbol = specificSymbol;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentAdditionalInfo paymentAdditionalInfo = (PaymentAdditionalInfo) o;
    return Objects.equals(this.constantSymbol, paymentAdditionalInfo.constantSymbol) &&
        Objects.equals(this.variableSymbol, paymentAdditionalInfo.variableSymbol) &&
        Objects.equals(this.specificSymbol, paymentAdditionalInfo.specificSymbol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(constantSymbol, variableSymbol, specificSymbol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentAdditionalInfo {\n");
    
    sb.append("    constantSymbol: ").append(toIndentedString(constantSymbol)).append("\n");
    sb.append("    variableSymbol: ").append(toIndentedString(variableSymbol)).append("\n");
    sb.append("    specificSymbol: ").append(toIndentedString(specificSymbol)).append("\n");
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

