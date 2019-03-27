import java.time.LocalTime;
import java.util.Date;
import moria.ServerApplication;
import moria.controller.IncomingTransactionsController;
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


public class CategoryScorerTest {

  // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
  private CategoryScorer categoryScorer;

  private List<Transaction> transactionList;

  @Before
  public void init() {

    List<Ruleset> myRulesets = new ArrayList<>();
    transactionList = new ArrayList<>();
    myRulesets.add(TestUtils.createTestRulesetFull(2, "Pravidlo 1", "Billa", 111, "OUTGOING", "CARD", new BigDecimal(50), new BigDecimal(2000),
        null, null, null, null, "thanks for your payment", null, null, null, String.valueOf(LocalTime.of(5, 0)),
        String.valueOf(LocalTime.of(8, 0)), "777333666"));

    myRulesets.add(TestUtils.createTestRulesetFull(3, "Pravidlo 2", "Cinestar", 124, "OUTGOING", "CARD", new BigDecimal(70), new BigDecimal(200),
        null, null, "0810", "Cinestar s.r.o", null, null, null, null, String.valueOf(LocalTime.of(19, 0)),
        String.valueOf(LocalTime.of(23, 0)), "777333666"));

    //testuje, ze algoritmus pozna, ze se jedna o transakci, ktera neni ve stejnem smeru
    myRulesets.add(TestUtils.createTestRulesetFull(3, "Pravidlo 3", "Cinestar", 124, "INCOMING", "CARD", new BigDecimal(70), new BigDecimal(200),
        null, null, "0810", null, "Cinestar s.r.o", null, null, null, String.valueOf(LocalTime.of(19, 0)),
        String.valueOf(LocalTime.of(23, 0)), "777333666"));

    TransactionValue transactionValue = TestUtils.createTransactionValue(new BigDecimal(150), "czk");
    transactionList.add(TestUtils.createTransaction("1", 25, transactionValue, new TransactionPartyAccount(), "Spring, Cinestar", "OUTGOING", "CARD",
        TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(20, 30, 0), null, "dekujeme za navstevu kina Cinestar", null,
        null, null, null, null, null, new TransactionAdditionalInfoCard()));

    categoryScorer = new CategoryScorer(myRulesets);
  }

  @Test
  public void testProperCategory() {

    int categoryID = categoryScorer.scoreCategories(transactionList.get(0));

    Assert.assertEquals(124, categoryID);

  }

}
