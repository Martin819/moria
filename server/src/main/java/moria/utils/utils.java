package moria.utils;

import moria.model.transactions.*;

import java.math.BigDecimal;

public class utils {

    public static Transaction verifyTransactionForNullValues(Transaction t) {

        if (t.getAdditionalInfoCard() == null) {
            t.setAdditionalInfoCard(new TransactionAdditionalInfoCard("", "", ""));
        }
        if (t.getAdditionalInfoDomestic() == null) {
            t.setAdditionalInfoDomestic(new TransactionAdditionalInfoDomestic("", "", ""));
        }
        if (t.getAdditionalInfoForeign() == null) {
            t.setAdditionalInfoForeign(new TransactionAdditionalInfoForeign(new TransactionForeignOriginalValue(new BigDecimal(0), ""), new BigDecimal(0)));
        }

        return t;
    }
}
