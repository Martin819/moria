import moria.ServerApplication;
import moria.model.rules.Ruleset;
import moria.services.RulesetService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class) // zajisti, ze se anotace vubec prectou
@SpringBootTest //nastavi aplikacni kontext
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED) //nastavi testovaci databazi (H2), by default se databáze smaže po každém testu
@ContextConfiguration(classes = {ServerApplication.class}) // melo by zajistovat to stejne jako SpringBootTest, ale z nejakeho duvodu je tato anotace potreba k tomu, aby se injektovala service  (nejaka jebárna, v ofiko dokumentaci staci jen prvni dve anotace..obecne ta konfigurace byla porod)
public class RulesetServiceTest {

    // jedna se o integracni test, vzhledem k tomu, ze testuje vice aplikacnich vrstev (service, repo, db)
    // proto je potreba servisu nainjektovat..
    // (v obycejnem unit testu staci servisu mockovat)

    @Autowired //nainjektujem servisu
    private RulesetService rulesetService;

    private Ruleset ruleset;

    @Before // takto anotovana metoda probehne VZDY pred kazdym testem.. takze sem jebnu veci, ktere potrebuju pro kazdy test
    public void init() {
        ruleset = TestUtils.createTestRuleset(1, "INCOMING", 111, "PAYMENT_HOME",
                new BigDecimal("1000.00"), new BigDecimal("2000.00"));
        rulesetService.saveRuleset(ruleset);
    }

    @Test
    public void findTransactionByIdTest() { // konvence - nazev metody, kterou testujem + Test, vraci void, bez parametru
        Ruleset rule = rulesetService.findRulesetById(1);

        //Assert - trida poskytujici porovnavaci metody
        //v tomto pripade porovnava, jestli se ulozeny objekt shoduje s tim, ktere jsme podle id vytahli
        //prvni parametr ruleset - ocekavany objekt (expected)
        //druhy parametr rule - ten objekt, ktery jsme vytahli z db (actual)
        Assert.assertEquals(ruleset, rule);

        //porovnava konkretni hodnoty
        Assert.assertEquals(ruleset.getValueFrom(), rule.getValueFrom());

    }

    @Test
    public void findAllRulesetsTest() {
        List<Ruleset> rulesets = rulesetService.findAllRulesets();

        // testuju jestli se vraci pocet rulesetu, kolik ocekavam
        Assert.assertEquals(1, rulesets.size());
    }


}