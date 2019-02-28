package moria.Util;

import moria.Dto.Payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryScorer {

    private HashMap<String, ArrayList<String>> categories = new HashMap<>();
    private HashMap<String, Integer> scoredCategories = new HashMap<>();

    public String scoreCategories(Payment payment, HashMap<String, ArrayList<String>> categories) {
        this.categories = categories;

        //sem se budou hodnotit skore pravdepodobnosti, kam to ma spadnout - nejdřív zkopíruju názvy kategorií do HashMapy
        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {
            String categoryName = entry.getKey();
            scoredCategories.put(categoryName, 0);
        }
        String retailer = payment.getPayer(); //obchodnik z prichozi platby

        scorePropabilityOfRetailers(retailer); //přidá bod ke kategoriim, ve kterých se obchodnik nachazi


        //Najdu nejvyssi hodnoceni ve scoredCategories (pokud jsou 2 a vice se stejným, beru to prvni) -> to je nase kategorie kde se obchodnik nachazi
        String categoryWithHighestPropability = "";
        int maxValue = 0;
        for (Map.Entry<String, Integer> entry : scoredCategories.entrySet()) {
            Integer score = entry.getValue();
            String retailers = entry.getKey();
            if (score > maxValue) {
                maxValue = score;
                categoryWithHighestPropability = retailers;
            }
        }
        return categoryWithHighestPropability;
    }

    /**
     * mame obchodnika z prichozi platby - projedu vsechny obchodniky, ktery mam v kategoriich a hledam, zdali se nějaká 2 jména neshoduji
     * pokud se shodují, vlozim si je do foundCategories
     *
     * @param retailer hledany obchodnik v kategoriich
     */
    private void scorePropabilityOfRetailers(String retailer) {
        ArrayList<String> foundCategories = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : categories.entrySet()) {

            String category = entry.getKey();
            ArrayList<String> retailers = entry.getValue();

            if (retailers != null) {
                for (String retailerName : retailers) {
                    //tohlento umi vyhledat nazev obchodnika v souveti -> najde napr. Billa v blabla Billa bla bla
                    if ( retailer.matches(".*" + retailerName +".*")) foundCategories.add(category);
                }
            }
        }

        //tyto kategorie pak dostanou +1 bod v scoredCategories hashmape
        for (String categoryName : foundCategories) {
            int score = scoredCategories.get(categoryName);
            scoredCategories.put(categoryName, score + 1);
        }
    }
}