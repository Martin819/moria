package moria.bankingApiClient;

import moria.model.transactions.Transaction;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BankingAPIInterface {
    @GET("transaction/findByDate")
    Call<List<Transaction>> findTransactionsByDate(@Query("accountId") String accountId, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);
    // dateFrom and dateTo should be formatted as follows: 'yyyy-mm-dd' or 'yyyy-mm-dd hh:mm:ss'

    @POST("transaction/create")
    Call<Transaction> createTransaction(@Query("accountId") String accountId, @Body Transaction transaction, @Header("Content-Type") String contentType);
}
