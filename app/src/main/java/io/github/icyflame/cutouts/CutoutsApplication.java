package io.github.icyflame.cutouts;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddharth on 8/8/16.
 */
public class CutoutsApplication extends Application {

    public static final String TAG = "cutoutsApplication";
    private Retrofit mRetrofitInstance;
    private CutoutsAPI mApi;
    private String mSid;
    private boolean mLoggedIn;
    private OkHttpClient mOkHttpClient;

    public void setSid(String sid) {
        mSid = sid;
        mLoggedIn = true;
    }

    public void logoutUser() {
        mSid = "";
        mLoggedIn = false;
    }

    public Retrofit getmRetrofitInstance() {
        return mRetrofitInstance;
    }

    public CutoutsAPI getmApi() {
        return mApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofitInstance = new Retrofit.Builder()
                .baseUrl("https://cutouts.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofitInstance.create(CutoutsAPI.class);

        Log.d(TAG, "onCreate: API and Retrofit Instances created");
    }
}
