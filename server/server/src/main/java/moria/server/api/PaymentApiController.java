package moria.server.api;

import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import moria.server.model.Payment;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-02-27T08:59:02.143Z")

@Controller
public class PaymentApiController implements PaymentApi {

    private static final Logger log = LoggerFactory.getLogger(PaymentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public PaymentApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Payment> addPayment(@ApiParam(value = "Payment object that needs to be added" ,required=true )  @Valid @RequestBody Payment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Payment>(objectMapper.readValue("{  \"accountId\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"dueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"realizationStatus\" : \"RTS_EDITED\",  \"additionalInfo\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"id\" : 1.46581298050294517310021547018550336360931396484375,  \"value\" : {    \"amount\" : 0.80082819046101150206595775671303272247314453125,    \"currency\" : \"currency\"  },  \"payerMessage\" : \"payerMessage\",  \"categoryId\" : 6.02745618307040320615897144307382404804229736328125,  \"editableByUser\" : true,  \"recuringPayment\" : {    \"firstPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"lastPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"interval\" : \"WEEK\"  },  \"payeeMessage\" : \"payeeMessage\"}", Payment.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Payment>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Payment>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Payment>> findPaymentByDate(@NotNull @ApiParam(value = "Datetime from which to load payments", required = true) @Valid @RequestParam(value = "dateFrom", required = true) OffsetDateTime dateFrom,@NotNull @ApiParam(value = "Datetime to which to load payments", required = true) @Valid @RequestParam(value = "dateTo", required = true) OffsetDateTime dateTo,@NotNull @ApiParam(value = "accountId for transactions", required = true) @Valid @RequestParam(value = "accountId", required = true) BigDecimal accountId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Payment>>(objectMapper.readValue("[ {  \"accountId\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"dueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"realizationStatus\" : \"RTS_EDITED\",  \"additionalInfo\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"id\" : 1.46581298050294517310021547018550336360931396484375,  \"value\" : {    \"amount\" : 0.80082819046101150206595775671303272247314453125,    \"currency\" : \"currency\"  },  \"payerMessage\" : \"payerMessage\",  \"categoryId\" : 6.02745618307040320615897144307382404804229736328125,  \"editableByUser\" : true,  \"recuringPayment\" : {    \"firstPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"lastPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"interval\" : \"WEEK\"  },  \"payeeMessage\" : \"payeeMessage\"}, {  \"accountId\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"dueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"realizationStatus\" : \"RTS_EDITED\",  \"additionalInfo\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"id\" : 1.46581298050294517310021547018550336360931396484375,  \"value\" : {    \"amount\" : 0.80082819046101150206595775671303272247314453125,    \"currency\" : \"currency\"  },  \"payerMessage\" : \"payerMessage\",  \"categoryId\" : 6.02745618307040320615897144307382404804229736328125,  \"editableByUser\" : true,  \"recuringPayment\" : {    \"firstPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"lastPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"interval\" : \"WEEK\"  },  \"payeeMessage\" : \"payeeMessage\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Payment>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Payment>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Payment> updatePayment(@ApiParam(value = "Payment object that needs to be updated" ,required=true )  @Valid @RequestBody Payment body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Payment>(objectMapper.readValue("{  \"accountId\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"dueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"realizationStatus\" : \"RTS_EDITED\",  \"additionalInfo\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"id\" : 1.46581298050294517310021547018550336360931396484375,  \"value\" : {    \"amount\" : 0.80082819046101150206595775671303272247314453125,    \"currency\" : \"currency\"  },  \"payerMessage\" : \"payerMessage\",  \"categoryId\" : 6.02745618307040320615897144307382404804229736328125,  \"editableByUser\" : true,  \"recuringPayment\" : {    \"firstPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"lastPayment\" : \"2000-01-23T04:56:07.000+00:00\",    \"interval\" : \"WEEK\"  },  \"payeeMessage\" : \"payeeMessage\"}", Payment.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Payment>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Payment>(HttpStatus.NOT_IMPLEMENTED);
    }

}
