package moria.services;

import moria.model.transactions.Transaction;
import moria.repository.TransactionRepository;
import moria.utils.utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Returns Transaction by given ID
    @Override
    public Transaction findTransactionById(int id) {
        return transactionRepository.findById(id);
    }

    // Returns list of all Transactions
    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    // Returns list of Transactions which were booked after given Date
    @Override
    public List<Transaction> findByBookingDateAfter(Date after) {
        return transactionRepository.findByBookingDateAfter(after);
    }

    // Returns list of Transactions which were booked before given Date
    @Override
    public List<Transaction> findByBookingDateBefore(Date before) {
        return transactionRepository.findByBookingDateBefore(before);
    }

    // Returns list of Transactions which were booked between two given dates
    @Override
    public List<Transaction> findByBookingDateBetween(Date from, Date to) {
        return transactionRepository.findByBookingDateBetween(from, to);
    }

    // Returns list of all incoming Transactions
    @Override
    public List<Transaction> findAllIncomingTransactions() {
        return transactionRepository.findByDirectionIgnoreCase("INCOMING");
    }

    // Returns list of all outgoing Transactions
    @Override
    public List<Transaction> findAllOutgoingTransactions() {
        return transactionRepository.findByDirectionIgnoreCase("OUTGOING");
    }

    // Returns list of all card Transactions
    @Override
    public List<Transaction> findAllCardTransactions() {
        return transactionRepository.findByTransactionTypeIgnoreCase("CARD");
    }

    // Returns list of all ATM withdrawal Transactions
    @Override
    public List<Transaction> findAllCashTransactions() {
        return transactionRepository.findByTransactionTypeIgnoreCase("CASH");
    }

    // Returns list of all home payment Transactions
    @Override
    public List<Transaction> findAllHomeTransactions() {
        return transactionRepository.findByTransactionTypeIgnoreCase("PAYMENT_HOME");
    }

    // Returns list of all abroad payment Transactions
    @Override
    public List<Transaction> findAllAbroadTransactions() {
        return transactionRepository.findByTransactionTypeIgnoreCase("PAYMENT_ABROAD");
    }

    // Sets categoryId to given ID for a Transaction identified by its ID
    @Override
    public void setCategoryIdForTransactionById(String transactionId, int categoryId) {
        transactionRepository.setCategoryIdForTransactionById(transactionId, categoryId);
    }

    @Override
    @Modifying
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveTransactionList(List<Transaction> list) {
        transactionRepository.saveAll(list);
    }

    @Override
    public void saveNewTransaction(Transaction transaction) {
        if (!(getNumberOfTransactionsById(transaction.getId()) > 0)) {
            transactionRepository.save(transaction);
        }
    }

    public void saveNewTransactionList(List<Transaction> list) {
        for (Transaction t:list) {
            Transaction tra = utils.verifyTransactionForNullValues(t);
            saveNewTransaction(tra);
        }
    }

    @Override
    public int getNumberOfTransactionsById(String transactionId) {
        return transactionRepository.getNumberOfTransactionsById(transactionId);
    }


}
