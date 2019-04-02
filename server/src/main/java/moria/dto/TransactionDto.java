package moria.dto;

import lombok.Data;
import moria.model.transactions.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class TransactionDto {

    private String id;

    //... PLATBA KARTOU ..//

    private String direction;

    // cislo uctu
    private String transactionPartyAccountPrefix;
    private String transactionPartyAccountAccountNumber;
    private String transactionPartyAccountBankCode;

    // jmeno/popis uctu?
    private String partyDescription;

    // platebni symboly
    private String transactionAdditionalInfoDomesticConstantSymbol;
    private String transactionAdditionalInfoDomesticVariableSymbol;
    private String transactionAdditionalInfoDomesticSpecificSymbol;

    // zprava
    private String payeeMessage;
    private String payerMessage;


    //... PLATBA KARTOU ..//

    private String transactionAdditionalInfoCardMerchantName;


    //... VYBER Z BANKOMATU ..//
    private String parentId;
    private BigDecimal originalValue;
    private List<ChildTransaction> childTransactionsList;

    //... SPOLECNE PRO VSECHNY ..//
    private BigDecimal  transactionValueAmount;
    private String  transactionValueCurrency;
    private Date valueDate;
    private String transactionType;
    private int categoryId;

    public TransactionDto(Transaction transaction, List<ChildTransaction> categories) {
        id = transaction.getId();
        direction = transaction.getDirection();

        if (transaction.getPartyAccount() != null){
            transactionPartyAccountPrefix = transaction.getPartyAccount().getPrefix();
            transactionPartyAccountAccountNumber = transaction.getPartyAccount().getAccountNumber();
            transactionPartyAccountBankCode = transaction.getPartyAccount().getBankCode();
        }


        partyDescription = transaction.getPartyDescription();

        payerMessage = transaction.getPayerMessage();
        payeeMessage = transaction.getPayeeMessage();

        if (transaction.getAdditionalInfoCard() != null )transactionAdditionalInfoCardMerchantName = transaction.getAdditionalInfoCard().getMerchantName();
        parentId = transaction.getParentId();
        originalValue = transaction.getOriginalValue();
        childTransactionsList = categories;

        transactionValueAmount = transaction.getValue().getAmount();
        transactionValueCurrency = transaction.getValue().getCurrency();
        valueDate = transaction.getValueDate();
        transactionType = transaction.getTransactionType();
        categoryId = transaction.getCategoryId();
    }

}
