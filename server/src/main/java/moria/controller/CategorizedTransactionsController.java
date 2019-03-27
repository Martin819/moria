package moria.controller;

import moria.dto.CategoryToUpdateDto;
import moria.dto.TransactionDto;
import moria.services.TransactionService;
import moria.utils.TransactionsToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategorizedTransactionsController {

    private final TransactionService transactionService;

    @Autowired
    public CategorizedTransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Get all categorized transactions
     * @return list of transactions
     */
    @GetMapping(value = "/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return new ResponseEntity<>(loadAllTransactions(), HttpStatus.OK);
    }

    /**
     * Change categoryId of given transaction
     * @param categoryToUpdateDto - contains transaction id and category id
     * @return
     */
    @PutMapping(value = "/transactions/update")
    public ResponseEntity<Void> changeCategory(@RequestBody CategoryToUpdateDto categoryToUpdateDto) {
        transactionService.setCategoryIdForTransactionById(categoryToUpdateDto.getId(), categoryToUpdateDto.getCategoryId());
        transactionService.setManuallyUpdateCategory(categoryToUpdateDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private List<TransactionDto> loadAllTransactions() {
        TransactionsToDtoMapper dtoTransformer = new TransactionsToDtoMapper();
        return dtoTransformer.transformToDto(transactionService.findAllTransactions());
    }


}
