package moria.tansactionSplitting;

import moria.SpringContext;
import moria.dto.ChildTransaction;
import moria.dto.TransactionDto;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionValue;
import moria.services.TransactionServiceImpl;
import moria.utils.Utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionSplitter {

    private static TransactionServiceImpl getTransactionService() {
        return SpringContext.getBean(TransactionServiceImpl.class);
    }

    /**
     * Create child transaction from parent transaction
     *
     * @param childTransaction information about new transaction from frontend
     * @return child transaction with all parameter of parent with another category_id, amount
     */
    public static TransactionDto createSplittedTransaction(ChildTransaction childTransaction) {
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
        //nasetuju nove transakci stejnou menu, ale jinou castku
        newTransactionValue.setAmount(childTransaction.getAmount());
        newTransaction.setValue(newTransactionValue);

        if (parentTransaction.getValue().getAmount() != null && parentTransaction.getValue().getAmount().compareTo(new BigDecimal(0)) != 0) { //pokud je hodnota v amount
            //Prehozeni amountu do original value
            BigDecimal originalValue = new BigDecimal(parentTransaction.getValue().getAmount().longValue());
            parentTransaction.setOriginalValue(originalValue);
            String currency = parentTransaction.getValue().getCurrency();
            parentTransaction.setValue(new TransactionValue(null, currency));
            transactionService.saveTransaction(parentTransaction);

        } else { //pokud je uz v originalValue, tak jenom nasetuju hodnoty nove transakce
            newTransaction.setOriginalValue(null);
        }

        List<Transaction> childTransactionList = transactionService.findByParentId(parentTransaction.getId());

        //najdu child transakci se stejným category_id
        boolean isChildTransactionAlreadyCreated = false;
        Transaction createdChildTransaction = null;
        for (Transaction childTransactionFromParentList : childTransactionList) {
            if (childTransactionFromParentList.getCategoryId() == childTransaction.getCategoryId()) {
                isChildTransactionAlreadyCreated = true;
                createdChildTransaction = childTransactionFromParentList;
            }
        }

        //pokud child transakce s danym category_id neexistuje
        if (!isChildTransactionAlreadyCreated) {
            //nasetuju hodnoty nove splitove transakce
            newTransaction.setParentId(parentTransaction.getId());
            UUID uuid = UUID.randomUUID();
            newTransaction.setId(uuid.toString());

            newTransaction.setCategoryId(childTransaction.getCategoryId());
        } else { //pokud existuje, provede se pouze update value
            newTransaction = createdChildTransaction;
            TransactionValue transactionValueChild = newTransaction.getValue();
            transactionValueChild.setAmount(childTransaction.getAmount().add(transactionValueChild.getAmount()));
            newTransaction.setValue(transactionValueChild);
        }

        transactionService.saveNewTransaction(newTransaction);
        updateRestOfAmountToOriginalValue(childTransaction);

        //po updatu databaze si znovu načtu chil transakce (pro FE)
        childTransactionList = transactionService.findByParentId(parentTransaction.getId());  //musím zavolat znovu, protože se seznam child transakci zmenil
        List<ChildTransaction> childList = Utils.getChildTransactions(childTransactionList);
        TransactionDto transactionDto = new TransactionDto(parentTransaction, childList);

        return transactionDto;
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

    public static TransactionDto removeSplittedTransaction(String id) {
        TransactionServiceImpl transactionService = getTransactionService();

        Transaction transactionToRemove = transactionService.findTransactionById(id);
        transactionService.removeTransaction(transactionToRemove);
        List<Transaction> childTransactionList = transactionService.findByParentId(transactionToRemove.getParentId());
        Transaction parentTransaction = transactionService.findTransactionById(transactionToRemove.getParentId());
        //pokud je uz dopocitavaci kategorie sama, je smazana a parent transakci se prehodi original value do amountu
        if (childTransactionList.size() == 1) {
            transactionService.removeTransaction(childTransactionList.get(0));
            TransactionValue transactionValue = parentTransaction.getValue();
            transactionValue.setAmount(parentTransaction.getOriginalValue());
            parentTransaction.setValue(transactionValue);
            parentTransaction.setOriginalValue(null);
            transactionService.saveTransaction(parentTransaction);
        } else {
            updateRestOfAmountToOriginalValue(parentTransaction, childTransactionList); //aktualizuje dopocitavaci transakci
        }

        //vratime na FE celou parent transakci
        childTransactionList = transactionService.findByParentId(transactionToRemove.getParentId());
        List<ChildTransaction> childTransactions = Utils.getChildTransactions(childTransactionList);
        TransactionDto transactionForFrontend = new TransactionDto(parentTransaction, childTransactions);

        return transactionForFrontend;
    }
}
