package io.github.icyflame.cutouts;

import com.google.gson.JsonObject;

/**
 * Created by siddharth on 10/8/16.
 */
public class ApiHelper {
    public static JsonObject getUserSignInObject(String username, String password) {
        JsonObject temp = new JsonObject();
        temp.addProperty("auth_data", username);
        temp.addProperty("auth_password", password);
        return temp;
    }
}
