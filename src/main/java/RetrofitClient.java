import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface RetrofitClient {
    @POST("users")
    @Headers({"ContentType-Type: application/json"})
    Call<List<User>> addObject (@Body User user);

    @PUT("users/{id}")
    @Headers({"ContentType-Type: application/json"})
    Call<List<User>> updateUser(@Body User user, @Path("id") int id);

    @GET("users")
    @Headers({"ContentType-Type: application/json"})
    Call<List<User>> getUser();

    @DELETE("users/{id}")
    @Headers({"ContentType-Type: application/json"})
    Call<List<User>> deleteUser(@Path("id") int id);
}
