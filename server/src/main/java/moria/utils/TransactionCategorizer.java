package moria.utils;

import moria.SpringContext;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.RulesetService;
import moria.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionCategorizer {
    private CategoryScorer categoryScorer;

    public TransactionCategorizer() {
        RulesetService rulesetService = getRulesetService();
        categoryScorer = new CategoryScorer(rulesetService.findAllRulesets());
    }

    private TransactionService getTransactionService() {
        return SpringContext.getBean(TransactionService.class);
    }

    private RulesetService getRulesetService() {
        return SpringContext.getBean(RulesetService.class);
    }

    //for test purposes only (tahle metoda bude smazana)
    public ArrayList<Category> getListOfCategorizedTransaction() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        ArrayList<Category> categoryList = new ArrayList<>();
        for (Transaction transactionLiTransaction : transactionList){
            categoryList.add(new Category(transactionLiTransaction.getCategoryId(), transactionLiTransaction.getId()));
        }
        return categoryList;
    }

    public void findCategoriesForAllTransaction(boolean recategorizeAllTransaction) {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            if (recategorizeAllTransaction && !transaction.getIsCategoryManuallyAssigned()) {
                int category = categoryScorer.scorePossibleCategories(transaction);
                transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
            } else {
                if (transaction.getCategoryId() == 0) {
                    int category = categoryScorer.scorePossibleCategories(transaction);
                    transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
                }
            }
        }
    }

    public void categorizeTransaction(List<Transaction> transactionList) {
        TransactionService transactionService = getTransactionService();
        for (Transaction transaction : transactionList) {
            int category = categoryScorer.scorePossibleCategories(transaction);
            transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
        }
    }
}
