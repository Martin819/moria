package moria.controller;

import java.io.IOException;

import moria.bankingApiClient.BankingAPIService;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.TransactionServiceImpl;
import moria.transactionCategorization.Categories;
import moria.transactionCategorization.TransactionCategorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IncomingTransactionsController {

    @Autowired
    BankingAPIService APIservice;

    @Autowired
    TransactionServiceImpl traService;

    // jen pro testovací účely
    @GetMapping(path = "/plsCategorize", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String[] getCategorizedTransactionTest() {

        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.categorizeTransactions();
        ArrayList<Category> listOfAllTransactions = transactionCategorizer.getListOfCategorizedTransactions();


        String[] outputStrings = new String[listOfAllTransactions.size()];
        for (int i = 0; i < outputStrings.length ; i++ ){
            outputStrings[i] = "id platby je " + listOfAllTransactions.get(i).getIdPayment() + "     id kategorie je " + listOfAllTransactions.get(i).getIdCategory() + " (" + Categories.getCategoryById(listOfAllTransactions.get(i).getIdCategory()) + ")";
        }
        return outputStrings;
    }

    @GetMapping(value = "/categorize")
    public ResponseEntity<Void> categorizeTransactionWithoutCategoryID() {
        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.categorizeTransactions();
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
        transactionCategorizer.categorizeTransactions();
        return true;
    }

    @GetMapping(path="/saveTransactions/{fromDate}/{toDate}")
    public boolean saveTransactions(@PathVariable String fromDate, @PathVariable String toDate) throws IOException {
        List<Transaction> transactions = APIservice.findTransactionsByDate(fromDate, toDate);
        traService.saveNewTransactionList(transactions);
        traService.setOriginalValueToValue();
        return true;
    }

}
