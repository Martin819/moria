package moria.model.rules;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity(name = "rulesets")
public class Ruleset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ruleName;

    // COMMON
    private String partyName;
    private int categoryId;
    private String direction;
    private String transactionType;
    private BigDecimal valueFrom;
    private BigDecimal valueTo;

    // BANK TRANSFER
    private String partyAccountPrefix;
    private String partyAccountNumber;
    private String partyBankCode;
    private String payerMessage;
    private String payeeMessage;
    private String constantSymbol;
    private String variableSymbol;
    private String specificSymbol;

    // CARDS
    private String bookingTimeFrom;
    private String bookingTimeTo;
    private String cardNumber;

}