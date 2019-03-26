package moria.utils;

import moria.SpringContext;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionCategorizer {
    private CategoryScorer categoryScorer;

    public TransactionCategorizer() {
        categoryScorer = new CategoryScorer();
    }

    private TransactionService getTransactionService() {
        return SpringContext.getBean(TransactionService.class);
    }

    public ArrayList<Category> findCategoriesForAllUncategorizedTransactionWithListing() {
        ArrayList<Category> list = new ArrayList<>();
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            if (transaction.getCategoryId() == 0) {
                int category = categoryScorer.scoreCategories(transaction);
                transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
                Category cat = new Category(category, transaction.getId());
                list.add(cat);
            }
        }
        return list;
    }

    public void findCategoriesForAllUncategorizedTransactionWithoutListing() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            if (transaction.getCategoryId() == 0) {
                int category = categoryScorer.scoreCategories(transaction);
                transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
            }
        }
    }

    /**
     * slouží pro znovuzkategorizování všech plateb (při smazání pravidla)
     */
    public void categorizeAllTransaction() {
        TransactionService transactionService = getTransactionService();
        List<Transaction> transactionList = transactionService.findAllTransactions();
        for (Transaction transaction : transactionList) {
            int category = categoryScorer.scoreCategories(transaction);
            transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
        }
    }

    public void categorizeTransaction(List<Transaction> transactionList) {
        TransactionService transactionService = getTransactionService();
        for (Transaction transaction : transactionList) {
            int category = categoryScorer.scoreCategories(transaction);
            transactionService.setCategoryIdForTransactionById(transaction.getId(), category);
        }
    }
}
