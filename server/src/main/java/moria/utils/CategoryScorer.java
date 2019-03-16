package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
import moria.dto.Category;
import moria.dto.Rule;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
import moria.services.RulesetService;
import moria.services.TransactionService;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

public class CategoryScorer {

    private HashMap<String, ArrayList<String>> categories = new HashMap<>();
    private HashMap<String, Double> scoredCategories = new HashMap<>();

    private HashMap<String, ArrayList<String>> dummyCategories = new HashMap<>();

    private List<Ruleset> ruleSet;
    private Transaction transaction;

    //for test only
    public int category;

    public int scoreCategories(Transaction transaction) {
        loadAllRules();
        this.transaction = transaction;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
//        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
//            String categoryName = entry.getKey();
//            scoredCategories.put(categoryName, 0);
//        }

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        TreeMap<Double, Integer> categories = new TreeMap<>();
        for (Ruleset rule : ruleSet) {
            double score = 0;
            score += scorePartyAccount(rule.getDirection(), rule.getPartyPrefixValue(), rule.getPartyAccountNumberValue(), rule.getPartyBankCodeValue(), rule.getPartyDescriptionValue());
            score += scoreTransactionDate(rule.getBookingDateFromValue(), rule.getBookingDateToValue());
            score += scoreTransactionValue(rule.getValueFromValue(), rule.getValueToValue());
            if (transaction.getDirection().equals("INCOMING"))
                score += scoreTransactionMessage(rule.getPayerMessageValue(), transaction.getPayerMessage());
            if (transaction.getDirection().equals("OUTCOMMING"))
                score += scoreTransactionMessage(rule.getPayeeMessageValue(), transaction.getPayeeMessage());

            String categoryName = rule.getUserDescriptionValue();
//            scoredCategories.put(categoryName, score);
            categories.put(score, rule.getCategoryId());
        }

        if (categories.firstEntry().getKey() == 0) {
            return 0;
        } else {
            return categories.firstEntry().getValue();
        }
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

        if (transactionPartyAccount != null && partyPrefixValue != null && transactionPartyAccount.getPrefix() != null)
            if (transactionPartyAccount.getPrefix().contains(partyPrefixValue)) score += 2;
        if (transactionPartyAccount != null && partyAccountNumberValue != null && transactionPartyAccount.getAccountNumber() != null)
            if (transactionPartyAccount.getAccountNumber().contains(partyAccountNumberValue)) score += 2;
        if (transactionPartyAccount != null && partyBankCodeValue != null && transactionPartyAccount.getBankCode() != null)
            if (transactionPartyAccount.getBankCode().contains(partyBankCodeValue)) score += 2;

        return score;
    }

    private RulesetService getRulesetService() {
        return SpringContext.getBean(RulesetService.class);
    }

    private TransactionService getTransactionService() {
        return SpringContext.getBean(TransactionService.class);
    }


    private void loadAllRules() {

//        //dummy data for Rules
//        HashMap<String, String> dummyRule = new HashMap<>();
//        dummyRule.put("payer", "Decathlon");
//        dummyRule.put("transferMoney", "500");
//        dummyRule.put("compare", ">");
//        dummyRule.put("incomingPayment", "false");
//        Date date = new Date();
//        dummyRule.put("dateOfPayment", String.valueOf(date));
//        dummyRule.put("description", "nakup funkcniho obleceni Decathlon");
//
//        Rule rule = new Rule("Sport", "věci na běhání", dummyRule);
//
//        HashMap<String, String> dummyRule2 = new HashMap<>();
//        dummyRule2.put("payer", "Shell");
//        dummyRule2.put("compare", ">");
//        dummyRule2.put("transferMoney", "1200");
//        dummyRule2.put("incomingPayment", "false");
//        dummyRule2.put("dateOfPayment", String.valueOf(date));
//        dummyRule2.put("description", "shell PowerV");
//
//        Rule rule2 = new Rule("Pohonne hmoty", "nakup benzinu do autaku", dummyRule2);
//
//        allRules.add(rule);
//        allRules.add(rule2);

        // vytáhnutí pravidel z databáze
        RulesetService rulesetService = getRulesetService();
        ruleSet = rulesetService.findAllRulesets();
    }

    public CategoryScorer() {

        //for test purpose only
        int max = 0;
        TransactionService transactionService = getTransactionService();
//        Transaction transaction = transactionService.findTransactionById("1");
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            category = scoreCategories(transaction);
            if (category > max) max = category;
        }
        category = max;

//        //dummy data (tohle jakoby budeme mit (preddefinovany pravidla)
//        ArrayList<String> supermarkets = new ArrayList<>();
//        supermarkets.add("Billa");
//        supermarkets.add("Kaufland");
//        supermarkets.add("Penny Market");
//        dummyCategories.put("Jidlo", supermarkets);
//
//        ArrayList<String> retailers = new ArrayList<>();
//        retailers.add("C&A");
//        retailers.add("H&M");
//        dummyCategories.put("Oblečení", retailers);
//
//        categories = dummyCategories;
//
//        dummyCategories.put("Nájem", null);
//        dummyCategories.put("Výplata", null);
//        dummyCategories.put("Úroky", null);
//        category.setCategories(dummyCategories);
    }
}
