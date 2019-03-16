package moria.repository;

import moria.model.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findById(int id);

    List<Transaction> findAll();

    List<Transaction> findByBookingDateAfter(Date after);

    List<Transaction> findByBookingDateBefore(Date before);

    List<Transaction> findByBookingDateBetween(Date from, Date to);

    List<Transaction> findByDirectionIgnoreCase(String direction);

    List<Transaction> findByTransactionTypeIgnoreCase(String transactionType);

    @Modifying
    @Transactional
    @Query("update transactions t set t.categoryId = ?2 where t.id = ?1")
    void setCategoryIdForTransactionById(int transactionId, int categoryId);
}
