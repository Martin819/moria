import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import moria.model.rules.Ruleset;

import java.math.BigDecimal;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionAdditionalInfoCard;
import moria.model.transactions.TransactionAdditionalInfoDomestic;
import moria.model.transactions.TransactionAdditionalInfoForeign;
import moria.model.transactions.TransactionPartyAccount;
import moria.model.transactions.TransactionValue;

/**
 * Trida pro pomocne metody k testovani
 */
class TestUtils {

  //parametry upravuj dle libosti
  static Ruleset createTestRuleset(int id, String direction, int categoryId, String transactionType, BigDecimal valueFrom, BigDecimal valueTo) {
    Ruleset ruleset = new Ruleset();
    ruleset.setId(id);
    ruleset.setDirection(direction);
    ruleset.setCategoryId(categoryId);
    ruleset.setTransactionType(transactionType);
    ruleset.setValueFrom(valueFrom);
    ruleset.setValueTo(valueTo);
    return ruleset;
  }


  static Ruleset createTestRulesetFull(int id, String ruleName, String partyName, int categoryId,
      String direction, String transactionType, BigDecimal valueFrom, BigDecimal valueTo, String partyAccountPrefix,
      String partyAccountNumber, String partyBankCode, String payerMessage, String payeeMessage,
      String constantSymbol, String variableSymbol, String specificSymbol, String bookingTimeFrom,
      String bookingTimeTo, String cardNumber) {

    Ruleset ruleset = new Ruleset();
    ruleset.setId(id);
    ruleset.setRuleName(ruleName);
    ruleset.setPartyName(partyName);
    ruleset.setCategoryId(categoryId);
    ruleset.setDirection(direction);
    ruleset.setTransactionType(transactionType);
    ruleset.setValueFrom(valueFrom);
    ruleset.setValueTo(valueTo);
    ruleset.setPartyAccountPrefix(partyAccountPrefix);
    ruleset.setPartyAccountNumber(partyAccountNumber);
    ruleset.setPartyBankCode(partyBankCode);
    ruleset.setPayeeMessage(payeeMessage);
    ruleset.setPayerMessage(payerMessage);
    ruleset.setConstantSymbol(constantSymbol);
    ruleset.setVariableSymbol(variableSymbol);
    ruleset.setSpecificSymbol(specificSymbol);
    ruleset.setBookingTimeFrom(bookingTimeFrom);
    ruleset.setBookingTimeTo(bookingTimeTo);
    ruleset.setCardNumber(cardNumber);
    return ruleset;
  }

  static Transaction createTransaction(int id, int accountId, TransactionValue value, TransactionPartyAccount partyAccount, String partyDescription, String direction,
      String transactionType, Date valueDate, Date bookingDate, String userDescription, String payerMessage, String payeeMessage, Integer categoryId,
      BigDecimal transactionFee, Boolean transactionFeeCanceled, TransactionAdditionalInfoDomestic additionalInfoDomestic,
      TransactionAdditionalInfoForeign additionalInfoForeign, TransactionAdditionalInfoCard additionalInfoCard) {

    Transaction transaction = new Transaction();

    transaction.setId(id);
    transaction.setAccountId(accountId);
    transaction.setValue(value);
    transaction.setPartyAccount(partyAccount);
    transaction.setPartyDescription(partyDescription);
    transaction.setDirection(direction);
    transaction.setTransactionType(transactionType);
    transaction.setValueDate(valueDate);
    transaction.setBookingDate(bookingDate);
    transaction.setUserDescription(userDescription);
    transaction.setPayerMessage(payerMessage);
    transaction.setPayeeMessage(payeeMessage);
    transaction.setCategoryId(categoryId);
    transaction.setTransactionFee(transactionFee);
    transaction.setTransactionFeeCanceled(transactionFeeCanceled);
    transaction.setAdditionalInfoDomestic(additionalInfoDomestic);
    transaction.setAdditionalInfoForeign(additionalInfoForeign);
    transaction.setAdditionalInfoCard(additionalInfoCard);

    return transaction;
  }


  static TransactionValue createTransactionValue(BigDecimal value, String currency) {
    TransactionValue transactionValue = new TransactionValue();
    transactionValue.setAmount(value);
    transactionValue.setCurrency(currency);
    return transactionValue;
  }

  static Date parseDate(int hours, int minutes, int seconds) {
    try {
      SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date date = isoFormat.parse("2010-05-23T" + hours + ":" + minutes + ":" + seconds);
      return date;
    } catch (ParseException e){
      e.getMessage();
    }
    return null;
  }
  //stejnym zpusobem napriklad createTransaction
}
