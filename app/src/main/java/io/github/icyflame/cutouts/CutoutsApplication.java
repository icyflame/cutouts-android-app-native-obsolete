package io.github.icyflame.cutouts;

import android.app.Application;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddharth on 8/8/16.
 */
public class CutoutsApplication extends Application {


    private Retrofit mRetrofitInstance;
    private CutoutsAPI mApi;
    private String mSid;
    private boolean mLoggedIn;

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

    public void setmRetrofitInstance(Retrofit mRetrofitInstance) {
        this.mRetrofitInstance = mRetrofitInstance;
    }

    public CutoutsAPI getmApi() {
        return mApi;
    }

    public void setmApi(CutoutsAPI mApi) {
        this.mApi = mApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofitInstance = new Retrofit.Builder()
                .baseUrl("https://cutouts/herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApi = mRetrofitInstance.create(CutoutsAPI.class);
    }
}
