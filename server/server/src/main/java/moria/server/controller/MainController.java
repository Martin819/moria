package moria.server.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import moria.server.MockData.Item;

import moria.server.Model.Category;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
public class MainController {

    private Category category = new Category();
    private HashMap<String, ArrayList<String>> dummyCategories = new HashMap<>();

    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendItems() {
//        Item item = new Item();
//        item.setCount(1);
//        item.setName("Bagrinho");
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
        HashMap<String, String> payment = new HashMap<>();
        payment.put("retailer", "Billa");
        payment.put("incoming", "false");
        payment.put("dateOfPayment", "Tue Aug 31 10:20:56 SGT 1982");
        payment.put("description", "sunka");
        //--------------------------------------------

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        HashMap<String, Integer> scoredCategories = new HashMap<>();

        for (Map.Entry<String, ArrayList<String>> entry : dummyCategories.entrySet()) {
            String categoryName = entry.getKey();
            scoredCategories.put(categoryName, 0);
        }

        //A pak budu hodnotit, zdali to spadá do nějaké námi předem definované kategorie
        return scoreCategories(scoredCategories, payment);
    }

    @PostMapping(path = "/incomingPayment")
    public void incomingPayment(Map<String, String> payment) {

//        //dummy data (tohle jakoby budeme mit
//        ArrayList<String> supermarkets = new ArrayList<>();
//        supermarkets.add("Billa");
//        supermarkets.add("Kaufland");
//        supermarkets.add("Penny Market");
//        dummyCategories.put("Jidlo", supermarkets);
//
//        ArrayList<String> retailers = new ArrayList<>();
//        retailers.add("C&A");
//        retailers.add("H&M");
//        dummyCategories.put("Oblečení", retailers);
//
//        dummyCategories.put("Nájem", null);
//        dummyCategories.put("Výplata", null);
//        dummyCategories.put("Úroky", null);
//        category.setCategories(dummyCategories);
//        //--------------------------------------------
//
//        //neco takovyho prijde
//        payment = new HashMap<>();
//        payment.put("retailer", "Billa");
//        payment.put("incoming", "false");
//        payment.put("dateOfPayment", "Tue Aug 31 10:20:56 SGT 1982");
//        payment.put("description", "sunka");
//        //--------------------------------------------
//
//        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
//        HashMap<String, Integer> scoredCategories = new HashMap<>();
//
//        for (Map.Entry<String, ArrayList<String>> entry : dummyCategories.entrySet()) {
//            String categoryName = entry.getKey();
//            scoredCategories.put(categoryName, 0);
//        }
//
//        //A pak budu hodnotit, zdali to spadá do nějaké námi předem definované kategorie
//        String category = scoreCategories(scoredCategories, payment);

    }

    private String scoreCategories(HashMap<String, Integer> scoredCategories, Map<String, String> payment) {
        String retailer = payment.get("retailer"); //obchodnik z prichozi platby

        //mame obchodnika z prichozi platby - projedu vsechny obchodniky, ktery mam v kategoriich a hledam, zdali se nějaká 2 jména neshoduji
        //pokud se shodují, vlozim si je do foundCategories
        ArrayList<String> foundCategories = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : dummyCategories.entrySet()){

            String category = entry.getKey();
            ArrayList<String> retailers = entry.getValue();

            if (retailers != null){
                for (String retailerName : retailers){
                    if (retailerName.contains(retailer)) foundCategories.add(category);
                }
            }
        }

        //tyto kategorie pak dostanou +1 bod v hashmape
        for (String categoryName : foundCategories){
            int score = scoredCategories.get(categoryName);
            scoredCategories.put(categoryName, score + 1);
        }

        String categoryWithHighestPropability = "";
        int maxValue = 0;
        for (Map.Entry<String, Integer> entry : scoredCategories.entrySet()){
            Integer score = entry.getValue();
            String retailers = entry.getKey();
            if (score > maxValue){
                maxValue = score;
                categoryWithHighestPropability = retailers;
            }
        }

        return categoryWithHighestPropability;


    }


}
