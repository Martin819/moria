package moria.controller;

import moria.model.rules.Ruleset;
import moria.services.RulesetService;
import moria.transactionCategorization.TransactionCategorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RulesetsController {

    private final RulesetService rulesetService;

    @Autowired
    public RulesetsController(RulesetService rulesetService) {
        this.rulesetService = rulesetService;
    }

    /**
     * Get all rules from database
     * @return all rulesets
     */
    @GetMapping(value = "rules")
    public ResponseEntity<List<Ruleset>> getAllRules() {
        List<Ruleset> rulesets = rulesetService.findAllRulesets();
        return new ResponseEntity<>(rulesets, HttpStatus.OK);
    }

    /**
     * Create new rule
     * @param rule - rule to create
     * @return HttpStatus.CREATED
     */
    @PostMapping(path = "rules")
    public ResponseEntity<Ruleset> createRule(@RequestBody Ruleset rule) {
        Ruleset ruleset = rulesetService.saveRuleset(rule);
        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.categorizeTransactions();
        return new ResponseEntity<>(ruleset, HttpStatus.CREATED);
    }

    /**
     * Remove rule
     * @param ids - id of rule to remove
     */
    @PostMapping (path = "rules/remove")
    public ResponseEntity<Void> removeRule(@RequestBody List<Integer> ids) {
        rulesetService.deleteByIdIn(ids);
        TransactionCategorizer transactionCategorizer = new TransactionCategorizer();
        transactionCategorizer.categorizeTransactions();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update rule
     * @param rule - rule to update
     * @return ok
     */
    @PutMapping (path = "rules")
    public ResponseEntity<Ruleset> updateRule(@RequestBody Ruleset rule) {
        Ruleset ruleset = rulesetService.saveRuleset(rule);
        return new ResponseEntity<>(ruleset, HttpStatus.OK);
    }

}
