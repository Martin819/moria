package moria.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.bankingApiClient.BankingAPIService;
import moria.dto.Category;
import moria.model.transactions.Transaction;
import moria.services.TransactionServiceImpl;
import moria.utils.Categories;
import moria.utils.CategoryScorer;
import moria.utils.utils;
import org.joda.time.LocalTime;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Retrofit;

import java.util.ArrayList;
import java.util.List;


@RestController
public class IncomingTransactionsController {

    // jen pro testovací účely
    @GetMapping(path = "/plsCategorize", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String[] sendItems() {

        CategoryScorer categoryScorer = new CategoryScorer();
        ArrayList<Category> list = categoryScorer.findCategoriesForTransaction();

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

//        return localTime.toString();
        LocalTime proper = new LocalTime(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute());
        return proper.toString();
    }

    @GetMapping(path = "/fetchTransactions")
    public List<Transaction> fetchTransactions() throws IOException {
        BankingAPIService service = new BankingAPIService();
        List<Transaction> transactions = service.findTransactionsByDate("1990-01-01", "2020-12-31");
        return transactions;
    }

    @GetMapping(path = "/saveTransactions")
    public boolean saveTransactions() throws IOException {
        BankingAPIService APIservice = new BankingAPIService();
        TransactionServiceImpl traService = new TransactionServiceImpl();
        List<Transaction> transactions = APIservice.findTransactionsByDate("1990-01-01", "2020-12-31");
        for (Transaction t:transactions) {
            System.out.println(t.toString());
            Transaction tra = utils.verifyTransactionForNullValues(t);
            System.out.println(tra.toString());
            traService.saveTransaction(tra);
        }
//        traService.saveTransactionList(transactions);
        return true;
    }

}
