package io.github.icyflame.cutouts;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by siddharth on 8/8/16.
 */
public interface CutoutsAPI {

    @FormUrlEncoded
    @POST("users/auth")
    Call<JsonObject> userSignin(@Field("auth_data") String username, @Field("auth_password") String password);

    @GET("articles")
    Call<JsonObject> articlesList(@Path("sid") String sid);
}
