package moria.services;


import moria.model.rules.Ruleset;
import moria.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rulesetService")
public class RulesetServiceImpl implements RulesetService {

    @Autowired
    private RulesetRepository rulesetRepository;

    // Returns Ruleset by given ID
    @Override
    public Ruleset findRulesetById(int id) {
        return rulesetRepository.findById(id);
    }

    // Returns list of all Rulesets
    @Override
    public List<Ruleset> findAllRulesets() {
        return rulesetRepository.findAll();
    }

    // Saves given Ruleset to the DB
    @Override
    public void saveRuleset(Ruleset ruleset) {
        rulesetRepository.save(ruleset);
    }

    // Deletes Ruleset with given ID
    @Override
    public void deleteById(int id) {
        rulesetRepository.deleteById(id);
    }
}
