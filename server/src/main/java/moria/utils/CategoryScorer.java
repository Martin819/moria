package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
import moria.dto.Category;
import moria.dto.Rule;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
import moria.services.RulesetService;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

public class CategoryScorer {

    private HashMap<String, ArrayList<String>> categories = new HashMap<>();
    private HashMap<String, Double> scoredCategories = new HashMap<>();
    private ArrayList<Rule> allRules = new ArrayList<>();

    private Category category = new Category();
    private HashMap<String, ArrayList<String>> dummyCategories = new HashMap<>();

    ///////////////////////
    private List<Ruleset> ruleSet;
    private Transaction transaction;

    public String scoreCategories(Transaction transaction){
        loadAllRules();
        this.transaction = transaction;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
//        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
//            String categoryName = entry.getKey();
//            scoredCategories.put(categoryName, 0);
//        }

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        TreeMap<Double, String> categories = new TreeMap<>();
        for (Ruleset rule : ruleSet) {
            double score = 0;
            score += scorePartyAccount(rule.getDirection(), rule.getPartyPrefixValue(), rule.getPartyAccountNumberValue(), rule.getPartyBankCodeValue(), rule.getPartyDescriptionValue());
            score += scoreTransactionDate(rule.getBookingDateFromValue(), rule.getBookingDateToValue());
            score += scoreTransactionValue(rule.getValueFromValue(), rule.getValueToValue());
            if (transaction.getDirection().equals("INCOMING"))
                score += scoreTransactionMessage(rule.getPayerMessageValue(), transaction.getPayerMessage());
            if (transaction.getDirection().equals("OUTCOMMING"))
                score += scoreTransactionMessage(rule.getPayeeMessageValue(), transaction.getPayeeMessage());
//            score += scorePayeeName(rule.get)


          String categoryName = rule.getUserDescriptionValue();
//            scoredCategories.put(categoryName, score);


            categories.put(score, categoryName);
        }

//        String retailer = payment.getPayer(); //obchodnik z prichozi platby
//
//        scorePropabilityOfRetailers(retailer); //přidá bod ke kategoriim, ve kterých se obchodnik nachazi
//
//        scorePaymentBasedOnCustomerRules(payment); //prohleda pravidla od uzivatele a mrkne, zdali je tam nějaká shoda

        //Najdu nejvyssi hodnoceni ve scoredCategories (pokud jsou 2 a vice se stejným, beru to prvni) -> to je nase kategorie kde se obchodnik nachazi
//        String categoryWithHighestPropability = "";
//        double maxValue = 0;
//        for (Map.Entry<String, Double> entry : scoredCategories.entrySet()) {
//            Double score = entry.getValue();
//            String retailers = entry.getKey();
//            if (score > maxValue) {
//                maxValue = score;
//                categoryWithHighestPropability = retailers;
//            }
//        }
//        return categoryWithHighestPropability;

        return String.valueOf(categories.firstEntry().getValue());
    }

    private double scoreTransactionMessage(String rulePayeeMessage, String transactionPayeeMessage) {
        double score = 0;
        if (transactionPayeeMessage.contains(rulePayeeMessage)) {
            score++;
        } else if (FuzzySearch.partialRatio(rulePayeeMessage, transactionPayeeMessage) > 50) {
            score++;
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
        else if (valueToValue == null) {
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
        if (Integer.valueOf(duration.toStandardMinutes().toString()) < 60) {
            Interval differenceBettweenRuleAndTransaction = new Interval(bookingDateFromValue.getTime(), transaction.getBookingDate().getTime());
            Duration durationBettweenRuleAndTransaction = differenceBettweenRuleAndTransaction.toDuration();
            if (Integer.valueOf(durationBettweenRuleAndTransaction.toStandardMinutes().toString()) < 60) {
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
        } else if (Integer.valueOf(duration.toStandardMinutes().toString()) > 60) {
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

        if (transaction.getDirection().equals(direction)) score += 2;
        if (transactionPartyAccount.getPrefix().contains(partyPrefixValue)) score += 2;
        if (transactionPartyAccount.getAccountNumber().contains(partyAccountNumberValue)) score += 2;
        if (transactionPartyAccount.getBankCode().contains(partyBankCodeValue)) score += 2;

        return score;
    }

//    /**
//     * mame obchodnika z prichozi platby - projedu vsechny obchodniky, ktery mam v kategoriich a hledam, zdali se nějaká 2 jména neshoduji
//     * pokud se shodují, vlozim si je do foundCategories
//     *
//     * @param retailer hledany obchodnik v kategoriich
//     */
//    private void scorePropabilityOfRetailers(String retailer) {
//        ArrayList<String> foundCategories = new ArrayList<>();
//        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
//
//            String category = entry.getKey();
//            ArrayList<String> retailers = entry.getValue();
//
//            if (retailers != null) {
//                for (String retailerName : retailers) {
//                    //tohlento umi vyhledat nazev obchodnika v souveti -> najde napr. Billa v blabla Billa bla bla
//                    if ( retailer.matches(".*" + retailerName +".*")) foundCategories.add(category);
//                }
//            }
//        }
//
//        //tyto kategorie pak dostanou +1 bod v scoredCategories hashmape
//        for (String categoryName : foundCategories) {
//            int score = scoredCategories.get(categoryName);
//            scoredCategories.put(categoryName, score + 1);
//        }
//    }
//
//
//    /**
//     * Prohleda všechny definovany pravidla a pokud maji nějakou část společnou s uživatelsky definovanými, přičte k nim nějaký skore
//     * @param payment hledaná platba
//     */
//    private void scorePaymentBasedOnCustomerRules(Payment payment) throws ParseException {
//
//        HashMap<String, String> singleRule = new HashMap<>();
//        for (Rule rule: allRules){
//            int score = 0;
//            singleRule = rule.getRules();
//            String payer = singleRule.get("payer");
//
//            if ( payment.getPayer().matches(".*" + payer +".*")) score++;  //pokud se shoduje platce (nebo alespoň obsahuje tu část)
//
//            double transferMoney = Double.parseDouble(singleRule.get("transferMoney"));
//            score = decideTheRule(singleRule.get("compare"), transferMoney, payment.getTransferMoney(), score); //pokud vyhovuje výše platby
//
//            if (payment.isIncomingPayment() == Boolean.valueOf(singleRule.get("incomingPayment"))) score++; //pokud je prichozi/odchozi stejne jako v pravidle
//
////            DateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
////            if (payment.getDateOfPayment().after(dateFormat.parse(singleRule.get("dateOfPayment")))) score += 0.5; //pokud je platba po zacatku platnosti pravidla - pomocne hodnoceni
//
//            scoredCategories.put(rule.getRuleName(), score);
//        }
//    }
//
//    /**
//     *  Podle compare v pravidlu rozpozna, zdali to tomu pravidlu vyhovuje (připočítává se jenom 0,5 pro pomocne rozhodovani)
//     * @param compare oznaceni, zdali je platba vyssi (>), mensi (<), nebo rovna (=)
//     * @param ruleForTransferedMoney hodnota z pravidla
//     * @param moneyFromPayment hodnota z posuzovane platby
//     * @param score skore pravdepodobnosti daneho pravidla
//     * @return vraci skore zvysene o 1, pokud to vyhovi danemu pravidlu
//     */
//    private int decideTheRule(String compare, double ruleForTransferedMoney, double moneyFromPayment, int score ) {
//        switch (compare){
//            case ">":
//                if (moneyFromPayment > ruleForTransferedMoney) score += 0.5;
//            break;
//
//            case "<":
//                if (moneyFromPayment < ruleForTransferedMoney)  score += 0.5;
//            break;
//
//            case "=":
//                if (moneyFromPayment == ruleForTransferedMoney)  score += 0.5;
//        }
//        return score;
//    }

    private RulesetService getRulesetService() {
        return SpringContext.getBean(RulesetService.class);
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
