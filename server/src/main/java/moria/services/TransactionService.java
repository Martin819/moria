package moria.services;

import moria.model.transactions.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    Transaction findTransactionById(String id);
    List<Transaction> findAllTransactions();

}
