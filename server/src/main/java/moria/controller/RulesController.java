package moria.controller;

import moria.dto.Rule;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class RulesController {


    /**
     * Get all rules from database
     * @return
     */
    @GetMapping(value = "/getRules")
    public List<Rule> getAllRules() {
        //TODO Bezdy Joe - load from database
        return loadAllRules();
    }

    /**
     * Create new rule
     * @param rule - rule to create
     * @return
     */
    @PostMapping(path = "/createRule")
    public Rule createRule(@RequestBody Rule rule) {
        //TODO Bezdy Joe - save to db
        return null;

    }

    /**
     * Remove rule
     * @param rule - rule to remove
     */
    @DeleteMapping (path = "/removeRule")
    public void removeRule(@RequestBody Rule rule) {
        //TODO Bezdy Joe - remove from db
    }


    // jen for test - bude nahrazeno volanim z db
    private List<Rule> loadAllRules() {
       List<Rule> allRules = new ArrayList<>();

        //dummy data for Rules
        HashMap<String, String> dummyRule = new HashMap<>();
        dummyRule.put("payer", "Decathlon");
        dummyRule.put("transferMoney", "500");
        dummyRule.put("compare", ">");
        dummyRule.put("incomingPayment", "false");
        Date date = new Date();
        dummyRule.put("dateOfPayment", String.valueOf(date));
        dummyRule.put("description", "nakup funkcniho obleceni Decathlon");
        Rule rule = new Rule("Sport", "věci na běhání", dummyRule);
        allRules.add(rule);
        return allRules;
    }

}
