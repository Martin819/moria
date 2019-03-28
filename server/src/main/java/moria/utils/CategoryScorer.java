package moria.utils;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

public class CategoryScorer {

    private static final int threshold = 75; //pro fuzzy
    private List<Ruleset> ruleset;
    private Transaction transaction;
    private boolean isBankAccountDifferentAndFilled; //pokud je v pravidle vyplněno číslo účtu, musí mít transakce stejný číslo - pokud nema, hodi ji na true a hodí skore kategorie na 0

    /**
     * Evalute categoryId based on provided Transaction
     *
     * @param transaction provided transaction for evaluation
     * @return categoryId or 0 (in case algorithm could't find proper categoryId)
     */

    public int scorePossibleCategories(Transaction transaction) {
        this.transaction = transaction;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        TreeMap<Double, Integer> possibleCategories = new TreeMap<>();
        for (Ruleset rule : ruleset) {
            double score = 0;
            isBankAccountDifferentAndFilled = false;
            boolean doPaymentAndRuleDirectionMatch = true;
            if (transaction.getDirection().equals("INCOMING")) {
                if (rule.getDirection().equals("OUTGOING")) {
                    doPaymentAndRuleDirectionMatch = false;
                }
                if (doPaymentAndRuleDirectionMatch) {
                    score += scorePayeeMessage(rule.getPayeeMessage(), transaction.getPayeeMessage());
                }
            }
            if (transaction.getDirection().equals("OUTGOING")) {
                if (rule.getDirection().equals("INCOMING")) {
                    doPaymentAndRuleDirectionMatch = false;
                }
                if (doPaymentAndRuleDirectionMatch) {
                    score += scorePayeeMessage(rule.getPayerMessage(), transaction.getPayerMessage());
                }

            }
            if (doPaymentAndRuleDirectionMatch) {
                if (checkNullScoreParty(rule.getPartyAccountPrefix(), rule.getPartyAccountNumber(), rule.getPartyBankCode())) {
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
                    if (checkNullScoreCard(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber())) {
                        score += scoreCardNumber(rule.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber());
                    }
                }
                if (transaction.getAdditionalInfoDomestic() != null) {
                    if (checkNullSymbol(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())) {
                        score += scoreAnySymbol(rule.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
                    }
                    if (checkNullSymbol(rule.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol())) {
                        score += scoreAnySymbol(rule.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol());
                    }
                    if (checkNullSymbol(rule.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol())) {
                        score += scoreAnySymbol(rule.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol());
                    }
                }
                if (rule.getTransactionType() != null && transaction.getTransactionType() != null) {
                    score += scoreTransactionType(rule.getTransactionType(), transaction.getTransactionType());
                }

                //podminka kvuli vybrani kategorie na zakladě incomming/outgoing
                if (isBankAccountDifferentAndFilled) {
                    score = 0;
                }

                //zjistí kolik pravidel v rulesetu je vyplněných a poté převrácenou hodnotou tohoto počtu vynásobí skore (pro zvýhodnění malého počtu vyplněných pravidel v rulesetu)
                double coefficient = getNotNullRulesCount(rule);
                score = score * coefficient;
                possibleCategories.put(score, rule.getCategoryId());
            }
        }

        if (!possibleCategories.isEmpty()) {
            if (possibleCategories.lastKey() == 0 || possibleCategories.isEmpty()) {
                return 0;
            } else {
                return possibleCategories.lastEntry().getValue();
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
     * Count how many rules of a ruleset are not null
     *
     * @param ruleset stored in database
     * @return +1 for every not null rule
     */
    private double getNotNullRulesCount(Ruleset ruleset) { // vrati kolik pravidel je v rulesetu vyplnenych
        double NotNullRulesCount = 2; //id a direction ohsahuje ruleset vzdy

        if (ruleset.getValueFrom() != null) NotNullRulesCount++;
        if (ruleset.getValueTo() != null) NotNullRulesCount++;
        if (ruleset.getPartyAccountPrefix() != null) NotNullRulesCount++;
        if (ruleset.getPartyAccountNumber() != null) NotNullRulesCount++;
        if (ruleset.getPartyBankCode() != null) NotNullRulesCount++;
        if (ruleset.getPartyName() != null) NotNullRulesCount++;
        if (ruleset.getBookingTimeFrom() != null) NotNullRulesCount++;
        if (ruleset.getBookingTimeTo() != null) NotNullRulesCount++;
        if (ruleset.getTransactionType() != null) NotNullRulesCount++;
        if (ruleset.getPayerMessage() != null) NotNullRulesCount++;
        if (ruleset.getPayeeMessage() != null) NotNullRulesCount++;
        if (ruleset.getConstantSymbol() != null) NotNullRulesCount++;
        if (ruleset.getVariableSymbol() != null) NotNullRulesCount++;
        if (ruleset.getSpecificSymbol() != null) NotNullRulesCount++;
        if (ruleset.getCardNumber() != null) NotNullRulesCount++;

        NotNullRulesCount = Ruleset.class.getDeclaredFields().length / NotNullRulesCount;

        return NotNullRulesCount;
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

    private double scoreAnySymbol(String symbolValue, String symbol) { // jedna metoda stejna pro konstantni, variabilni i specificky symbol
        double score = 0;
        if (symbol.contains(symbolValue)) {
            score += 2;
        }
        return score;
    }

    private boolean checkNullSymbol(String symbolValue, String symbol) { // jedna metoda stejna pro konstantni, variabilni i specificky symbol
        return (symbol != null && symbolValue != null);
    }

    private boolean checkNullScoreCard(String cardNumberValue, String cardNumber) {
        return (cardNumber != null && cardNumberValue != null);
    }

    private boolean checkNullScoreParty(String partyPrefixValue, String partyAccountNumberValue, String partyBankCodeValue) {
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

    private double scorePayeeMessage(String rulePayeeMessage, String transactionPayeeMessage) {
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

    private double scoreTransactionValue(BigDecimal valueFrom, BigDecimal valueTo) {
        double score = 0;
        //neni vyplneno
        if (valueFrom == null && valueTo == null){
            return score;
        }
        //castka do
        else if (valueFrom == null && valueTo != null){
            if (transaction.getValue().getAmount().compareTo(valueTo) < 0){
                score++;
            }
        }
        //castka od
        else if (valueFrom != null && valueTo == null){
            if (transaction.getValue().getAmount().compareTo(valueFrom) > 0){
                score++;
            }
        }
        //castka mezi
        else {
            if (transaction.getValue().getAmount().compareTo(valueFrom) > 0 && transaction.getValue().getAmount().compareTo(valueTo) < 0){
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


    public CategoryScorer(List<Ruleset> ruleset) {
        this.ruleset = ruleset;
    }
}
