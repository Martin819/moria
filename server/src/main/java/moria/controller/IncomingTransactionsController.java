package moria.controller;

import java.io.IOException;
import java.util.Date;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.bankingApiClient.BankingAPIService;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.TransactionServiceImpl;
import moria.utils.Categories;
import moria.utils.TransactionCategorizer;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class IncomingTransactionsController {

    @Autowired
    BankingAPIService APIservice;

    @Autowired
    TransactionServiceImpl traService;

    // jen pro testovací účely
    @GetMapping(path = "/plsCategorize", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String[] getCategorizedTransactionTest() {

        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.findCategoriesForAllTransaction(false);
        ArrayList<Category> list = transactionCategorizer.getListOfCategorizedTransaction();

        String[] strings = new String[list.size()];
        for (int i = 0; i < strings.length ; i++ ){
            strings[i] = "id platby je " + list.get(i).getIdPayment() + " id kategorie je " + list.get(i).getIdCategory() + " což je " + Categories.getCategoryById(list.get(i).getIdCategory());
        }
        return strings;
    }

    // jen pro testovací účely
    @GetMapping(path = "/fuzzy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String fuzzyTest() {

        return String.valueOf(FuzzySearch.partialRatio("0", ""));
    }

    // jen pro testovací účely
    @GetMapping(path = "/time", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String timeTest() {

        Date date = new Date();
        LocalTime localTime = LocalTime.fromDateFields(date);

        java.time.LocalTime localTimeJava = java.time.LocalTime.of(21, 30, 59, 11001);

        LocalTime proper = new LocalTime(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute());
        return proper.toString();
    }

    @GetMapping(value = "/categorize")
    public ResponseEntity<Void> categorizeTransactionWithoutCategoryID() {
        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.findCategoriesForAllTransaction(false);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "/fetchTransactions")
    public List<Transaction> fetchTransactions() throws IOException {
        return APIservice.findTransactionsByDate("1990-01-01", "2020-12-31");
    }

    @GetMapping(path = "/saveTransactions")
    public boolean saveTransactions() throws IOException {
        List<Transaction> transactions = APIservice.findTransactionsByDate("1990-01-01", "2020-12-31");
        traService.saveNewTransactionList(transactions);
        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.categorizeTransaction(transactions);
        return true;
    }

    @GetMapping(path="/saveTransactions/{fromDate}/{toDate}")
    public boolean saveTransactions(@PathVariable String fromDate, @PathVariable String toDate) throws IOException {
        List<Transaction> transactions = APIservice.findTransactionsByDate(fromDate, toDate);
        traService.saveNewTransactionList(transactions);
        return true;
    }

}
