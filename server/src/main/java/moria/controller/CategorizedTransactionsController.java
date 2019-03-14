package moria.controller;

import moria.dto.TransactionDto;
import moria.services.TransactionService;
import moria.utils.TransactionsToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategorizedTransactionsController {

    @Autowired
    private TransactionService transactionService;


    /**
     * Get all categorized transactions
     * @return list of transactions
     */
    @GetMapping(value = "/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return new ResponseEntity<>(loadAllTransactions(), HttpStatus.OK);
    }

    private List<TransactionDto> loadAllTransactions() {
        TransactionsToDtoMapper dtoTransformer = new TransactionsToDtoMapper();
        return dtoTransformer.transformToDto(transactionService.findAllTransactions());
    }


}
