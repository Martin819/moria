package moria.server.api;

import java.math.BigDecimal;
import org.threeten.bp.OffsetDateTime;
import moria.server.model.Transaction;
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
public class TransactionApiController implements TransactionApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Transaction> addTransaction(@ApiParam(value = "Transaction object that needs to be added" ,required=true )  @Valid @RequestBody Transaction body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Transaction>(objectMapper.readValue("{  \"userDescription\" : \"userDescription\",  \"additionalInfoCard\" : {    \"mcc\" : \"mcc\",    \"cardNumber\" : \"cardNumber\",    \"merchantName\" : \"merchantName\"  },  \"valueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"payerMessage\" : \"payerMessage\",  \"payeeMessage\" : \"payeeMessage\",  \"transactionType\" : \"PAYMENT_HOME\",  \"accountId\" : 0.80082819046101150206595775671303272247314453125,  \"transactionFee\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"bookingDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"id\" : \"id\",  \"partyDescription\" : \"partyDescription\",  \"transactionFeeCanceled\" : true,  \"additionalInfoDomestic\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"additionalInfoForeign\" : {    \"exchangeRate\" : 2.3021358869347654518833223846741020679473876953125,    \"originalValue\" : {      \"amount\" : 5.63737665663332876420099637471139430999755859375,      \"currency\" : \"currency\"    }  },  \"value\" : {    \"amount\" : 6.02745618307040320615897144307382404804229736328125,    \"currency\" : \"currency\"  },  \"categoryId\" : 1.46581298050294517310021547018550336360931396484375,  \"direction\" : \"INCOMING\"}", Transaction.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Transaction>> findTransactionByDate(@NotNull @ApiParam(value = "Datetime from which to load transactions", required = true) @Valid @RequestParam(value = "dateFrom", required = true) OffsetDateTime dateFrom,@NotNull @ApiParam(value = "Datetime to which to load transactions", required = true) @Valid @RequestParam(value = "dateTo", required = true) OffsetDateTime dateTo,@NotNull @ApiParam(value = "accountId for transactions", required = true) @Valid @RequestParam(value = "accountId", required = true) BigDecimal accountId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {  \"userDescription\" : \"userDescription\",  \"additionalInfoCard\" : {    \"mcc\" : \"mcc\",    \"cardNumber\" : \"cardNumber\",    \"merchantName\" : \"merchantName\"  },  \"valueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"payerMessage\" : \"payerMessage\",  \"payeeMessage\" : \"payeeMessage\",  \"transactionType\" : \"PAYMENT_HOME\",  \"accountId\" : 0.80082819046101150206595775671303272247314453125,  \"transactionFee\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"bookingDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"id\" : \"id\",  \"partyDescription\" : \"partyDescription\",  \"transactionFeeCanceled\" : true,  \"additionalInfoDomestic\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"additionalInfoForeign\" : {    \"exchangeRate\" : 2.3021358869347654518833223846741020679473876953125,    \"originalValue\" : {      \"amount\" : 5.63737665663332876420099637471139430999755859375,      \"currency\" : \"currency\"    }  },  \"value\" : {    \"amount\" : 6.02745618307040320615897144307382404804229736328125,    \"currency\" : \"currency\"  },  \"categoryId\" : 1.46581298050294517310021547018550336360931396484375,  \"direction\" : \"INCOMING\"}, {  \"userDescription\" : \"userDescription\",  \"additionalInfoCard\" : {    \"mcc\" : \"mcc\",    \"cardNumber\" : \"cardNumber\",    \"merchantName\" : \"merchantName\"  },  \"valueDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"payerMessage\" : \"payerMessage\",  \"payeeMessage\" : \"payeeMessage\",  \"transactionType\" : \"PAYMENT_HOME\",  \"accountId\" : 0.80082819046101150206595775671303272247314453125,  \"transactionFee\" : 5.962133916683182377482808078639209270477294921875,  \"partyAccount\" : {    \"bankCode\" : \"bankCode\",    \"prefix\" : \"prefix\",    \"accountNumber\" : \"accountNumber\"  },  \"bookingDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"id\" : \"id\",  \"partyDescription\" : \"partyDescription\",  \"transactionFeeCanceled\" : true,  \"additionalInfoDomestic\" : {    \"constantSymbol\" : \"constantSymbol\",    \"variableSymbol\" : \"variableSymbol\",    \"specificSymbol\" : \"specificSymbol\"  },  \"additionalInfoForeign\" : {    \"exchangeRate\" : 2.3021358869347654518833223846741020679473876953125,    \"originalValue\" : {      \"amount\" : 5.63737665663332876420099637471139430999755859375,      \"currency\" : \"currency\"    }  },  \"value\" : {    \"amount\" : 6.02745618307040320615897144307382404804229736328125,    \"currency\" : \"currency\"  },  \"categoryId\" : 1.46581298050294517310021547018550336360931396484375,  \"direction\" : \"INCOMING\"} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
