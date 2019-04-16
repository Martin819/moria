package moria.controller;

import moria.dto.*;
import moria.model.transactions.Transaction;
import moria.services.TransactionService;
import moria.tansactionSplitting.TransactionSplitter;
import moria.utils.TransactionsToDtoMapper;
import moria.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StoredTransactionsController {

    private final TransactionService transactionService;

    @Autowired
    public StoredTransactionsController(TransactionService transactionService) {
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
     * @return ok
     */
    @PutMapping(value = "/transactions/update")
    public ResponseEntity<Void> changeCategory(@RequestBody CategoryToUpdateDto categoryToUpdateDto) {
        transactionService.setCategoryIdForTransactionById(categoryToUpdateDto.getId(), categoryToUpdateDto.getCategoryId());
        transactionService.setManuallyUpdateCategory(categoryToUpdateDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Change categoryId of given transaction
     * @param childTransaction - contains transaction id, amount of money divided from transaction and category id
     * @return created transaction
     */
    @PostMapping(value = "/transactions/split")
    public ResponseEntity<TransactionDto> splitTransaction(@RequestBody ChildTransaction childTransaction) {
        TransactionDto parentTransaction = TransactionSplitter.createSplittedTransaction(childTransaction);
        return new ResponseEntity<>(parentTransaction, HttpStatus.CREATED);
    }

    /**
     * Remove transaction
     * @param categoryID - transaction id to remove
     * @return created transaction
     */
    @PostMapping(value = "/transactions/removeSplit")
    public ResponseEntity<TransactionDto> removeSplitTransaction(@RequestBody CategoryID categoryID) {
        TransactionDto transactionDto = TransactionSplitter.removeSplittedTransaction(categoryID.getId());
        return new ResponseEntity<>(transactionDto, HttpStatus.OK);
    }


    @GetMapping(value = "/test/dtoTransformation")
    private List<TransactionDto> loadAllTransactions() {
        TransactionsToDtoMapper dtoTransformer = new TransactionsToDtoMapper();
        List<Transaction> tList = transactionService.findAllTransactions();
        List<TransactionDto> transactionDtos = dtoTransformer.transformToDto(tList);
        return Utils.bindParentAndChildTransactions(transactionDtos);
    }


}
