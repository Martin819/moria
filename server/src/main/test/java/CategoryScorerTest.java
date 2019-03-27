import java.time.LocalTime;

import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionAdditionalInfoCard;
import moria.model.transactions.TransactionPartyAccount;
import moria.model.transactions.TransactionValue;
import moria.utils.CategoryScorer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CategoryScorerTest {

    // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
    private CategoryScorer categoryScorer;

    private List<Transaction> transactionList;

    @Before
    public void init() {

        List<Ruleset> myRulesets = new ArrayList<>();
        transactionList = new ArrayList<>();


        //  ----------------------------------------
        //  RULESETY ciste pro testovani
        //  ----------------------------------------

        //testuje, ze algoritmus pozna, ze se jedna o transakci, ktera neni ve stejnem smeru
        myRulesets.add(TestUtils.createTestRuleset(2, "Pravidlo 1", 111, "Billa", "OUTGOING", "CARD", new BigDecimal(50), new BigDecimal(2000),
                null, null, null, null, "thanks for your payment", null, null, null, String.valueOf(LocalTime.of(5, 0)),
                String.valueOf(LocalTime.of(8, 0)), "777333666"));

        myRulesets.add(TestUtils.createTestRuleset(3, "Pravidlo 2", 124, "Cinestar", "OUTGOING", "CARD", new BigDecimal(70), new BigDecimal(200),
                null, null, "0810", "Cinestar s.r.o", null, null, null, null, String.valueOf(LocalTime.of(19, 0)),
                String.valueOf(LocalTime.of(23, 0)), "777333666"));

        myRulesets.add(TestUtils.createTestRuleset(3, "Pravidlo 3", 124, "Cinestar", "INCOMING", "CARD", new BigDecimal(70), new BigDecimal(200),
                null, null, "0810", null, "Cinestar s.r.o", null, null, null, String.valueOf(LocalTime.of(19, 0)),
                String.valueOf(LocalTime.of(23, 0)), "777333666"));


        //  ----------------------------------------
        //  RULESETY z import.sql
        //  ----------------------------------------

        //  zamyslene jako uzivatelsky definovane (shodou okolnosti oboji VYDAJE)
        myRulesets.add(TestUtils.createTestRuleset(1, "Mortgage monthly payments", 128, "CSOB", "OUTGOING", "MORTGAGE", null, null, null, null, null, null, null, null, null, null, null, null, null));
        myRulesets.add(TestUtils.createTestRuleset(2, "ATM", 131, null, "OUTGOING", "CASH", null, null, null, null, null, null, null, null, null, null, null, null, null));
        //  zamyslene jako uzivatelsky definovane (shodou okolnosti oboji PRIJMY)
        myRulesets.add(TestUtils.createTestRuleset(3, "My main income", 11, null, "INCOMING", "PAYMENT_HOME", new BigDecimal(15000.00), new BigDecimal(25000.00), "00000865", "654651579612", "0600", null, null, null, null, null, null, null, null));
        myRulesets.add(TestUtils.createTestRuleset(4, "My drinking buddy", 17, null, "INCOMING", "PAYMENT_HOME", null, null, "00000000", "191816107239", "0800", null, null, null, null, null, null, null, null));


        //  ----------------------------------------
        //  TRANSAKCE ciste pro testovani
        //  ----------------------------------------

        TransactionValue transactionValue0 = TestUtils.createTransactionValue(new BigDecimal(150), "czk");
        transactionList.add(TestUtils.createTransaction("1", 25, transactionValue0, new TransactionPartyAccount(), "Spring, Cinestar", "OUTGOING", "CARD",
                TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(20, 30, 0), null, null, "dekujeme za navstevu kina Cinestar",
                null, null, null, null, null, null, false));


        //  ----------------------------------------
        //  TRANSAKCE z import.sql
        //  ----------------------------------------

        // prijem - vyplata
        TransactionValue transactionValue2 = TestUtils.createTransactionValue(new BigDecimal(17325), "czk");
        transactionList.add(TestUtils.createTransaction("2", 6666, transactionValue2, new TransactionPartyAccount("00000865", "654651579612", "0600"), "McDonalds", "INCOMING", "PAYMENT_HOME", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "Muj zamestanavatel", null, null, null, null, null, null, null, null, null));

        // vydaj - splatka hypoteky
        TransactionValue transactionValue6 = TestUtils.createTransactionValue(new BigDecimal(4099), "czk");
        transactionList.add(TestUtils.createTransaction("6", 6666, transactionValue6, new TransactionPartyAccount("00000001", "785845484688", "1500"), "Hypotecni banka", "OUTGOING", "MORTGAGE", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "Splatka hypoteky", null, null, null, null, null, null, null, null, null));

        // prijem - kamos splatil dluh
        TransactionValue transactionValue12 = TestUtils.createTransactionValue(new BigDecimal(262), "czk");
        transactionList.add(TestUtils.createTransaction("12", 6666, transactionValue12, new TransactionPartyAccount("00000000", "191816107239", "0800"), null, "INCOMING", "PAYMENT_HOME", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "amigo", null, null, null, null, null, null, null, null, null));

        // vydaj - vyber z bankomatu
        TransactionValue transactionValue16 = TestUtils.createTransactionValue(new BigDecimal(5000), "czk");
        transactionList.add(TestUtils.createTransaction("16", 6666, transactionValue16, new TransactionPartyAccount(), "CSOB", "OUTGOING", "CASH", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "vyber z bankomatu", null, null, null, null, null, null, null, null, null));


        categoryScorer = new CategoryScorer(myRulesets);
    }

    @Test
    public void testProperCategorization() {

        // ruleset ciste pro otestovani
        int categoryID_0 = categoryScorer.scoreCategories(transactionList.get(0));
        Assert.assertEquals(124, categoryID_0);

        // rulesety z import.sql
        int categoryID_1 = categoryScorer.scoreCategories(transactionList.get(1));
        Assert.assertEquals(11, categoryID_1);

        int categoryID_2 = categoryScorer.scoreCategories(transactionList.get(2));
        Assert.assertEquals(128, categoryID_2);

        int categoryID_3 = categoryScorer.scoreCategories(transactionList.get(3));
        Assert.assertEquals(17, categoryID_3);

        int categoryID_4 = categoryScorer.scoreCategories(transactionList.get(4));
        Assert.assertEquals(131, categoryID_4);

    }

}
