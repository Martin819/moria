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

    @Override
    public Ruleset findRulesetById(int id) {
        return rulesetRepository.findById(id);
    }

    @Override
    public List<Ruleset> findAllRulesets() {
        return rulesetRepository.findAll();
    }
}
