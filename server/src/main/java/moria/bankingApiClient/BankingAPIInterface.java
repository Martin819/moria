package moria.bankingApiClient;

import moria.model.transactions.Transaction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface BankingAPIInterface {
    @GET("findByDate")
    Call<List<Transaction>> findTransactionsByDate();

    @POST("create")
    Call<Transaction> createTransaction(@Body Transaction transaction);
}
