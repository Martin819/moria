package moria.services;

import moria.model.rules.Ruleset;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RulesetService {

    Ruleset findRulesetById(int id);

    List<Ruleset> findAllRulesets();

    void saveRuleset(Ruleset ruleset);

    void deleteById(int id);
}
