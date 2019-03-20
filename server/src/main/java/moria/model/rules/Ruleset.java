package moria.model.rules;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity(name = "rulesets")
public class Ruleset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // VALUE
    private BigDecimal valueFromValue;
    private BigDecimal valueToValue;
    private String direction;
    // PARTY ACCOUNT
    private String partyPrefixValue;
    private String partyAccountNumberValue;
    private String partyBankCodeValue;
    private String partyDescriptionValue;
    // DATE
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateFromValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateToValue;
    // DESCRIPTIONS
    private String transactionType;
    private String userDescriptionValue;
    private String payerMessageValue;
    private String payeeMessageValue;
    // SYMBOLS
    private String constantSymbolValue;
    private String variableSymbolValue;
    private String specificSymbolValue;
    // CARDS
    private String merchantNameValue;
    private String cardNumberValue;
    //CATEGORY ID
    private int categoryId;
}