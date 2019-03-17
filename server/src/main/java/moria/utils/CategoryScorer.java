package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
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

            score += scoreTransactionDate(rule.getBookingDateFromValue(), rule.getBookingDateToValue());
            score += scoreTransactionValue(rule.getValueFromValue(), rule.getValueToValue());
            if (transaction.getDirection().equals("INCOMING"))
                score += scoreTransactionMessage(rule.getPayerMessageValue(), transaction.getPayerMessage());
            if (transaction.getDirection().equals("OUTGOING"))
                score += scoreTransactionMessage(rule.getPayeeMessageValue(), transaction.getPayeeMessage());
            if (transaction.getAdditionalInfoCard() != null ) if (checkNullForScoreCard(rule.getCardNumberValue(), transaction.getAdditionalInfoCard().getCardNumber())){
                score += scoreCardNumber(rule.getCardNumberValue(), transaction.getAdditionalInfoCard().getCardNumber());
            }
            if (transaction.getAdditionalInfoDomestic() != null ) if (checkNullForConstantAndVarialbe(rule.getSpecificSymbolValue(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())){
                score += scoreConstantAndVariable(rule.getSpecificSymbolValue(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
            }

//            String categoryName = rule.getUserDescriptionValue();
            categories.put(score, rule.getCategoryId());
        }

        if (categories.firstEntry().getKey() == 0) {
            return 0;
        } else {
            return categories.firstEntry().getValue();
        }
    }

    private double scoreConstantAndVariable(String specificSymbolValue, String specificSymbol) {
        double score = 0;
        if (specificSymbol.contains(specificSymbolValue)) score += 2;
        return score;
    }

    private boolean checkNullForConstantAndVarialbe(String specificSymbolValue, String specificSymbol) {
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

//        switch (valueFromOperator) {
//            case "=":
//                int resolution = valueFromValue.compareTo(transaction.getValue().getAmount());
//                if (resolution == 0) {
//                    score++;
//                }
//                break;
//            case "<":
//                resolution = valueFromValue.compareTo(transaction.getValue().getAmount());
//                if (resolution == 1) {
//                    score++;
//                }
//                break;
//            case ">":
//                resolution = valueFromValue.compareTo(transaction.getValue().getAmount());
//                if (resolution == 2) {
//                    score++;
//                }
//                break;
//            case "BETWEEN":
//                BigDecimal transactionValue = transaction.getValue().getAmount();
//                if (valueFromValue.compareTo(transactionValue) < 0 && transactionValue.compareTo(valueToValue) < 0) {
//                    score++;
//                }
//                break;
//        }
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
        Transaction transactionaa = transactionService.findTransactionById(1);
        transactionService.setCategoryIdForTransactionById(1,11);
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            category = scoreCategories(transaction);
            if (category > max) max = category;
        }
        category = max;
    }

    public ArrayList<Integer> findCategoriesForTransaction() {
        ArrayList<Integer> list = new ArrayList<>();
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            if (transaction.getCategoryId() == null) {
                category = scoreCategories(transaction);
                if (category != 0) {
                    transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
                    list.add(category);
                }
            }
        }
        return list;
    }
}
