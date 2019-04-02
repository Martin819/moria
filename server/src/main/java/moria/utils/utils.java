package moria.utils;

import moria.SpringContext;
import moria.dto.ChildTransaction;
import moria.dto.ParentTransaction;
import moria.dto.TransactionDto;
import moria.model.transactions.*;
import moria.services.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.*;

public class utils {

    private static TransactionServiceImpl getTransactionService() {
        return SpringContext.getBean(TransactionServiceImpl.class);
    }

    public static Transaction verifyTransactionForNullValues(Transaction t) {

        if (t.getAdditionalInfoCard() == null) {
            t.setAdditionalInfoCard(new TransactionAdditionalInfoCard("", "", ""));
        }
        if (t.getAdditionalInfoDomestic() == null) {
            t.setAdditionalInfoDomestic(new TransactionAdditionalInfoDomestic("", "", ""));
        }
        if (t.getAdditionalInfoForeign() == null) {
            t.setAdditionalInfoForeign(new TransactionAdditionalInfoForeign(new TransactionForeignOriginalValue(new BigDecimal(0), ""), new BigDecimal(0)));
        }

        return t;
    }

    public static List<TransactionDto> bindParentAndChildTransactions(List<Transaction> transactionList) {
//        TransactionServiceImpl transactionService = getTransactionService();
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            List<ChildTransaction> childTransactions = findChildTransactions(transaction);
            transactionDtoList.add(new TransactionDto(transaction, childTransactions));
        }
        return transactionDtoList;
    }

    public static List<ChildTransaction> findChildTransactions(Transaction t) {
        TransactionServiceImpl transactionService = getTransactionService();
        List<Transaction> childrenList = transactionService.findByParentId(t.getId());
        return getChildTransactions(childrenList);
    }

    public static String getNormalizedAccountNumber(TransactionPartyAccount a) {
        return getNormalizedAccountNumber(a.getPrefix(), a.getAccountNumber(), a.getBankCode());
    }

    public static String getNormalizedAccountNumber(String prefix, String number, String bankCode) {
        prefix = prefix.replaceFirst("^0+(?!$)", "");
        number = number.replaceFirst("^0+(?!$)", "");
        bankCode = bankCode.replaceFirst("^0+(?!$)", "");
        return prefix + "-" + number + "/" + bankCode;
    }

    /**
     * Create child transaction from parent transaction
     *
     * @param childTransaction information about new transaction from frontend
     * @return child transaction with all parameter of parent with another category_id, amount
     */
    public static ParentTransaction createDividedTransaction(ChildTransaction childTransaction) {
        TransactionServiceImpl transactionService = getTransactionService();
        Transaction parentTransaction = transactionService.findTransactionById(childTransaction.getId());
        Transaction newTransaction = null;
        try {
            newTransaction = (Transaction) parentTransaction.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        TransactionValue transactionValue = parentTransaction.getValue();
        TransactionValue newTransactionValue = null;
        try {
            newTransactionValue = (TransactionValue) transactionValue.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if (parentTransaction.getValue().getAmount() != null && parentTransaction.getValue().getAmount().compareTo(new BigDecimal(0)) != 0) { //pokud je hodnota v amount
            //nasetuju nove transakci stejnou menu, ale jinou castku
            newTransactionValue.setAmount(childTransaction.getAmount());

            newTransaction.setValue(newTransactionValue);

            //Prehozeni amountu do original value
            BigDecimal originalValue = new BigDecimal(parentTransaction.getValue().getAmount().longValue());
            parentTransaction.setOriginalValue(originalValue);
            String currency = parentTransaction.getValue().getCurrency();
            parentTransaction.setValue(new TransactionValue(null, currency));
            transactionService.saveTransaction(parentTransaction);

        } else { //pokud je uz v originalValue, tak jenom nasetuju hodnoty nove transakce
            newTransactionValue.setAmount(childTransaction.getAmount());
            newTransaction.setValue(newTransactionValue);
            newTransaction.setOriginalValue(null);
        }

        //nasetuju hodnoty nove splitove transakce
        newTransaction.setParentId(parentTransaction.getId());
        UUID uuid = UUID.randomUUID();
        newTransaction.setId(uuid.toString());

        newTransaction.setCategoryId(childTransaction.getCategoryId());

        transactionService.saveNewTransaction(newTransaction);
        updateRestOfAmountToOriginalValue(childTransaction);

        List<Transaction> childTransactionList = transactionService.findByParentId(parentTransaction.getId());
        List<ChildTransaction> childList = getChildTransactions(childTransactionList);
        ParentTransaction parentTransactionForFE = new ParentTransaction(parentTransaction, childList);

        return parentTransactionForFE;
    }

    private static List<ChildTransaction> getChildTransactions(List<Transaction> childTransactionList) {
        List<ChildTransaction> childList = new ArrayList<>();
        for (Transaction transaction : childTransactionList) {
            ChildTransaction child = new ChildTransaction(transaction.getId(), transaction.getCategoryId(), transaction.getValue().getAmount());
            childList.add(child);
        }
        return childList;
    }

    /**
     * Update child transaction to have rest amount to parent transaction
     *
     * @param childTransaction transaction which is being split
     */
    private static void updateRestOfAmountToOriginalValue(ChildTransaction childTransaction) {
        TransactionServiceImpl transactionService = getTransactionService();
        Transaction parentTransaction = transactionService.findTransactionById(childTransaction.getId());
        List<Transaction> transactionChildList = transactionService.findByParentId(childTransaction.getId());
        updateRestOfAmountToOriginalValue(parentTransaction, transactionChildList);
    }

    private static void updateRestOfAmountToOriginalValue(Transaction parentTransaction, List<Transaction> transactionChildList) {
        TransactionServiceImpl transactionService = getTransactionService();
        Transaction newTransaction;
        try {
            newTransaction = (Transaction) parentTransaction.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }

        Transaction subtractTransaction = null; //tohle je ta transakce, ve který bude zbytek do hodnoty parenta

        BigDecimal total = new BigDecimal(0);
        for (Transaction transaction : transactionChildList) {
            if (transaction.getCategoryId() == 0) {
                subtractTransaction = transaction; //najde zbytkovou transakci
            } else {
                total = total.add(transaction.getValue().getAmount()); //pocitam soucet - pro zjisteni, jaka ma byt hodnota zbytku
            }
        }

        BigDecimal parentAmount;
        if (parentTransaction.getValue().getAmount() != null && parentTransaction.getValue().getAmount().compareTo(new BigDecimal(0)) != 0) { //vytahnuti spravne polozky transactionValue
            parentAmount = parentTransaction.getValue().getAmount();
        } else {
            parentAmount = parentTransaction.getOriginalValue();
        }

        //nasetujeme value podle rozdilu mezi parent transaction a child transactions
        TransactionValue parentTransactionValue = parentTransaction.getValue();
        TransactionValue newTransactionValue = null;
        try {
            newTransactionValue = (TransactionValue) parentTransactionValue.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        //pokud je child transakce pri smayani jedina, nastavi se child value na 0
        BigDecimal difference = new BigDecimal(parentAmount.subtract(total).longValue());
        if (difference.compareTo(parentAmount) != 0) {
            newTransactionValue.setAmount(parentAmount.subtract(total));
        } else {
            newTransactionValue.setAmount(new BigDecimal(0));
        }

        newTransaction.setValue(newTransactionValue);

        if (subtractTransaction == null) { //pokud dopocitavaci transakce nebyla vytvorena, udelame novou
            newTransaction.setOriginalValue(null);

            UUID uuid = UUID.randomUUID();
            newTransaction.setCategoryId(0);
            newTransaction.setId(uuid.toString());
            newTransaction.setParentId(parentTransaction.getId());
            transactionService.saveNewTransaction(newTransaction);

        } else { //jinak probehne update stavajici (pokud tam jsou vsechny, ma amount 0 a nebude se zobrazovat
            subtractTransaction.setValue(newTransactionValue);
            transactionService.saveTransaction(subtractTransaction);
        }
    }

    public static void removeSplitTransaction(String id) {
        TransactionServiceImpl transactionService = getTransactionService();

        Transaction transactionToRemove = transactionService.findTransactionById(id);
        transactionService.removeTransaction(transactionToRemove);
        List<Transaction> transactionList = transactionService.findByParentId(transactionToRemove.getParentId());
        Transaction parentTransaction = transactionService.findTransactionById(transactionToRemove.getParentId());
        updateRestOfAmountToOriginalValue(parentTransaction, transactionList); //aktualizuje dopocitavaci transakci (v pripade smazani vsech bude nula)
    }
}