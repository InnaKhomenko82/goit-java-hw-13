import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface RetrofitClient {
    @POST("users")
    @Headers({"ContentType-Type: application/json"})
    Call<User> addObject (@Body User user);

    @PUT("users/{id}")
    @Headers({"ContentType-Type: application/json"})
    Call<User> updateUser(@Body User user, @Path("id") Integer id);

    @GET("users")
    @Headers({"ContentType-Type: application/json"})
    Call<List<User>> getUser();

    @DELETE("users/{id}")
    @Headers({"ContentType-Type: application/json"})
    Call<User> deleteUser(@Path("id") Integer id);

    @GET("users")
    @Headers({"Content-Type: application/json"})
    Call<List<User>> getUserByID(@Query("id") Integer id);

    @GET("users")
    @Headers({"Content-Type: application/json"})
    Call<List<User>> getUserByUserName(@Query("username") String userName);

    @GET("users/{userID}/posts")
    @Headers({"ContentType-Type: application/json"})
    Call <List<Post>> getUserPosts(@Path("userID") Integer userID);

    @GET("posts/{postID}/comments")
    @Headers({"ContentType-Type: application/json"})
    Call <List<Comment>> getComments(@Path("postID") Integer userID);

    @GET("users/{userID}/todos")
    @Headers({"ContentType-Type: application/json"})
    Call<List<ToDo>> getTodo(@Path("userID") String userID,
                             @Query("completed") boolean completed);
}
