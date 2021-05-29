import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Converter.Factory;

public class RetrofitConfig {
    public static <T> T createClient(String apiUrl, Factory factory, Class<T> clientClass){
        return new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(apiUrl)
                .addConverterFactory(factory)
                .build()
                .create(clientClass);
    }

    @SneakyThrows
    public static <T> T execute (Call <T> call){
        Response<T> response = call.execute();
        if (response.isSuccessful()) return response.body();
        else {
            String errorMessage = "HTTP code: " + response.code() + " -> " + response.errorBody().string();
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

}
