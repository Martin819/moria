package moria.transactionCategorization;

import moria.SpringContext;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.RulesetService;
import moria.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionCategorizer {
    private CategoryFinder categoryFinder;

    public TransactionCategorizer() {
        RulesetService rulesetService = getRulesetService();
        categoryFinder = new CategoryFinder(rulesetService.findAllRulesets());
    }

    private TransactionService getTransactionService() {
        return SpringContext.getBean(TransactionService.class);
    }

    private RulesetService getRulesetService() {
        return SpringContext.getBean(RulesetService.class);
    }

    //for test purposes only (tahle metoda bude smazana)
    public ArrayList<Category> getListOfCategorizedTransactions() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        ArrayList<Category> categoryList = new ArrayList<>();
        for (Transaction transactionLiTransaction : transactionList){
            if (transactionLiTransaction.getParentId() == null || transactionLiTransaction.getOriginalValue() == null ){
                categoryList.add(new Category(transactionLiTransaction.getCategoryId(), transactionLiTransaction.getId()));
            }
        }
        return categoryList;
    }

    public void categorizeTransactions() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        List<Transaction> toRemove = new ArrayList<>();
        for (Transaction transaction : transactionList) {
            // vyjmout z kategorizace child transakce (tzn. takove, co maji vyplnene parentId) a vyjmout uz prazdne (plne rozdelene) rodicovske transakce
            if (transaction.getParentId() != null || transaction.getOriginalValue() != null) {
                toRemove.add(transaction);
            }
            // vyjmout z kategorizace transakce, co byly kategorizovany rucne (maji parametr nastaven na true)
            if (transaction.getIsCategoryManuallyAssigned() != null && transaction.getIsCategoryManuallyAssigned()) {
                toRemove.add(transaction);
            }
        }
        transactionList.removeAll(toRemove);
        assignCategories(transactionList);
    }

    public void assignCategories(List<Transaction> transactionList) {
        TransactionService transactionService = getTransactionService();
        for (Transaction transaction : transactionList) {
                int category = categoryFinder.findBestMatchingCategory(transaction);
                transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
        }
    }
}
