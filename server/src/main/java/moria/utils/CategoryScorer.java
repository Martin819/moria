package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.SpringContext;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
import moria.services.RulesetService;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

public class CategoryScorer {

    private static final int threshold = 75; //pro fuzzy
    private List<Ruleset> ruleSet;
    private Transaction transaction;
    private boolean isBankAccountDifferentAndFilled; //pokud je v pravidle vyplněno číslo účtu musí mít transakce stejný číslo - pokud nema, hodi ji na true a hodí skore kategorie na 0

    /**
     * Evalute categoryId based on provided Transaction
     *
     * @param transaction provided transaction for evaluation
     * @return ID of categoryId from Enum or 0 - algorithm cant find proper categoryId
     */

    public int scoreCategories(Transaction transaction) {
        this.transaction = transaction;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        TreeMap<Double, Integer> categories = new TreeMap<>();
        for (Ruleset rule : ruleSet) {
            double score = 0;
            isBankAccountDifferentAndFilled = false;
            boolean isRuleDirecetionCorrect = true;
            if (transaction.getDirection().equals("INCOMING")) {
                if (rule.getDirection().equals("OUTGOING")) {
                    isRuleDirecetionCorrect = false;
                }
                if (isRuleDirecetionCorrect) {
                    score += scoreTransactionMessage(rule.getPayeeMessage(), transaction.getPayeeMessage());
                }
            }
            if (transaction.getDirection().equals("OUTGOING")) {
                if (rule.getDirection().equals("INCOMING")) {
                    isRuleDirecetionCorrect = false;
                }
                if (isRuleDirecetionCorrect) {
                    score += scoreTransactionMessage(rule.getPayerMessage(), transaction.getPayerMessage());
                }

            }
            if (isRuleDirecetionCorrect) {
                if (checkNullForScoreParty(rule.getPartyAccountPrefix(), rule.getPartyAccountNumber(), rule.getPartyBankCode())) {
                    score += scorePartyAccount(rule.getPartyAccountPrefix(), rule.getPartyAccountNumber(), rule.getPartyBankCode());
                }

                if (rule.getPartyName() != null) {
                    score += scorePartyName(rule.getPartyName());
                }

                if (rule.getBookingTimeFrom() != null && rule.getBookingTimeTo() != null) {
                    score += scoreTransactionDate(LocalTime.parse(rule.getBookingTimeFrom()), LocalTime.parse(rule.getBookingTimeTo()));
                }
                score += scoreTransactionValue(rule.getValueFrom(), rule.getValueTo());

                if (transaction.getAdditionalInfoCard() != null) {
                    if (checkNullForScoreCard(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber())) {
                        score += scoreCardNumber(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber());
                    }
                }
                if (transaction.getAdditionalInfoDomestic() != null) {
                    if (checkNullForConstantAndVariable(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())) {
                        score += scoreConstantAndVariable(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
                    }
                    if (checkNullForConstantAndVariable(rule.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol())) {
                        score += scoreConstantAndVariable(rule.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol());
                    }
                    if (checkNullForConstantAndVariable(rule.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol())) {
                        score += scoreConstantAndVariable(rule.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol());
                    }
                }
                if (rule.getTransactionType() != null && transaction.getTransactionType() != null) {
                    score += scoreTransactionType(rule.getTransactionType(), transaction.getTransactionType());
                }

                //podminka kvuli vybrani kategorie na zakladě incomming/outgoing
                if (isBankAccountDifferentAndFilled) {
                    score = 0;
                }

                //počítá počet vyplněných pravidel a poté je jejich převrácenou hodnotou celého počtu násobí skore (pro zvýhodnění malého počtu vyplněných položek v rulesetu)
                double coefficient = findCoefficientBasedOnNumberOfParameters(rule);
                score = score * coefficient;
                categories.put(score, rule.getCategoryId());
            }
        }

        if (!categories.isEmpty()) {
            if (categories.lastKey() == 0 || categories.isEmpty()) {
                return 0;
            } else {
                return categories.lastEntry().getValue();
            }
        } else {
            return 0;
        }

    }

    private double scorePartyName(String partyName) {
        double score = 0;
        String partyDescription = transaction.getPartyDescription();
        if (partyDescription != null && (partyDescription.contains(partyName))) score++;
        return score;
    }

    /**
     * check if parameters of ruleset are null
     *
     * @param rule from Ruleset used in database
     * @return reverted value of not null parameters
     */
    private double findCoefficientBasedOnNumberOfParameters(Ruleset rule) {
        double notNull = 2; //id a direction je tam vzdy

        if (rule.getValueFrom() != null) notNull++;
        if (rule.getValueTo() != null) notNull++;
        if (rule.getPartyAccountPrefix() != null) notNull++;
        if (rule.getPartyAccountNumber() != null) notNull++;
        if (rule.getPartyBankCode() != null) notNull++;
        if (rule.getPartyName() != null) notNull++;
        if (rule.getBookingTimeFrom() != null) notNull++;
        if (rule.getBookingTimeTo() != null) notNull++;
        if (rule.getTransactionType() != null) notNull++;
        if (rule.getPayerMessage() != null) notNull++;
        if (rule.getPayeeMessage() != null) notNull++;
        if (rule.getConstantSymbol() != null) notNull++;
        if (rule.getVariableSymbol() != null) notNull++;
        if (rule.getSpecificSymbol() != null) notNull++;
        if (rule.getCardNumber() != null) notNull++;

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

    private boolean checkNullForScoreParty(String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue) {
        TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

        return (transaction != null && transactionPartyAccount != null && partyPrefixValue != null && transactionPartyAccount.getPrefix() != null
                && partyAccountNumberValue != null && transactionPartyAccount.getAccountNumber() != null
                && partyBankCodeValue != null && transactionPartyAccount.getBankCode() != null
                && transaction != null);
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
        //neni vyplneno
        if (valueFromValue == null && valueToValue == null){
            return score;
        }
        //castka do
        else if (valueFromValue == null && valueToValue != null){
            if (transaction.getValue().getAmount().compareTo(valueToValue) < 0){
                score++;
            }
        }
        //castka od
        else if (valueFromValue != null && valueToValue == null){
            if (transaction.getValue().getAmount().compareTo(valueFromValue) > 0){
                score++;
            }
        }
        //castka mezi
        else {
            if (transaction.getValue().getAmount().compareTo(valueFromValue) > 0 && transaction.getValue().getAmount().compareTo(valueToValue) < 0){
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

    private double scorePartyAccount(String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue) {
        double score = 0;
        TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

        if (transactionPartyAccount.getPrefix().contains(partyPrefixValue) && transactionPartyAccount.getAccountNumber().contains(partyAccountNumberValue)
                && transactionPartyAccount.getBankCode().contains(partyBankCodeValue)) {
            score += 100;
        } else if (!partyAccountNumberValue.isEmpty()) {
            isBankAccountDifferentAndFilled = true;
        }

        return score;
    }


    public CategoryScorer(List<Ruleset> ruleSet) {
        this.ruleSet = ruleSet;
    }
}
