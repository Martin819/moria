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
            dto.setCategoryId(transaction.getCategoryId());
            if (transaction.getParentId() == null) {
                String partyDescription = determinePartyDescription(transaction);
                dto.setPartyDescription(partyDescription);
            }
            transactionDtos.add(dto);
        }
        return transactionDtos;
    }

    public String determinePartyDescription(Transaction t) {
        String result = "Unknown";
        if (t.getPartyDescription() != null && !t.getPartyDescription().trim().equals("")) {
            result = t.getPartyDescription();
        } else {
            if (t.getTransactionType().equals("CARD") || t.getTransactionType().equals("CASH")) { // protoze vyber hotovosti je v podstate taky karetni transakce
                if (t.getAdditionalInfoCard().getMerchantName() != null && !t.getAdditionalInfoCard().getMerchantName().trim().equals("")) {
                    result = t.getAdditionalInfoCard().getMerchantName();
                }
            } else {
                if (t.getUserDescription() != null && !t.getUserDescription().trim().equals("")) {
                    result = t.getUserDescription();
                } else {
                    if (t.getPartyAccount().getAccountNumber() != null && !t.getPartyAccount().getAccountNumber().trim().equals("")) {
                        result = utils.getNormalizedAccountNumber(t.getPartyAccount());
                    }
                }
            }
        }
        return result;
    }

}
