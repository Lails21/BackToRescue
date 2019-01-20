package dsa.eetac.upc.edu.backtorescueapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIRest {
    String BASE_URL = "http://147.83.7.204:8080/dsaApp/";

    @POST("authentication/login")
    Call<Character> getUser(@Body User user);

    @POST("authentication/signin")
    Call <User> createNewUser (@Body User user);

    @GET("scoreboard/scoreboard")
    Call<List<Character>> getScoreboard();

    @GET("states/statsbyusername/{username}")
    Call<Character> getUserStats(@Path("username") String username);

    @PUT()
    Call <Void> logout();



    static APIRest createAPIRest() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(APIRest.class);
    }
}
