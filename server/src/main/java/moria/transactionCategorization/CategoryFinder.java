package moria.transactionCategorization;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.TreeMap;

public class CategoryFinder {

    private static final int THRESHOLD = 75; //pro fuzzy
    private List<Ruleset> ruleset;
    private Transaction transaction;

    /**
     * Find best matching categoryId for provided Transaction.
     *
     * @param transaction transaction to be categorized
     * @return categoryId or 0 (in case algorithm could't find proper categoryId)
     */

    public int findBestMatchingCategory(Transaction transaction) {
        this.transaction = transaction;
        TreeMap<Double, Integer> possibleCategories = new TreeMap<>();

        /*  Metoda pro každou transakci projde všechny rulesety a zvyšováním skóre vyjádří míru shody (vhodnosti použití) rulesetu a transakce.
            Výstupy ukládá do TreeMapy ve tvaru <score, categoryID>
            Nejvyšší skóre (tzn. nejvyšší podobnost pravidla s platbou) vyhrává a metoda vrátí ID odpovídající kategorie */

        for (Ruleset ruleset : ruleset) {

            // pokud se smery platby shoduji, pokracuju dal k vyhodnoceni
            if (ruleset.getDirection().equals(transaction.getDirection())) {
                compareParametersAndUpdateScore(ruleset, transaction, possibleCategories);
            }
            // pokud ne, nedelam nic (zadny vystup do hashmapy)
        }

        if (!possibleCategories.isEmpty()) {
            if (possibleCategories.lastKey() == 0) {
                return 0;
            } else {
                return possibleCategories.lastEntry().getValue();
            }
        } else {
            return 0;
        }
    }

    private void compareParametersAndUpdateScore(Ruleset ruleset, Transaction transaction, TreeMap<Double, Integer> possibleCategories) {
        double totalScore = 0;
        int notNullRulesCount = getNotNullRulesCount(ruleset);

        if (ruleset.getValueFrom() != null && ruleset.getValueTo() != null) {
            totalScore += scoreValue(ruleset.getValueFrom(), ruleset.getValueTo());
        }

        if (isNotNullOrEmpty(ruleset.getTransactionType()) && isNotNullOrEmpty(transaction.getTransactionType())) {
            totalScore += scoreType(ruleset.getTransactionType(), transaction.getTransactionType(), notNullRulesCount);
        }


        if (ruleset.getDirection().equals("INCOMING")) {
            totalScore += scoreMessage(ruleset.getPayeeMessage(), transaction.getPayeeMessage());
        } else if (ruleset.getDirection().equals("OUTGOING")) {
            totalScore += scoreMessage(ruleset.getPayerMessage(), transaction.getPayerMessage());
        }


        if (checkPartyAccountNotNull(ruleset.getPartyAccountPrefix(), ruleset.getPartyAccountNumber(), ruleset.getPartyBankCode())) {
            totalScore += scorePartyAccount(ruleset.getPartyAccountPrefix(), ruleset.getPartyAccountNumber(), ruleset.getPartyBankCode());
        }


        if (isNotNullOrEmpty(ruleset.getPartyName())) {
            totalScore += scorePartyName(ruleset.getPartyName());
        }


        if (isNotNullOrEmpty(ruleset.getBookingTimeFrom()) && isNotNullOrEmpty(ruleset.getBookingTimeTo())) {
            totalScore += scoreTransactionDate(LocalTime.parse(ruleset.getBookingTimeFrom()), LocalTime.parse(ruleset.getBookingTimeTo()));
        }


        if (isNotNullOrEmpty(ruleset.getCardNumber()) && checkCardNumberNotNull(transaction)) {
            totalScore += scoreCardNumber(ruleset.getCardNumber(), transaction.getAdditionalInfoCard().getCardNumber());
        }


        if (transaction.getAdditionalInfoDomestic() != null) {
            if (checkSymbolNotNull(ruleset.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol())) {
                totalScore += scoreSymbol(ruleset.getSpecificSymbol(), transaction.getAdditionalInfoDomestic().getSpecificSymbol());
            }
            if (checkSymbolNotNull(ruleset.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol())) {
                totalScore += scoreSymbol(ruleset.getVariableSymbol(), transaction.getAdditionalInfoDomestic().getVariableSymbol());
            }
            if (checkSymbolNotNull(ruleset.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol())) {
                totalScore += scoreSymbol(ruleset.getConstantSymbol(), transaction.getAdditionalInfoDomestic().getConstantSymbol());
            }
        }


        if (totalScore >= 1.0) {
            possibleCategories.put(totalScore, ruleset.getCategoryId());
            //for testing only
            System.out.println(transaction.getId() + "  " + totalScore + "  " + ruleset.getCategoryId() + " " + getNotNullRulesCount(ruleset));
        }
    }

    private double scorePartyName(String rulePartyName) {
        double score = 0;
        String transactionPartyName = null;

        if (isNotNullOrEmpty(transaction.getPartyDescription())) {
            transactionPartyName = transaction.getPartyDescription();
        } else if (checkMerchantNameNotNull(transaction)) {
            transactionPartyName = transaction.getAdditionalInfoCard().getMerchantName();
        }

        if (transactionPartyName != null){
            if (transactionPartyName.toLowerCase().contains(rulePartyName.toLowerCase())) {
                score++;
            }
        }
        return score;
    }

    private double scoreType(String ruleTransactionType, String transactionType, int notNullRulesCount) {
        double score = 0;
        if ((transactionType.toLowerCase().equals(ruleTransactionType.toLowerCase())) || FuzzySearch.partialRatio(ruleTransactionType, transactionType) > THRESHOLD) {
            if (notNullRulesCount > 1) {
                score += 0.5;
            } else {
                score++;
            }
        }
        return score;
    }

    private double scoreSymbol(String rulesetSymbol, String transactionSymbol) { // jedna metoda stejna pro konstantni, variabilni i specificky symbol
        double score = 0;
        if (transactionSymbol.contains(rulesetSymbol)) {
            score += 2;
        }
        return score;
    }

    private boolean checkSymbolNotNull(String rulesetSymbol, String transactionSymbol) { // jedna metoda stejna pro konstantni, variabilni i specificky symbol
        return (isNotNullOrEmpty(transactionSymbol) && isNotNullOrEmpty(rulesetSymbol));
    }

    private boolean checkPartyAccountNotNull(String rulesetPartyPrefix, String rulesetPartyAccountNumber, String rulesetPartyBankCode) {
        TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

        return (transactionPartyAccount != null && isNotNullOrEmpty(rulesetPartyPrefix) && isNotNullOrEmpty(transactionPartyAccount.getPrefix())
                && isNotNullOrEmpty(rulesetPartyAccountNumber) && isNotNullOrEmpty(transactionPartyAccount.getAccountNumber())
                && isNotNullOrEmpty(rulesetPartyBankCode) && isNotNullOrEmpty(transactionPartyAccount.getBankCode()));
    }

    private boolean checkMerchantNameNotNull(Transaction transaction) {
        if (transaction.getAdditionalInfoCard() != null) {
            if (isNotNullOrEmpty(transaction.getAdditionalInfoCard().getMerchantName())) {
                return true;
            }
        }
        return false;
    }


    private boolean checkCardNumberNotNull(Transaction transaction) {
        if (transaction.getAdditionalInfoCard() != null) {
            if (isNotNullOrEmpty(transaction.getAdditionalInfoCard().getCardNumber())) {
                return true;
            }
        }
        return false;
    }


    private double scoreCardNumber(String rulesetCardNumber, String transactionCardNumber) {
        double score = 0;
        if (rulesetCardNumber.equals(transactionCardNumber)) {
            score += 10;
        }
        return score;
    }

    private double scoreMessage(String ruleMessage, String transactionMessage) {
        double score = 0;
        if (isNotNullOrEmpty(ruleMessage) && isNotNullOrEmpty(transactionMessage)) {
            if (transactionMessage.toLowerCase().contains(ruleMessage.toLowerCase()) && !ruleMessage.isEmpty()) {
                score++;
            } else if (FuzzySearch.partialRatio(ruleMessage, transactionMessage) > THRESHOLD) {
                score++;
            }
        }
        return score;
    }

    private double scoreValue(BigDecimal rulesetValueFrom, BigDecimal rulesetValueTo) {
        double score = 0;
        //neni vyplneno
        if (rulesetValueFrom == null & rulesetValueTo == null) {
            return score;
        }
        //castka do
        else if (rulesetValueFrom == null & rulesetValueTo != null) {
            if (transaction.getValue().getAmount().compareTo(rulesetValueTo) < 0) {
                score++;
            }
        }
        //castka od
        else if (rulesetValueFrom != null & rulesetValueTo == null) {
            if (transaction.getValue().getAmount().compareTo(rulesetValueFrom) > 0) {
                score++;
            }
        }
        //castka mezi
        else {
            if (transaction.getValue().getAmount().compareTo(rulesetValueFrom) > 0 && transaction.getValue().getAmount().compareTo(rulesetValueTo) < 0) {
                score++;
            }
        }
        return score;
    }

    private double scoreTransactionDate(LocalTime rulesetBookingDateFromValue, LocalTime rulesetBookingDateToValue) {
        double score = 0;
        org.joda.time.LocalTime transactionTime = org.joda.time.LocalTime.fromDateFields(transaction.getBookingDate());
        org.joda.time.LocalTime bookingDateFromValueYoda = new org.joda.time.LocalTime(rulesetBookingDateFromValue.getHour(), rulesetBookingDateFromValue.getMinute(), rulesetBookingDateFromValue.getSecond());
        org.joda.time.LocalTime bookingDateToValueYoda = new org.joda.time.LocalTime(rulesetBookingDateToValue.getHour(), rulesetBookingDateToValue.getMinute(), rulesetBookingDateToValue.getSecond());

        if (transactionTime.isBefore(bookingDateToValueYoda) && transactionTime.isAfter(bookingDateFromValueYoda)) {
            score++;
        }
        return score;
    }

    private double scorePartyAccount(String rulesetPartyPrefix, String rulesetPartyAccountNumber, String rulesetPartyBankCode) {
        double score = 0;
        TransactionPartyAccount transactionPartyAccount = transaction.getPartyAccount();

        if (transactionPartyAccount.getPrefix().contains(rulesetPartyPrefix) && transactionPartyAccount.getAccountNumber().contains(rulesetPartyAccountNumber)
                && transactionPartyAccount.getBankCode().equals(rulesetPartyBankCode)) {
            score += 2;
        } else if (!rulesetPartyAccountNumber.isEmpty()) {
            // pokud cislo je vyplnene, ale neshoduje se s rulesetem, prislusny ruleset se znevyhodni snizenim skore
            score -= 5;
        }

        return score;
    }

    private boolean isNotNullOrEmpty(String string) {
        return string != null && !string.trim().isEmpty();
    }

    /**
     * Count how many rules in a ruleset are not null
     *
     * @param ruleset stored in database
     * @return +1 for every not null rule
     */
    private int getNotNullRulesCount(Ruleset ruleset) {
        // V uvahu se neberou polozky, ktere nemaji vliv na rozhodovani (jako IDkategorie, nazev rulesetu apod)
        int notNullRulesCount = 0;

        // COMMON
        if (isNotNullOrEmpty(ruleset.getPartyName())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getTransactionType())) notNullRulesCount++;
        if (ruleset.getValueFrom() != null) notNullRulesCount++;
        if (ruleset.getValueTo() != null) notNullRulesCount++;

        // BANK TRANSFER
        if (isNotNullOrEmpty(ruleset.getPartyAccountPrefix())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getPartyAccountNumber())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getPartyBankCode())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getPayerMessage())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getPayeeMessage())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getConstantSymbol())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getVariableSymbol())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getSpecificSymbol())) notNullRulesCount++;

        // CARDS
        if (isNotNullOrEmpty(ruleset.getBookingTimeFrom())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getBookingTimeTo())) notNullRulesCount++;
        if (isNotNullOrEmpty(ruleset.getCardNumber())) notNullRulesCount++;

        return notNullRulesCount;
    }

    public CategoryFinder(List<Ruleset> ruleset) {
        this.ruleset = ruleset;
    }
}
