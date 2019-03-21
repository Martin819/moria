import moria.model.rules.Ruleset;

import java.math.BigDecimal;

/**
 * Trida pro pomocne metody k testovani
 */
class TestUtils {

    //parametry upravuj dle libosti
    static Ruleset createTestRuleset(int id, String direction, int categoryId, String transactionType, BigDecimal valueFrom, BigDecimal valueTo) {
        Ruleset ruleset = new Ruleset();
        ruleset.setId(id);
        ruleset.setDirection(direction);
        ruleset.setCategoryId(categoryId);
        ruleset.setTransactionType(transactionType);
        ruleset.setValueFrom(valueFrom);
        ruleset.setValueTo(valueTo);
        return ruleset;
    }

    //stejnym zpusobem napriklad createTransaction
}
