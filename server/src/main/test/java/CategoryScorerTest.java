import java.time.LocalTime;
import java.util.Date;
import moria.ServerApplication;
import moria.dto.Category;
import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionAdditionalInfoCard;
import moria.model.transactions.TransactionPartyAccount;
import moria.model.transactions.TransactionValue;
import moria.services.RulesetService;
import moria.services.TransactionService;
import moria.utils.CategoryScorer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@ContextConfiguration(classes = {ServerApplication.class})
public class CategoryScorerTest {

  @MockBean // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
  private RulesetService rulesetService;

  @MockBean // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
  private TransactionService transactionService;

  @MockBean // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
  private CategoryScorer categoryScorer;

  private List<Ruleset> myRulesets;
  private List<Transaction> transactionList;

  @Before
  public void init() {
    myRulesets = new ArrayList<>();
    transactionList = new ArrayList<>();
    myRulesets.add(TestUtils.createTestRulesetFull(2, "Pravidlo 1", "Billa", 111, "OUTGOING", "CARD", new BigDecimal(50), new BigDecimal(2000),
        null, null, null, "thanks for your payment", null, null, null, null, String.valueOf(LocalTime.of(5, 0)),
        String.valueOf(LocalTime.of(8, 0)), "777333666"));

    myRulesets.add(TestUtils.createTestRulesetFull(3, "Pravidlo 2", "Cinestar", 124, "OUTGOING", "CARD", new BigDecimal(70), new BigDecimal(200),
        null, null, "0810", "Cinestar s.r.o", null, null, null, null, String.valueOf(LocalTime.of(19, 0)),
        String.valueOf(LocalTime.of(23, 0)), "777333666"));

    TransactionValue transactionValue = TestUtils.createTransactionValue(new BigDecimal(150), "czk");
    transactionList.add(TestUtils.createTransaction(1, 25, transactionValue, new TransactionPartyAccount(), "Spring, Cinestar", "OUTGOING", "CARD",
        TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(20, 30, 0), null, "dekujeme za navstevu kina Cinestar", null,
        null, null, null, null, null, new TransactionAdditionalInfoCard()));

    myRulesets.forEach(rulesetService::saveRuleset);

    categoryScorer = new CategoryScorer();
  }

  @Test
  public void testExample() {
    myRulesets = new ArrayList<>(Arrays.asList(TestUtils.createTestRuleset(1, "INCOMING", 111, "PAYMENT_HOME",
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

  @Test
  public void testProperCategory() {


    categoryScorer.setListRuleset(myRulesets);
    int categoryID = categoryScorer.scoreCategories(transactionList.get(0));

    Assert.assertEquals(categoryID, 124);

  }

  @Test
  public void testSettingCategory() {

    CategoryScorer categoryScorer = new CategoryScorer();
    categoryScorer.setListRuleset(myRulesets);
    int categoryID = categoryScorer.scoreCategories(transactionList.get(0));

    transactionService.setCategoryIdForTransactionById(transactionList.get(0).getId(), categoryID);
    Transaction transaction = transactionService.findTransactionById(transactionList.get(0).getId());

    Assert.assertEquals(transaction.getCategoryId(), Integer.valueOf(categoryID));

  }

}
