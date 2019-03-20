package moria.controller;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import moria.dto.Category;
import moria.utils.Categories;
import moria.utils.CategoryScorer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class IncomingTransactionsController {


    // jen pro testovací účely
    @GetMapping(path = "/plsCategorize", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String[] sendItems() {

        CategoryScorer categoryScorer = new CategoryScorer();
        ArrayList<Category> list = categoryScorer.findCategoriesForTransaction();

//        return Categories.getCategoryById(categoryScorer.category);
//        return Categories.getCategoryById(111);
        String[] strings = new String[list.size()];
        for (int i = 0; i < strings.length - 1 ; i++ ){
            strings[i] = "id platby je " + list.get(i).getIdPayment() + " id kategorie je " + list.get(i).getIdCategory() + " což je " + Categories.getCategoryById(list.get(i).getIdCategory());
        }
        return strings;
    }

    // jen pro testovací účely
    @GetMapping(path = "/fuzzy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String fuzzyTest() {

        return String.valueOf(FuzzySearch.partialRatio("Decathlon", "DECATHLON-XTRS254354, děkujeme za nakup"));
    }

   /* @PostMapping(path = "/incomingPayment")
    public void incomingPayment() {


    }*/


}
