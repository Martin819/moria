package moria.controller;

import moria.model.rules.Ruleset;
import moria.services.RulesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RulesController {

    private final RulesetService rulesetService;

    @Autowired
    public RulesController(RulesetService rulesetService) {
        this.rulesetService = rulesetService;
    }

    /**
     * Get all rules from database
     * @return all rulesets
     */
    @GetMapping(value = "rules/getAll")
    public ResponseEntity<List<Ruleset>> getAllRules() {
        List<Ruleset> rulesets = rulesetService.findAllRulesets();
        return new ResponseEntity<>(rulesets, HttpStatus.OK);
    }

    /**
     * Create new rule
     * @param rule - rule to create
     * @return
     */
    @PostMapping(path = "rules/create")
    public ResponseEntity<Void> createRule(@RequestBody Ruleset rule) {
        rulesetService.saveRuleset(rule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Remove rule
     * @param ids - id of rule to remove
     */
    @PostMapping (path = "rules/remove")
    public ResponseEntity<Void> removeRule(@RequestBody List<Integer> ids) {
        rulesetService.deleteByIdIn(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update rule
     * @param rule - rule to update
     * @return
     */
    @PutMapping (path = "rules/update")
    public ResponseEntity<Void> updateRule(@RequestBody Ruleset rule) {
        rulesetService.saveRuleset(rule);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
