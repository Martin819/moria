package moria.server.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
public class Payment {


    @Getter @Setter private String payer;
    @Getter @Setter private BigDecimal transferMoney;
    @Getter @Setter private boolean incomingPayment;
    @Getter @Setter private Date dateOfPayment;
    @Getter @Setter private String description;



}
