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
 * party account number
 */
@ApiModel(description = "party account number")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

public class TransactionPartyAccount   {
  @JsonProperty("prefix")
  private String prefix = null;

  @JsonProperty("accountNumber")
  private String accountNumber = null;

  @JsonProperty("bankCode")
  private String bankCode = null;

  public TransactionPartyAccount prefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * account number prefix
   * @return prefix
  **/
  @ApiModelProperty(value = "account number prefix")

@Size(min=0) 
  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public TransactionPartyAccount accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  /**
   * account number. For domestic accounts, this will be specified in national format.
   * @return accountNumber
  **/
  @ApiModelProperty(required = true, value = "account number. For domestic accounts, this will be specified in national format.")
  @NotNull

@Size(min=0) 
  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public TransactionPartyAccount bankCode(String bankCode) {
    this.bankCode = bankCode;
    return this;
  }

  /**
   * for domestic accounts, bank code in national format; for foreign accounts, BIC code.
   * @return bankCode
  **/
  @ApiModelProperty(required = true, value = "for domestic accounts, bank code in national format; for foreign accounts, BIC code.")
  @NotNull

@Size(min=0) 
  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionPartyAccount transactionPartyAccount = (TransactionPartyAccount) o;
    return Objects.equals(this.prefix, transactionPartyAccount.prefix) &&
        Objects.equals(this.accountNumber, transactionPartyAccount.accountNumber) &&
        Objects.equals(this.bankCode, transactionPartyAccount.bankCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prefix, accountNumber, bankCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionPartyAccount {\n");
    
    sb.append("    prefix: ").append(toIndentedString(prefix)).append("\n");
    sb.append("    accountNumber: ").append(toIndentedString(accountNumber)).append("\n");
    sb.append("    bankCode: ").append(toIndentedString(bankCode)).append("\n");
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

