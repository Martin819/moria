package moria.server.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
public class Payment {


    @Getter @Setter private String payer;
    @Getter @Setter private boolean incomingPayment;
    @Getter @Setter private Date dateOfPayment;
    @Getter @Setter private String description;


}
