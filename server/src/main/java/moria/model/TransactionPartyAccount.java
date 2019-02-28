package moria.model;

public class TransactionPartyAccount {

    private String prefix;
    private String accountNumber;
    private String bankCode;

    public TransactionPartyAccount() {
    }

    public TransactionPartyAccount(String prefix, String accountNumber, String bankCode) {
        this.prefix = prefix;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
