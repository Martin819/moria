package moria.controller;

import moria.Dto.Category;
import moria.Dto.Payment;
import moria.Util.CategoryScorer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@RestController
public class PaymentController {

    private Category category = new Category();
    private HashMap<String, ArrayList<String>> dummyCategories = new HashMap<>();

    // jen pro testovací účely
    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendItems() {

        //dummy data (tohle jakoby budeme mit
        ArrayList<String> supermarkets = new ArrayList<>();
        supermarkets.add("Billa");
        supermarkets.add("Kaufland");
        supermarkets.add("Penny Market");
        dummyCategories.put("Jidlo", supermarkets);

        ArrayList<String> retailers = new ArrayList<>();
        retailers.add("C&A");
        retailers.add("H&M");
        dummyCategories.put("Oblečení", retailers);

        dummyCategories.put("Nájem", null);
        dummyCategories.put("Výplata", null);
        dummyCategories.put("Úroky", null);
        category.setCategories(dummyCategories);
        //--------------------------------------------

        //neco takovyho prijde
        Date date = new Date();
        Payment payment = new Payment("H&M sdfgdsdfgs", new BigDecimal(250), false, date, "bagrovicovic");
        //--------------------------------------------

        CategoryScorer categoryScorer = new CategoryScorer();

        //A pak budu hodnotit, zdali to spadá do nějaké námi předem definované kategorie
        return categoryScorer.scoreCategories(payment, dummyCategories);
    }

    @PostMapping(path = "/incomingPayment")
    public void incomingPayment(Payment payment) {


    }




}
