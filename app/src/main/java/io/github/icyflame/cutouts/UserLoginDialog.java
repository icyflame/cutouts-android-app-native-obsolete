package io.github.icyflame.cutouts;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserLoginDialog extends DialogFragmentCustom {

    public static final String TAG = "UserLoginDialogFragment";

    public interface userLoginResults {
        void userLoginDone(boolean result, JsonObject authResponse, JsonArray articlesList);
    }

    public userLoginResults mCallback;

    public UserLoginDialog() {
        // Required empty public constructor
    }

    public static UserLoginDialog newInstance() {
        UserLoginDialog fragment = new UserLoginDialog();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (getParentFragment() == null) {
                mCallback = ((userLoginResults) getActivity());
            } else {
                mCallback = ((userLoginResults) getParentFragment());
            }
        } catch (ClassCastException error) {
            Log.e(TAG, "onCreate: "
                    + (getParentFragment() == null ? getActivity().toString() : getParentFragment().toString())
                    + " must implement the userLoginResults interface.", error);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View main = inflater.inflate(R.layout.fragment_user_login_dialog, container, false);

        main.findViewById(R.id.user_login_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Starting user Login!");
                String username = ((EditText) main.findViewById(R.id.user_login_dialog_username)).getText().toString();
                String password = ((EditText) main.findViewById(R.id.user_login_dialog_password)).getText().toString();

                Log.d(TAG, "onClick: " + String.format(Locale.US, "Username: %s, Password: %s", username, password));

                final CutoutsApplication appInstance = (CutoutsApplication) UserLoginDialog.this
                        .getActivity()
                        .getApplication();

                appInstance.getmApi().userSignin(username, password).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        Log.d(TAG, "onResponse: Raw: " + (response.raw() == null ? "NOTHING!" : response.raw().toString()));

                        if (response.code() != 200 || response.body() == null) {
                            Log.e(TAG, "onResponse: Response didn't end in 200 or had a null response body!");
                            return;
                        }

                        Log.d(TAG, "onResponse: User signed in: " + response.body());

                        final JsonObject authResponse = response.body();

                        if (response.body().has("res")) {
                            String _sid = response.body().get("res").getAsJsonObject().get("sid").getAsString();

                            appInstance.setSid(_sid);

                            appInstance.getmApi().articlesList(_sid).enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Log.d(TAG, "onResponse: List of articles response: " + response.body().toString());
                                    if (response.body().has("res")) {
                                        final JsonArray articlesList = response.body()
                                                .getAsJsonArray("res");
                                        int numArticles = articlesList.size();
                                        Toast.makeText(UserLoginDialog.this.getActivity(), "Logged in! Number of articles for this user: " + numArticles, Toast.LENGTH_LONG).show();

                                        UserLoginDialog.this.dismiss();

                                        mCallback.userLoginDone(true, authResponse, articlesList);

                                    } else {
                                        Toast.makeText(UserLoginDialog.this.getActivity(), "There was some problem!", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d(TAG, "onFailure: API Response for List articles failed!");
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: API Response for User login failed!");
                    }
                });
            }
        });

        return main;
    }
}
