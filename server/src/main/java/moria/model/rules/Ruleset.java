package moria.model.rules;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "rulesets")
public class Ruleset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
// VALUE
    private String valueFromValue;
    private String valueFromOperator;
    private String valueToValue;
    private String valueToOperator;
// PARTY ACCOUNT
    private String partyPrefixValue;
    private String partyAccountNumberValue;
    private String partyBankCodeValue;
    private String partyDescriptionValue;
    private String partyDescriptionOperator;
// DATE
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDateFromValue;
    private String valueDateFromOperator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDateToValue;
    private String valueDateToOperator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateFromValue;
    private String bookingDateFromOperator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateToValue;
    private String bookingDateToOperator;
// DESCRIPTIONS
    private String userDescriptionValue;
    private String userDescriptionOperator;
    private String payerMessageValue;
    private String payerMessageOperator;
    private String payeeMessageValue;
    private String payeeMessageOperator;
// SYMBOLS
    private String constantSymbolValue;
    private String variableSymbolFromValue;
    private String variableSymbolFromOperator;
    private String variableSymbolToValue;
    private String variableSymbolToOperator;
    private String specificSymbolValue;
// CARDS
    private String mccValue;
    private String merchantNameValue;
    private String merchantNameOperator;
    private String cardNumberValue;

    public Ruleset() {
    }

    public Ruleset(String valueFromValue, String valueFromOperator, String valueToValue, String valueToOperator, String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue, String partyDescriptionValue, String partyDescriptionOperator, Date valueDateFromValue, String valueDateFromOperator, Date valueDateToValue, String valueDateToOperator, Date bookingDateFromValue, String bookingDateFromOperator, Date bookingDateToValue, String bookingDateToOperator, String userDescriptionValue, String userDescriptionOperator, String payerMessageValue, String payerMessageOperator, String payeeMessageValue, String payeeMessageOperator, String constantSymbolValue, String variableSymbolFromValue, String variableSymbolFromOperator, String variableSymbolToValue, String variableSymbolToOperator, String specificSymbolValue, String mccValue, String merchantNameValue, String merchantNameOperator, String cardNumberValue) {
        this.valueFromValue = valueFromValue;
        this.valueFromOperator = valueFromOperator;
        this.valueToValue = valueToValue;
        this.valueToOperator = valueToOperator;
        this.partyPrefixValue = partyPrefixValue;
        this.partyAccountNumberValue = partyAccountNumberValue;
        this.partyBankCodeValue = partyBankCodeValue;
        this.partyDescriptionValue = partyDescriptionValue;
        this.partyDescriptionOperator = partyDescriptionOperator;
        this.valueDateFromValue = valueDateFromValue;
        this.valueDateFromOperator = valueDateFromOperator;
        this.valueDateToValue = valueDateToValue;
        this.valueDateToOperator = valueDateToOperator;
        this.bookingDateFromValue = bookingDateFromValue;
        this.bookingDateFromOperator = bookingDateFromOperator;
        this.bookingDateToValue = bookingDateToValue;
        this.bookingDateToOperator = bookingDateToOperator;
        this.userDescriptionValue = userDescriptionValue;
        this.userDescriptionOperator = userDescriptionOperator;
        this.payerMessageValue = payerMessageValue;
        this.payerMessageOperator = payerMessageOperator;
        this.payeeMessageValue = payeeMessageValue;
        this.payeeMessageOperator = payeeMessageOperator;
        this.constantSymbolValue = constantSymbolValue;
        this.variableSymbolFromValue = variableSymbolFromValue;
        this.variableSymbolFromOperator = variableSymbolFromOperator;
        this.variableSymbolToValue = variableSymbolToValue;
        this.variableSymbolToOperator = variableSymbolToOperator;
        this.specificSymbolValue = specificSymbolValue;
        this.mccValue = mccValue;
        this.merchantNameValue = merchantNameValue;
        this.merchantNameOperator = merchantNameOperator;
        this.cardNumberValue = cardNumberValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValueFromValue() {
        return valueFromValue;
    }

    public void setValueFromValue(String valueFromValue) {
        this.valueFromValue = valueFromValue;
    }

    public String getValueFromOperator() {
        return valueFromOperator;
    }

    public void setValueFromOperator(String valueFromOperator) {
        this.valueFromOperator = valueFromOperator;
    }

    public String getValueToValue() {
        return valueToValue;
    }

    public void setValueToValue(String valueToValue) {
        this.valueToValue = valueToValue;
    }

    public String getValueToOperator() {
        return valueToOperator;
    }

    public void setValueToOperator(String valueToOperator) {
        this.valueToOperator = valueToOperator;
    }

    public String getPartyPrefixValue() {
        return partyPrefixValue;
    }

    public void setPartyPrefixValue(String partyPrefixValue) {
        this.partyPrefixValue = partyPrefixValue;
    }

    public String getPartyAccountNumberValue() {
        return partyAccountNumberValue;
    }

    public void setPartyAccountNumberValue(String partyAccountNumberValue) {
        this.partyAccountNumberValue = partyAccountNumberValue;
    }

    public String getPartyBankCodeValue() {
        return partyBankCodeValue;
    }

    public void setPartyBankCodeValue(String partyBankCodeValue) {
        this.partyBankCodeValue = partyBankCodeValue;
    }

    public String getPartyDescriptionValue() {
        return partyDescriptionValue;
    }

    public void setPartyDescriptionValue(String partyDescriptionValue) {
        this.partyDescriptionValue = partyDescriptionValue;
    }

    public String getPartyDescriptionOperator() {
        return partyDescriptionOperator;
    }

    public void setPartyDescriptionOperator(String partyDescriptionOperator) {
        this.partyDescriptionOperator = partyDescriptionOperator;
    }

    public Date getValueDateFromValue() {
        return valueDateFromValue;
    }

    public void setValueDateFromValue(Date valueDateFromValue) {
        this.valueDateFromValue = valueDateFromValue;
    }

    public String getValueDateFromOperator() {
        return valueDateFromOperator;
    }

    public void setValueDateFromOperator(String valueDateFromOperator) {
        this.valueDateFromOperator = valueDateFromOperator;
    }

    public Date getValueDateToValue() {
        return valueDateToValue;
    }

    public void setValueDateToValue(Date valueDateToValue) {
        this.valueDateToValue = valueDateToValue;
    }

    public String getValueDateToOperator() {
        return valueDateToOperator;
    }

    public void setValueDateToOperator(String valueDateToOperator) {
        this.valueDateToOperator = valueDateToOperator;
    }

    public Date getBookingDateFromValue() {
        return bookingDateFromValue;
    }

    public void setBookingDateFromValue(Date bookingDateFromValue) {
        this.bookingDateFromValue = bookingDateFromValue;
    }

    public String getBookingDateFromOperator() {
        return bookingDateFromOperator;
    }

    public void setBookingDateFromOperator(String bookingDateFromOperator) {
        this.bookingDateFromOperator = bookingDateFromOperator;
    }

    public Date getBookingDateToValue() {
        return bookingDateToValue;
    }

    public void setBookingDateToValue(Date bookingDateToValue) {
        this.bookingDateToValue = bookingDateToValue;
    }

    public String getBookingDateToOperator() {
        return bookingDateToOperator;
    }

    public void setBookingDateToOperator(String bookingDateToOperator) {
        this.bookingDateToOperator = bookingDateToOperator;
    }

    public String getUserDescriptionValue() {
        return userDescriptionValue;
    }

    public void setUserDescriptionValue(String userDescriptionValue) {
        this.userDescriptionValue = userDescriptionValue;
    }

    public String getUserDescriptionOperator() {
        return userDescriptionOperator;
    }

    public void setUserDescriptionOperator(String userDescriptionOperator) {
        this.userDescriptionOperator = userDescriptionOperator;
    }

    public String getPayerMessageValue() {
        return payerMessageValue;
    }

    public void setPayerMessageValue(String payerMessageValue) {
        this.payerMessageValue = payerMessageValue;
    }

    public String getPayerMessageOperator() {
        return payerMessageOperator;
    }

    public void setPayerMessageOperator(String payerMessageOperator) {
        this.payerMessageOperator = payerMessageOperator;
    }

    public String getPayeeMessageValue() {
        return payeeMessageValue;
    }

    public void setPayeeMessageValue(String payeeMessageValue) {
        this.payeeMessageValue = payeeMessageValue;
    }

    public String getPayeeMessageOperator() {
        return payeeMessageOperator;
    }

    public void setPayeeMessageOperator(String payeeMessageOperator) {
        this.payeeMessageOperator = payeeMessageOperator;
    }

    public String getConstantSymbolValue() {
        return constantSymbolValue;
    }

    public void setConstantSymbolValue(String constantSymbolValue) {
        this.constantSymbolValue = constantSymbolValue;
    }

    public String getVariableSymbolFromValue() {
        return variableSymbolFromValue;
    }

    public void setVariableSymbolFromValue(String variableSymbolFromValue) {
        this.variableSymbolFromValue = variableSymbolFromValue;
    }

    public String getVariableSymbolFromOperator() {
        return variableSymbolFromOperator;
    }

    public void setVariableSymbolFromOperator(String variableSymbolFromOperator) {
        this.variableSymbolFromOperator = variableSymbolFromOperator;
    }

    public String getVariableSymbolToValue() {
        return variableSymbolToValue;
    }

    public void setVariableSymbolToValue(String variableSymbolToValue) {
        this.variableSymbolToValue = variableSymbolToValue;
    }

    public String getVariableSymbolToOperator() {
        return variableSymbolToOperator;
    }

    public void setVariableSymbolToOperator(String variableSymbolToOperator) {
        this.variableSymbolToOperator = variableSymbolToOperator;
    }

    public String getSpecificSymbolValue() {
        return specificSymbolValue;
    }

    public void setSpecificSymbolValue(String specificSymbolValue) {
        this.specificSymbolValue = specificSymbolValue;
    }

    public String getMccValue() {
        return mccValue;
    }

    public void setMccValue(String mccValue) {
        this.mccValue = mccValue;
    }

    public String getMerchantNameValue() {
        return merchantNameValue;
    }

    public void setMerchantNameValue(String merchantNameValue) {
        this.merchantNameValue = merchantNameValue;
    }

    public String getMerchantNameOperator() {
        return merchantNameOperator;
    }

    public void setMerchantNameOperator(String merchantNameOperator) {
        this.merchantNameOperator = merchantNameOperator;
    }

    public String getCardNumberValue() {
        return cardNumberValue;
    }

    public void setCardNumberValue(String cardNumberValue) {
        this.cardNumberValue = cardNumberValue;
    }
}
