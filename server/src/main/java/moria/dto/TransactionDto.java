package moria.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
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
    private Map<Integer, BigDecimal> categories;

    //... SPOLECNE PRO VSECHNY ..//
    private BigDecimal  transactionValueAmount;
    private String  transactionValueCurrency;
    private Date valueDate;
    private String transactionType;
    private int categoryId;

}
