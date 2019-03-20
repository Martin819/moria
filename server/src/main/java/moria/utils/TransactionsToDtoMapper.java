package moria.utils;

import moria.dto.TransactionDto;
import moria.model.transactions.Transaction;
import moria.model.transactions.TransactionPartyAccount;
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
            String partyDescription = determinePartyDescription(transaction);
            dto.setPartyDescription(partyDescription);
            transactionDtos.add(dto);
        }
        return transactionDtos;
    }

    public String determinePartyDescription(Transaction t) {
        String result = "Unknown";
        if (t.getPartyDescription() != null && !t.getPartyDescription().trim().equals("")) {
            result = t.getPartyDescription();
        } else {
            if (t.getTransactionType().equals("CARD")) {
                result = t.getAdditionalInfoCard().getMerchantName();
            } else {
                if (t.getUserDescription() != null && !t.getUserDescription().trim().equals("")) {
                    result = t.getUserDescription();
                } else {
                    result = getNormalizedAccountNumber(t.getPartyAccount());
                }
            }
        }
        return result;
    }

    public String getNormalizedAccountNumber(TransactionPartyAccount a) {
        String ap = a.getPrefix().replaceFirst("^0+(?!$)", "");
        String an = a.getAccountNumber().replaceFirst("^0+(?!$)", "");
        String abc = a.getBankCode().replaceFirst("^0+(?!$)", "");
        String result = ap + "-" + an + "/" + abc;
        return result;
    }

    public String getNormalizedAccountNumber (String prefix, String number, String bankCode) {
        return getNormalizedAccountNumber(new TransactionPartyAccount(prefix, number, bankCode));
    }

}
