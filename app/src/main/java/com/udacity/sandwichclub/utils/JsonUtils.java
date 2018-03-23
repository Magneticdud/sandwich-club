package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //from the json
    static final String NAME = "name";
    static final String MAIN_NAME = "mainName";
    static final String ALSO_KNOWN_AS = "alsoKnownAs";
    static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    static final String DESCRIPTION = "description";
    static final String IMAGE = "image";
    static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        if (json==null) {
            //if no data, don't try to parse it, and return null
            return null;
            //in mainactivity, another check for null will alert the user for this problem
        }
        //if we're here, that means the string is not empty, so it might contain a json
        try {
            JSONObject sandwichJson = new JSONObject(json);
            //create new sandwich object
            Sandwich sandwich = new Sandwich();
            //name
            JSONObject sandwichNameJson = sandwichJson.getJSONObject(NAME);
            sandwich.setMainName(sandwichNameJson.getString(MAIN_NAME));

            //also known
            JSONArray alsoKnownAs = sandwichNameJson.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownNames = new ArrayList<>(alsoKnownAs.length());
            for(int i=0; i< alsoKnownAs.length(); i++){
                alsoKnownNames.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(alsoKnownNames);

            //description
            sandwich.setDescription(sandwichJson.getString(DESCRIPTION));
            //image
            sandwich.setImage(sandwichJson.getString(IMAGE));
            //origin
            sandwich.setPlaceOfOrigin(sandwichJson.getString(PLACE_OF_ORIGIN));

            //ingredients array
            JSONArray ingredientsJson = sandwichJson.getJSONArray(INGREDIENTS);
            List<String> ingredients = new ArrayList<>(ingredientsJson.length());
            for(int i=0; i< ingredientsJson.length(); i++){
                ingredients.add(ingredientsJson.getString(i));
            }
            sandwich.setIngredients(ingredients);
            
            return sandwich;
        }
        catch (JSONException e){
            //we got some big error, don't crash the whole app
            //return null so the user has a nice error saying the problem with the
            //sandwich data
            return null;
        }
        //here will never be reached
        //return null;
    }
}
