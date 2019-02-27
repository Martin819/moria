package moria.server.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * party account number
 */
@ApiModel(description = "party account number")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class PaymentRecuringPayment   {
  @JsonProperty("firstPayment")
  private OffsetDateTime firstPayment = null;

  @JsonProperty("lastPayment")
  private OffsetDateTime lastPayment = null;

  /**
   * Gets or Sets interval
   */
  public enum IntervalEnum {
    WEEK("WEEK"),
    
    MONTH("MONTH"),
    
    QUARTER("QUARTER"),
    
    HALF_YEAR("HALF-YEAR"),
    
    YEAR("YEAR");

    private String value;

    IntervalEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static IntervalEnum fromValue(String text) {
      for (IntervalEnum b : IntervalEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("interval")
  private IntervalEnum interval = null;

  public PaymentRecuringPayment firstPayment(OffsetDateTime firstPayment) {
    this.firstPayment = firstPayment;
    return this;
  }

  /**
   * Get firstPayment
   * @return firstPayment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getFirstPayment() {
    return firstPayment;
  }

  public void setFirstPayment(OffsetDateTime firstPayment) {
    this.firstPayment = firstPayment;
  }

  public PaymentRecuringPayment lastPayment(OffsetDateTime lastPayment) {
    this.lastPayment = lastPayment;
    return this;
  }

  /**
   * Get lastPayment
   * @return lastPayment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public OffsetDateTime getLastPayment() {
    return lastPayment;
  }

  public void setLastPayment(OffsetDateTime lastPayment) {
    this.lastPayment = lastPayment;
  }

  public PaymentRecuringPayment interval(IntervalEnum interval) {
    this.interval = interval;
    return this;
  }

  /**
   * Get interval
   * @return interval
  **/
  @ApiModelProperty(value = "")


  public IntervalEnum getInterval() {
    return interval;
  }

  public void setInterval(IntervalEnum interval) {
    this.interval = interval;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentRecuringPayment paymentRecuringPayment = (PaymentRecuringPayment) o;
    return Objects.equals(this.firstPayment, paymentRecuringPayment.firstPayment) &&
        Objects.equals(this.lastPayment, paymentRecuringPayment.lastPayment) &&
        Objects.equals(this.interval, paymentRecuringPayment.interval);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstPayment, lastPayment, interval);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentRecuringPayment {\n");
    
    sb.append("    firstPayment: ").append(toIndentedString(firstPayment)).append("\n");
    sb.append("    lastPayment: ").append(toIndentedString(lastPayment)).append("\n");
    sb.append("    interval: ").append(toIndentedString(interval)).append("\n");
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

