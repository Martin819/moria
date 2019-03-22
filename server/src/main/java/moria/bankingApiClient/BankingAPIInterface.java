package moria.bankingApiClient;

import moria.model.transactions.Transaction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface BankingAPIInterface {
    @GET("findByDate")
    Call<List<Transaction>> findTransactionsByDate(@Query("accountId") int accountId, @Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);
    // dateFrom and dateTo should be formatted as follows: 'yyyy-mm-dd' or 'yyyy-mm-dd hh:mm:ss'

    @POST("create")
    Call<Transaction> createTransaction(@Query("accountId") int accountId, @Body Transaction transaction);
}
