package moria.utils;

import moria.SpringContext;
import moria.dto.ChildTransaction;
import moria.dto.TransactionDto;
import moria.model.transactions.*;
import moria.services.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.*;

public class utils {

    private static TransactionServiceImpl getTransactionService() {
        return SpringContext.getBean(TransactionServiceImpl.class);
    }

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

    public static List<TransactionDto> bindParentAndChildTransactions(List<TransactionDto> transactionList) {
        for (TransactionDto transaction : transactionList) {
            List<ChildTransaction> childTransactions = findChildTransactions(transaction);
            transaction.setChildTransactionsList(childTransactions);
        }
        return transactionList;
    }

    public static List<ChildTransaction> findChildTransactions(TransactionDto t) {
        TransactionServiceImpl transactionService = getTransactionService();
        List<Transaction> childrenList = transactionService.findByParentId(t.getId());
        return getChildTransactions(childrenList);
    }

    public static String getNormalizedAccountNumber(TransactionPartyAccount a) {
        return getNormalizedAccountNumber(a.getPrefix(), a.getAccountNumber(), a.getBankCode());
    }

    public static String getNormalizedAccountNumber(String prefix, String number, String bankCode) {
        prefix = prefix.replaceFirst("^0+(?!$)", "");
        number = number.replaceFirst("^0+(?!$)", "");
        bankCode = bankCode.replaceFirst("^0+(?!$)", "");
        return prefix + "-" + number + "/" + bankCode;
    }

    public static List<ChildTransaction> getChildTransactions(List<Transaction> childTransactionList) {
        List<ChildTransaction> childList = new ArrayList<>();
        for (Transaction transaction : childTransactionList) {
            ChildTransaction child = new ChildTransaction(transaction.getId(), transaction.getCategoryId(), transaction.getValue().getAmount());
            childList.add(child);
        }
        return childList;
    }

}