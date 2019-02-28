package moria.controller;

import moria.Dto.Payment;
import moria.Dto.Rule;
import moria.Util.CategoryScorer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;


@RestController
public class PaymentController {


    // jen pro testovací účely
    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendItems() throws ParseException {

        //neco takovyho prijde
        Date date2 = new Date();
        Payment payment = new Payment("bla bla Decathlon dsdfgs", 2250, false, date2, "Dekujeme za nakup v obchode Decathlon");
        //--------------------------------------------

        CategoryScorer categoryScorer = new CategoryScorer();

        //A pak budu hodnotit, zdali to spadá do nějaké námi předem definované kategorie
        return categoryScorer.scoreCategories(payment);
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

    @PostMapping(path = "/newRule")
    public void createRule(Rule rule) {


    }

}
