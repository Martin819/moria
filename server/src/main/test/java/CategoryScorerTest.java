import moria.ServerApplication;
import moria.dto.Category;
import moria.model.rules.Ruleset;
import moria.services.RulesetService;
import moria.utils.CategoryScorer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {ServerApplication.class})
public class CategoryScorerTest {

    @MockBean // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
    private RulesetService rulesetService;

    @Test
    public void testExample() {
        List<Ruleset> myRulesets = new ArrayList<>(Arrays.asList(TestUtils.createTestRuleset(1, "INCOMING", 111, "PAYMENT_HOME",
                new BigDecimal("1000.00"), new BigDecimal("2000.00"))));

        // az budes testovat jakoukoliv logiku, tak si budes muset namockovat data, ktera by jinak realne prisla z databaze
        // napriklad v pripade, az se v prubehu testu zavola rulesetService.findAllRulesets(), tak se nebude sahat do databaze, ale vrati se myRulesets
        Mockito.when(rulesetService.findAllRulesets()).thenReturn(myRulesets);

        // jen tu testuju, ze se opravdu vratilo myRulesets
        Assert.assertEquals(myRulesets, rulesetService.findAllRulesets());


        //TODO - Vláďa
        //priklad, jak bys mohl udelat test obecne na cely CategoryScorer
            //vytvoris testovaci ruleset pres TestUtils
            //vytvoris testovaci transakci pres TestUtils
            //zavolas metodu scoreCategories
            // assertujes, ze id kategorie, kterou ti vrati scoreCategories je ta, kterou ocekavas
    }

}
