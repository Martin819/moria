package moria.services;

import moria.model.transactions.Transaction;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface TransactionService {

    Transaction findTransactionById(int id);

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

    void setCategoryIdForTransactionById(int transactionId, int categoryId);

    void saveTransaction(Transaction transaction);

    List<Transaction> saveTransactionList(List<Transaction> list);
}
