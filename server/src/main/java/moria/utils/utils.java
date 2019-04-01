package moria.utils;

import moria.SpringContext;
import moria.model.transactions.*;
import moria.services.TransactionServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<Transaction> bindParentAndChildTransactions(List<Transaction> tList) {
        TransactionServiceImpl transactionService = getTransactionService();
        ArrayList<String> categorizedParentTransactions = new ArrayList<>();
        for (Transaction transaction : tList) {
            if (transaction.getParentId() != null && !transaction.getParentId().isEmpty()) {
                String parentId = transaction.getParentId();
                Transaction parentTransaction = new Transaction();
                for (Transaction t : tList) {
                    if (t.getId().equals(parentId)) {
                        parentTransaction = t;
                        break;
                    }
                }
                if (parentTransaction.getOriginalValue() == null) {
                    parentTransaction.setOriginalValue(parentTransaction.getValue().getAmount());
                    transactionService.setOriginalValueById(parentId, parentTransaction.getValue().getAmount());
                }
                if (!categorizedParentTransactions.contains(parentId)) {
                    System.out.println(transaction);
                    parentTransaction.setCategories(getCategoriesForParentJob(parentTransaction));
                    System.out.println(parentTransaction);
                    categorizedParentTransactions.add(parentId);
                }
            }
        }
        return tList;
    }

    public static Map<Integer, BigDecimal> getCategoriesForParentJob(Transaction t) {
        TransactionServiceImpl transactionService = getTransactionService();
        List<Transaction> childrenList = transactionService.findByParentId(t.getId());
        Map<Integer, BigDecimal> categories = new HashMap<>();
        BigDecimal categorizedValue = new BigDecimal(0);
        System.out.println(childrenList);
        if (childrenList.size() > 0) {
//            transactionService.setOriginalValueById(t.getId(), t.getValue().getAmount());
            for (Transaction child : childrenList) {
                categorizedValue = categorizedValue.add(child.getValue().getAmount());
                categories.put(child.getCategoryId(), child.getValue().getAmount());
            }
            BigDecimal remainingAmount = t.getOriginalValue().subtract(categorizedValue);
            if (remainingAmount.compareTo(new BigDecimal(0)) > 0) {
                categories.put(0, remainingAmount);
            }
            transactionService.setValueAmountById(t.getId(), new BigDecimal(0));
            System.out.println(categories);
        }
        return categories;
    }

    public static String getNormalizedAccountNumber(TransactionPartyAccount a) {
        return getNormalizedAccountNumber(a.getPrefix(), a.getAccountNumber(), a.getBankCode());
    }

    public static String getNormalizedAccountNumber (String prefix, String number, String bankCode) {
        prefix = prefix.replaceFirst("^0+(?!$)", "");
        number = number.replaceFirst("^0+(?!$)", "");
        bankCode = bankCode.replaceFirst("^0+(?!$)", "");
        return prefix + "-" + number + "/" + bankCode;
    }
}