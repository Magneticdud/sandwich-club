package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json==null) {
            //if no data, don't try to parse it, and return null
            return null;
            //in mainactivity, another check for null will alert the user for this problem
        }
        //if we're here, that means the string is not empty, so it might contain a json
        try {

        }
        catch (JSONException e){
            //we got some big error, don't crash the whole app
            //return null so the user has a nice error saying the problem with the
            //sandwich data
            return null;
        }

        return null;
    }
}
