package moria.utils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
import moria.dto.Category;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
import moria.services.RulesetService;
import moria.services.TransactionService;

public class CategoryScorer {

  private static final double DIRECTION_SCORE = 1.75;
  private static final int threshold = 75;
  private List<Ruleset> ruleSet;
  private Transaction transaction;


  //for test purpose only
  private int category;

  /**
   * Evalute categoryId based on provided Transaction
   *
   * @param transaction provided transaction for evaluation
   * @return ID of categoryId from Enum or 0 - algorithm cant find proper categoryId
   */

  public int scoreCategories(Transaction transaction) {
    if (ruleSet == null){
        loadAllRules();
    }

    this.transaction = transaction;

    //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
    TreeMap<Double, Integer> categories = new TreeMap<>();
    for (Ruleset rule : ruleSet) {
      double score = 0;
      if (checkNullForScoreParty(rule.getDirection(), rule.getPartyAccountPrefix(), rule.getPartyAccountNumber(), rule.getPartyBankCode(), rule.getPartyName())) {
        score += scorePartyAccount(rule.getDirection(), rule.getPartyAccountPrefix(), rule.getPartyAccountNumber(), rule.getPartyBankCode(), rule.getPartyName());
      } else if (rule != null) {
        score += scorePartyAccountNumberOnly(rule.getPartyAccountNumber(), rule.getPartyBankCode(), rule.getPartyName());
      }

      if (rule.getBookingTimeFrom() != null && rule.getBookingTimeTo() != null) {
        score += scoreTransactionDate(LocalTime.parse(rule.getBookingTimeFrom()), LocalTime.parse(rule.getBookingTimeTo()));
      }
      if (rule.getValueFrom() != null && rule.getValueTo() != null) {
        score += scoreTransactionValue(rule.getValueFrom(), rule.getValueTo());
      }
      if (transaction.getDirection().equals("INCOMING")) {
        score += scoreTransactionMessage(rule.getPayerMessage(), transaction.getPayerMessage());
      }
      if (transaction.getDirection().equals("OUTGOING")) {
        score += scoreTransactionMessage(rule.getPayeeMessage(), transaction.getPayeeMessage());
      }
      if (transaction.getAdditionalInfoCard() != null) {
        if (checkNullForScoreCard(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber())) {
          score += scoreCardNumber(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber());
        }
      }
      if (transaction.getAdditionalInfoDomestic() != null) {
        if (checkNullForConstantAndVariable(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())) {
          score += scoreConstantAndVariable(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
        }
      }
      if (rule.getTransactionType() != null && transaction.getTransactionType() != null) {
        score += scoreTransactionType(rule.getTransactionType(), transaction.getTransactionType());
      }

      //podminka kvuli vybrani kategorie na zakladě incomming/outgoing
      if (score == DIRECTION_SCORE) {
        score = 0;
      }

      //počítá počet vyplněných pravidel a poté je jejich převrácenou hodnotou celého počtu násobí skore (pro zvýhodnění malého počtu vyplněných položek v rulesetu)
      double coefficient = findCoefficientBasedOnNumberOfParameters(rule);
      score = score * coefficient;
      categories.put(score, rule.getCategoryId());
    }

    if (categories.lastKey() == 0 || categories.isEmpty()) {
      return 0;
    } else {
      return categories.lastEntry().getValue();
    }
  }

  private double scorePartyAccountNumberOnly(String partyAccountNumber, String partyBankCode, String partyName) {
    double score = 0;
    if (partyAccountNumber != null && !partyAccountNumber.isEmpty() && transaction.getPartyAccount() != null && transaction.getPartyAccount().getAccountNumber() != null) {
      if (transaction.getPartyAccount().getAccountNumber().contains(partyAccountNumber)) {
        score++;
      }
    }

    if (partyBankCode != null && transaction.getPartyAccount() != null && transaction.getPartyAccount().getBankCode() != null) {
      if (transaction.getPartyAccount().getBankCode().contains(partyBankCode)) {
        score++;
      }
    }
    if (partyName != null && transaction.getPartyAccount() != null && transaction.getPartyDescription() != null) {
      if (transaction.getPartyDescription().contains(partyName)) {
        score++;
      }
    }
    return score;
  }

  /**
   * check if parameters of ruleset are null
   *
   * @param rule from Ruleset used in database
   * @return reverted value of not null parameters
   */
  private double findCoefficientBasedOnNumberOfParameters(Ruleset rule) {
    double notNull = 0;
    if (rule.getValueFrom() != null) {
      notNull++;
    }
    if (rule.getValueTo() != null) {
      notNull++;
    }
    if (rule.getPartyAccountPrefix() != null) {
      notNull++;
    }
    if (rule.getPartyAccountNumber() != null) {
      notNull++;
    }
    if (rule.getPartyBankCode() != null) {
      notNull++;
    }
    if (rule.getPartyName() != null) {
      notNull++;
    }
    if (rule.getBookingTimeFrom() != null) {
      notNull++;
    }
    if (rule.getBookingTimeTo() != null) {
      notNull++;
    }
    if (rule.getTransactionType() != null) {
      notNull++;
    }
    if (rule.getPayerMessage() != null) {
      notNull++;
    }
    if (rule.getPayeeMessage() != null) {
      notNull++;
    }
    if (rule.getCardNumber() != null) {
      notNull++;
    }

    notNull = Ruleset.class.getDeclaredFields().length / notNull;

    return notNull;
  }

  private double scoreTransactionType(String ruleTransactionType, String transactionType) {
    double score = 0;
    if (transactionType.contains(ruleTransactionType)) {
      score++;
    } else if (FuzzySearch.partialRatio(ruleTransactionType, transactionType) > threshold) {
      score++;
    }

    return score;
  }

  private double scoreConstantAndVariable(String specificSymbolValue, String specificSymbol) {
    double score = 0;
    if (specificSymbol.contains(specificSymbolValue)) {
      score += 2;
    }
    return score;
  }

  private boolean checkNullForConstantAndVariable(String specificSymbolValue, String specificSymbol) {
    return (specificSymbol != null && specificSymbolValue != null);
  }

  private boolean checkNullForScoreCard(String cardNumberValue, String cardNumber) {
    return (cardNumber != null && cardNumberValue != null);
  }

  private boolean checkNullForScoreParty(String direction, String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue, String partyDescriptionValue) {
    TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

    return (transaction != null && direction != null && transactionPartyAccount != null && partyPrefixValue != null && transactionPartyAccount.getPrefix() != null
        && partyAccountNumberValue != null && transactionPartyAccount.getAccountNumber() != null
        && partyBankCodeValue != null && transactionPartyAccount.getBankCode() != null
        && partyDescriptionValue != null && transaction != null && transaction.getPartyDescription() != null);
  }


  private double scoreCardNumber(String cardNumberValue, String cardNumber) {
    double score = 0;
    if (cardNumberValue.contains(cardNumber)) {
      score += 2;
    }
    return score;
  }

  private double scoreTransactionMessage(String rulePayeeMessage, String transactionPayeeMessage) {
    double score = 0;
    if (rulePayeeMessage != null && transactionPayeeMessage != null) {
      if (transactionPayeeMessage.contains(rulePayeeMessage) && !rulePayeeMessage.isEmpty()) {
        score++;
      } else if (FuzzySearch.partialRatio(rulePayeeMessage, transactionPayeeMessage) > threshold) {
        score++;
      }
    }
    return score;
  }

  private double scoreTransactionValue(BigDecimal valueFromValue, BigDecimal valueToValue) {
    double score = 0;
    int resolution = valueFromValue.compareTo(valueToValue);
    BigDecimal transactionValue = transaction.getValue().getAmount();

    //pokud je pravidlo mensi nez
    if (valueFromValue.compareTo(BigDecimal.valueOf(0)) == 0) {
      if (transactionValue.compareTo(valueFromValue) < 0) {
        score++;
      }
    }
    //pokud je to v rozmezi
    else if (resolution < 0) {
      if (valueFromValue.compareTo(transactionValue) < 0 && transactionValue.compareTo(valueToValue) < 0) {
        score++;
      }
    }
    //pokud je to přesně nějaký číslo
    else if (resolution == 0) {
      if (transactionValue.compareTo(valueFromValue) == 0) {
        score++;
      }
    }
    //pokud je větší než
    else if (valueToValue.equals(BigDecimal.valueOf(0))) {
      if (transactionValue.compareTo(valueFromValue) > 0) {
        score++;
      }
    }
    return score;
  }

  private double scoreTransactionDate(LocalTime bookingDateFromValue, LocalTime bookingDateToValue) {
    double score = 0;
    org.joda.time.LocalTime transactionTime = org.joda.time.LocalTime.fromDateFields(transaction.getBookingDate());
    org.joda.time.LocalTime bookingDateFromValueYoda = new org.joda.time.LocalTime(bookingDateFromValue.getHour(), bookingDateFromValue.getMinute(), bookingDateFromValue.getSecond());
    org.joda.time.LocalTime bookingDateToValueYoda = new org.joda.time.LocalTime(bookingDateToValue.getHour(), bookingDateToValue.getMinute(), bookingDateToValue.getSecond());

    if (transactionTime.isBefore(bookingDateToValueYoda) && transactionTime.isAfter(bookingDateFromValueYoda)) {
      score++;
    }
    return score;
  }

  private double scorePartyAccount(String direction, String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue, String partyDescriptionValue) {
    double score = 0;
    TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

    if (transaction != null && direction != null) {
      if (transaction.getDirection().equals(direction)) {
        score += DIRECTION_SCORE;
      }
    }

    if (transactionPartyAccount.getPrefix().contains(partyPrefixValue)) {
      score += 2;
    }
    if (transactionPartyAccount.getAccountNumber().contains(partyAccountNumberValue)) {
      score += 2;
    }
    if (transactionPartyAccount.getBankCode().contains(partyBankCodeValue)) {
      score += 2;
    }
    if (partyDescriptionValue.contains(transaction.getPartyDescription())) {
      score += 2;
    }

    return score;
  }

  private RulesetService getRulesetService() {
    return SpringContext.getBean(RulesetService.class);
  }

  private TransactionService getTransactionService() {
    return SpringContext.getBean(TransactionService.class);
  }


  private void loadAllRules() {
    // vytáhnutí pravidel z databáze
    RulesetService rulesetService = getRulesetService();
    ruleSet = rulesetService.findAllRulesets();

  }

  public CategoryScorer() {
    //for test purpose only

  }

  public ArrayList<Category> findCategoriesForTransaction() {
    ArrayList<Category> list = new ArrayList<>();
    TransactionService transactionService = getTransactionService();
    List<Transaction> transactionList = transactionService.findAllTransactions();
    for (Transaction transaction : transactionList) {
      if (transaction.getCategoryId() != null) {
        category = scoreCategories(transaction);
        transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
        Category cat = new Category(category, transaction.getId());
        list.add(cat);
      }
    }
    return list;
  }

  public void setListRuleset(List<Ruleset> ruleSetList) {
    ruleSet = ruleSetList;
  }
}
