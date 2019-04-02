package moria.services;

import moria.model.transactions.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface TransactionService {

    Transaction findTransactionById(String id);

    List<Transaction> findAllTransactions();

    List<Transaction> findByBookingDateAfter(Date after);

    List<Transaction> findByBookingDateBefore(Date before);

    List<Transaction> findByBookingDateBetween(Date from, Date to);

    List<Transaction> findAllIncomingTransactions();

    List<Transaction> findAllOutgoingTransactions();

    List<Transaction> findAllCardTransactions();

    List<Transaction> findAllCashTransactions();

    List<Transaction> findAllHomeTransactions();

    List<Transaction> findAllAbroadTransactions();

    List<Transaction> findByParentId(String parentId);

    void setCategoryIdForTransactionById(String transactionId, int categoryId);

    void setManuallyUpdateCategory(String transactionId);

    void setOriginalValueToValue();

    void setOriginalValueById(String transactionId, BigDecimal originalValue);

    void setValueAmountById(String transactionId, BigDecimal valueAmount);

//    void setCategoriesById(String transactionId, Map<Integer, BigDecimal> categories);

    void saveTransaction(Transaction transaction);

    void saveTransactionList(List<Transaction> list);

    void saveNewTransaction(Transaction transaction);

    void removeTransaction(Transaction transaction);

    int getNumberOfTransactionsById(String transactionId);

}
