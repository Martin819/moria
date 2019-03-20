package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
import moria.dto.Category;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
import moria.services.RulesetService;
import moria.services.TransactionService;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class CategoryScorer {

    private List<Ruleset> ruleSet;
    private Transaction transaction;

    //for test purpose only
    public int category;

    /**
     * Evalute category based on provided Transaction
     *
     * @param transaction provided transaction for evaluation
     * @return ID of category from Enum or 0 - algorithm cant find proper category
     */

    public int scoreCategories(Transaction transaction) {
        loadAllRules();
        this.transaction = transaction;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        TreeMap<Double, Integer> categories = new TreeMap<>();
        for (Ruleset rule : ruleSet) {
            double score = 0;
            if (checkNullForScoreParty(rule.getDirection(), rule.getPartyPrefixValue(), rule.getPartyAccountNumberValue(), rule.getPartyBankCodeValue(), rule.getPartyDescriptionValue())){
                score += scorePartyAccount(rule.getDirection(), rule.getPartyPrefixValue(), rule.getPartyAccountNumberValue(), rule.getPartyBankCodeValue(), rule.getPartyDescriptionValue());
            }

            if (rule.getBookingDateFromValue() != null && rule.getBookingDateToValue() != null) score += scoreTransactionDate(rule.getBookingDateFromValue(), rule.getBookingDateToValue());
            if (rule.getValueFromValue() != null && rule.getValueToValue() != null) score += scoreTransactionValue(rule.getValueFromValue(), rule.getValueToValue());
            if (transaction.getDirection().equals("INCOMING"))
                score += scoreTransactionMessage(rule.getPayerMessageValue(), transaction.getPayerMessage());
            if (transaction.getDirection().equals("OUTGOING"))
                score += scoreTransactionMessage(rule.getPayeeMessageValue(), transaction.getPayeeMessage());
            if (transaction.getAdditionalInfoCard() != null ) if (checkNullForScoreCard(rule.getCardNumberValue(), transaction.getAdditionalInfoCard().getCardNumber())){
                score += scoreCardNumber(rule.getCardNumberValue(), transaction.getAdditionalInfoCard().getCardNumber());
            }
            if (transaction.getAdditionalInfoDomestic() != null ) if (checkNullForConstantAndVariable(rule.getSpecificSymbolValue(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())){
                score += scoreConstantAndVariable(rule.getSpecificSymbolValue(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
            }
            if (rule.getTransactionType() != null && transaction.getTransactionType() != null){
                score += scoreTransactionType(rule.getTransactionType(), transaction.getTransactionType());
            }

            //počítá počet vyplněných pravidel a poté je jejich převrácenou hodnotou celého počtu násobí skore (pro zvýhodnění malého počtu vyplněných položek v rulesetu)
            double coefficient = findCoefficientBasedOnNumberOfParameters(rule);
            score = score * coefficient;
            categories.put(score, rule.getCategoryId());
        }

        if (categories.lastKey() == 0) {
            return 0;
        } else {
            return categories.lastEntry().getValue();
        }
    }

    private double findCoefficientBasedOnNumberOfParameters(Ruleset rule) {
        double notNull = 0;
        if (rule.getValueFromValue() != null) notNull++;
        if (rule.getValueToValue() != null) notNull++;
        if (rule.getPartyPrefixValue() != null) notNull++;
        if (rule.getPartyAccountNumberValue() != null) notNull++;
        if (rule.getPartyBankCodeValue() != null) notNull++;
        if (rule.getPartyDescriptionValue() != null) notNull++;
        if (rule.getBookingDateFromValue() != null) notNull++;
        if (rule.getBookingDateToValue() != null) notNull++;
        if (rule.getTransactionType() != null) notNull++;
        if (rule.getUserDescriptionValue() != null) notNull++;
        if (rule.getPayerMessageValue() != null) notNull++;
        if (rule.getPayeeMessageValue() != null) notNull++;
        if (rule.getMerchantNameValue() != null) notNull++;
        if (rule.getCardNumberValue() != null) notNull++;

        notNull = 13 / notNull;

        return notNull;


    }

    private double scoreTransactionType(String ruleTransactionType, String transactionType) {
        double score = 0;
        if (transactionType.contains(ruleTransactionType)){
            score += 2;
        } else if (FuzzySearch.partialRatio(ruleTransactionType, transactionType) > 50) {
            score++;
        }

        return score;
    }

    private double scoreConstantAndVariable(String specificSymbolValue, String specificSymbol) {
        double score = 0;
        if (specificSymbol.contains(specificSymbolValue)) score += 2;
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
         if (cardNumberValue.contains(cardNumber)) score += 2;
        return score;
    }

    private double scoreTransactionMessage(String rulePayeeMessage, String transactionPayeeMessage) {
        double score = 0;
        if (rulePayeeMessage != null && transactionPayeeMessage != null) {
            if (transactionPayeeMessage.contains(rulePayeeMessage)) {
                score++;
            } else if (FuzzySearch.partialRatio(rulePayeeMessage, transactionPayeeMessage) > 50) {
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
            if (transactionValue.compareTo(valueFromValue) < 0 && transactionValue.compareTo(valueToValue) < 0) {
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

    private double scoreTransactionDate(Date bookingDateFromValue, Date bookingDateToValue) {
        double score = 0;
        Interval interval = new Interval(bookingDateFromValue.getTime(), bookingDateToValue.getTime());
        Duration duration = interval.toDuration();

        //kdyz je to v nějaký konkretni čas
        if (duration.toStandardMinutes().getMinutes() < 60) {
            Interval differenceBettweenRuleAndTransaction = new Interval(bookingDateFromValue.getTime(), transaction.getBookingDate().getTime());
            Duration durationBettweenRuleAndTransaction = differenceBettweenRuleAndTransaction.toDuration();
            if (durationBettweenRuleAndTransaction.toStandardMinutes().getMinutes() < 60) {
                score++;
            }
            //když je to do nějakého času
        } else if (bookingDateToValue == null) {
            if (transaction.getBookingDate().after(bookingDateFromValue)) {
                score++;
            }
            //když je to do nějakého času
        } else if (bookingDateFromValue == null) {
            if (transaction.getBookingDate().before(bookingDateToValue)) {
                score++;
            }
            //když je to v rozmezi nějakého času
        } else if (duration.toStandardMinutes().getMinutes() > 60) {
            Date bookingDate = transaction.getBookingDate();
            if (bookingDate.after(bookingDateFromValue) && bookingDate.before(bookingDateToValue)) {
                score++;
            }
        }
        return score;
    }

    private double scorePartyAccount(String direction, String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue, String partyDescriptionValue) {
        double score = 0;
        TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

        if (transaction != null && direction != null)
            if (transaction.getDirection().equals(direction)) score += 2;

        if (transactionPartyAccount.getPrefix().contains(partyPrefixValue)) score += 2;
        if (transactionPartyAccount.getAccountNumber().contains(partyAccountNumberValue)) score += 2;
        if (transactionPartyAccount.getBankCode().contains(partyBankCodeValue)) score += 2;
        if (partyDescriptionValue.contains(transaction.getPartyDescription())) score += 2;

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
        int max = 0;
        TransactionService transactionService = getTransactionService();
        Transaction transaction2 = transactionService.findTransactionById(1);
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            category = scoreCategories(transaction);
            if (category > max) max = category;
        }
        category = max;
    }

    public ArrayList<Category> findCategoriesForTransaction() {
        ArrayList<Category> list = new ArrayList<>();
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            category = scoreCategories(transaction);
            transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
            Category cat = new Category(category, transaction.getId());
            list.add(cat);
        }
        return list;
    }
}
