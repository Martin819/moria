import java.time.LocalTime;

import moria.model.rules.Ruleset;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionAdditionalInfoCard;
import moria.model.transactions.TransactionPartyAccount;
import moria.model.transactions.TransactionValue;
import moria.transactionCategorization.CategoryFinder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CategoryFinderTest {

    // vytvori jen mocknutou servisu, ktera nebude sahat do databaze
    private CategoryFinder categoryFinder;

    private List<Transaction> testTransactionsList;

    @Before
    public void init() {

        List<Ruleset> testRulesetsList = new ArrayList<>();
        testTransactionsList = new ArrayList<>();

        // TODO použít opravdová data z db, jinak je to blázinec

        //  ----------------------------------------
        //  RULESETY ciste pro testovani
        //  ----------------------------------------

        //testuje, ze algoritmus pozna, ze se jedna o transakci, ktera neni ve stejnem smeru
        testRulesetsList.add(TestUtils.createTestRuleset(2, "Pravidlo 1", 111, "Billa", "OUTGOING", "CARD", new BigDecimal(50), new BigDecimal(2000),
                null, null, null, null, "thanks for your payment", null, null, null, String.valueOf(LocalTime.of(5, 0)),
                String.valueOf(LocalTime.of(8, 0)), "777333666"));

        testRulesetsList.add(TestUtils.createTestRuleset(3, "Pravidlo 2", 124, "cinestar", "OUTGOING", "CARD", new BigDecimal(70), new BigDecimal(200),
                null, null, "0810", "Cinestar s.r.o", null, null, null, null, String.valueOf(LocalTime.of(19, 0)),
                String.valueOf(LocalTime.of(23, 0)), "777333666"));

        testRulesetsList.add(TestUtils.createTestRuleset(3, "Pravidlo 3", 124, "Cinestar", "INCOMING", "CARD", new BigDecimal(70), new BigDecimal(200),
                null, null, "0810", null, "Cinestar s.r.o", null, null, null, String.valueOf(LocalTime.of(19, 0)),
                String.valueOf(LocalTime.of(23, 0)), "777333666"));


        //  ----------------------------------------
        //  RULESETY z import.sql
        //  ----------------------------------------

        //  zamyslene jako uzivatelsky definovane (shodou okolnosti oboji VYDAJE)
        testRulesetsList.add(TestUtils.createTestRuleset(1, "Mortgage monthly payments", 128, "Hypotecni banka", "OUTGOING", "MORTGAGE", null, null, null, null, null, null, null, null, null, null, null, null, null));
        testRulesetsList.add(TestUtils.createTestRuleset(2, "ATM", 131, null, "OUTGOING", "CASH", null, null, null, null, null, null, null, null, null, null, null, null, null));
        //  zamyslene jako uzivatelsky definovane (shodou okolnosti oboji PRIJMY)
        testRulesetsList.add(TestUtils.createTestRuleset(3, "My main income", 11, null, "INCOMING", "PAYMENT_HOME", new BigDecimal(15000.00), new BigDecimal(25000.00), "67010000", "717717717", "0600", null, null, null, null, null, null, null, null));
        testRulesetsList.add(TestUtils.createTestRuleset(4, "My drinking buddy", 17, null, "INCOMING", "PAYMENT_HOME", null, null, "00000000", "191816107239", "0800", null, null, null, null, null, null, null, null));
        testRulesetsList.add(TestUtils.createTestRuleset(5, "My addiction", 124, "Steam", "OUTGOING", "CARD", null, null, null, null, null, null, null, null, null, null, null, null, null));


        //  ----------------------------------------
        //  TRANSAKCE ciste pro testovani
        //  ----------------------------------------

        TransactionValue transactionValue0 = TestUtils.createTransactionValue(new BigDecimal(150), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("1", 25, transactionValue0, new TransactionPartyAccount(), "Spring, Cinestar", "OUTGOING", "CARD",
                TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(20, 30, 0), null, null, "dekujeme za navstevu kina Cinestar",
                null, null, null, null, null, null, false));


        //  ----------------------------------------
        //  TRANSAKCE z import.sql
        //  ----------------------------------------

        // vydaj - splatka hypoteky
        TransactionValue transactionValue4 = TestUtils.createTransactionValue(new BigDecimal(4099), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("4", 6666, transactionValue4, new TransactionPartyAccount("00000001", "785845484688", "1500"), "Hypotecni banka", "OUTGOING", "MORTGAGE", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "Splatka hypoteky", null, null, null, null, null, null, null, null, null));

        // prijem - kamos splatil dluh
        TransactionValue transactionValue7 = TestUtils.createTransactionValue(new BigDecimal(262), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("7", 6666, transactionValue7, new TransactionPartyAccount("00000000", "191816107239", "0800"), null, "INCOMING", "PAYMENT_HOME", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "amigo", null, null, null, null, null, null, null, null, null));

        // prijem - vyplata
        TransactionValue transactionValue12 = TestUtils.createTransactionValue(new BigDecimal(17325), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("12", 6666, transactionValue12, new TransactionPartyAccount("67010000", "717717717", "0600"), "McDonalds", "INCOMING", "PAYMENT_HOME", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "Muj zamestanavatel", null, null, null, null, null, null, null, null, null));

        // vydaj - nakum na Steamu
        TransactionValue transactionValue17 = TestUtils.createTransactionValue(new BigDecimal(1963), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("17", 6666, transactionValue17, null, "STEAM STORE", "OUTGOING","CARD", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), null, null, null, null, null, null, null, null, new TransactionAdditionalInfoCard("3542658921352648","4955","Steam Store"), null));

        // vydaj - vyber z bankomatu
        TransactionValue transactionValue18 = TestUtils.createTransactionValue(new BigDecimal(5000), "czk");
        testTransactionsList.add(TestUtils.createTestTransaction("18", 6666, transactionValue18, new TransactionPartyAccount(), "CSOB", "OUTGOING", "CASH", TestUtils.parseDate(8, 30, 0), TestUtils.parseDate(8, 30, 0), "vyber z bankomatu", null, null, null, null, null, null, null, null, null));

        categoryFinder = new CategoryFinder(testRulesetsList);
    }

    @Test
    public void testProperCategorization() {

        // ruleset ciste pro otestovani
        int categoryID_0 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(0));
        Assert.assertEquals(124, categoryID_0);

        // rulesety z import.sql
        int categoryID_1 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(1));
        Assert.assertEquals(128, categoryID_1);

        int categoryID_2 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(2));
        Assert.assertEquals(17, categoryID_2);

        int categoryID_3 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(3));
        Assert.assertEquals(11, categoryID_3);

        int categoryID_4 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(4));
        Assert.assertEquals(124, categoryID_4);

        int categoryID_5 = categoryFinder.findBestMatchingCategory(testTransactionsList.get(5));
        Assert.assertEquals(131, categoryID_5);

    }

}
