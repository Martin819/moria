package moria.services;


import moria.model.rules.Ruleset;
import moria.repository.RulesetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Ruleset saveRuleset(Ruleset ruleset) {
        return rulesetRepository.save(ruleset);
    }

    // Deletes Ruleset with given ID
    @Override
    public void deleteById(int id) {
        rulesetRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIdIn(List<Integer> ids) {
        rulesetRepository.deleteByIdIn(ids);
    }
}
