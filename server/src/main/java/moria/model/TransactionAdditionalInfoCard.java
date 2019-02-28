package moria.model;

public class TransactionAdditionalInfoCard {

    private String mcc;
    private String merchantName;
    private String cardNumber;

    public TransactionAdditionalInfoCard() {
    }

    public TransactionAdditionalInfoCard(String mcc, String merchantName, String cardNumber) {
        this.mcc = mcc;
        this.merchantName = merchantName;
        this.cardNumber = cardNumber;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
