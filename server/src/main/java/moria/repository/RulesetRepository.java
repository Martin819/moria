package moria.repository;

import moria.model.rules.Ruleset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulesetRepository extends JpaRepository<Ruleset, Integer> {

    Ruleset findById(int id);
    List<Ruleset> findAll();

}
