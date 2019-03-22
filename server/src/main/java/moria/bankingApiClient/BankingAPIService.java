package moria.bankingApiClient;

import moria.model.transactions.Transaction;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class BankingAPIService implements BankingAPIConfiguration {

    private BankingAPIInterface service;

    public BankingAPIService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL + API_VERSION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(BankingAPIInterface.class);
    }

     public List<Transaction> findTransactionsByDate(String dateFrom, String dateTo) throws IOException {

        Call<List<Transaction>> retrofitCall = service.findTransactionsByDate(ACCOUNT_ID, dateFrom, dateTo);
        Response<List<Transaction>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
     }

     public Transaction createTransaction(Transaction t) throws IOException {

        Call<Transaction> retrofitCall = service.createTransaction(ACCOUNT_ID, t, CONTENT_TYPE);
        Response<Transaction> response = retrofitCall.execute();

         if (!response.isSuccessful()) {
             throw new IOException(response.errorBody() != null ? response.errorBody().string() : "Unknown error");
         }

         return response.body();
     }

}
