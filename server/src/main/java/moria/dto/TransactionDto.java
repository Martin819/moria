package moria.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDto {

    private int id;

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

    // zprava
    private String payeeMessage;
    private String payerMessage;


    //... PLATBA KARTOU ..//

    private String transactionAdditionalInfoCardMerchantName;


    //... VYBER Z BANKOMATU ..//
    // ?

    //... SPOLECNE PRO VSECHNY ..//
    private BigDecimal  transactionValueAmount;
    private String  transactionValueCurrency;
    private Date valueDate;
    private String transactionType;
    private int categoryId;

}
