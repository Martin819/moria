package moria.controller;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.dto.Payment;
import moria.model.transactions.Transaction;
import moria.services.RulesetService;
import moria.services.TransactionService;
import moria.services.TransactionServiceImpl;
import moria.utils.Categories;
import moria.utils.CategoryScorer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;


@RestController
public class IncomingTransactionsController {


    // jen pro testovací účely
    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendItems() throws ParseException {

        CategoryScorer categoryScorer = new CategoryScorer();
        return Categories.getCategoryById(categoryScorer.category);
//        return Categories.getCategoryById(111);
    }

    // jen pro testovací účely
    @GetMapping(path = "/fuzzy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String fuzzyTest() {

        return String.valueOf(FuzzySearch.partialRatio("Decathlon", "DECATHLON-XTRS254354, děkujeme za nakup"));
    }

    @PostMapping(path = "/incomingPayment")
    public void incomingPayment(Payment payment) {

//        //neco takovyho prijde
//        Date date2 = new Date();
//        Payment payment = new Payment("bla bla Shell dsdfgs", 2250, false, date2, "bagrovicovic");
//        //--------------------------------------------
//
//        CategoryScorer categoryScorer = new CategoryScorer();
//
//        //A pak budu hodnotit, zdali to spadá do nějaké námi předem definované kategorie
//        return categoryScorer.scoreCategories(payment);

    }


}
