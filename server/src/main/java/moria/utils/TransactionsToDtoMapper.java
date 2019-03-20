package moria.utils;

import moria.dto.TransactionDto;
import moria.model.transactions.Transaction;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class TransactionsToDtoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    /**
     * Mapuje db entitu Transaction na TransactionDto
     * @param transactions - vsechny transakce z databaze
     * @return seznam transakci v podobe, ktera se bude posilat na FE
     */
    public List<TransactionDto> transformToDto(List<Transaction> transactions) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for (Transaction transaction : transactions ) {
            TransactionDto dto = modelMapper.map(transaction, TransactionDto.class);
            dto.setCategory(Categories.getCategoryById(transaction.getCategoryId()));
            transactionDtos.add(dto);
        }
        return transactionDtos;
    }

}
