package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichJsonObject = new JSONObject(json);

        JSONObject name = sandwichJsonObject.getJSONObject("name");
        String mainName = name.getString("mainName");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAsStringArray = new ArrayList<>();
        for (int index = 0; index < alsoKnownAs.length(); index++) {
            alsoKnownAsStringArray.add((String) alsoKnownAs.get(index));
        }

        String placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");

        String description = sandwichJsonObject.getString("description");

        String image = sandwichJsonObject.getString("image");


        JSONArray ingredients = sandwichJsonObject.getJSONArray("ingredients");
        List<String> ingredientsStringArray = new ArrayList<>();
        for (int index = 0; index < ingredients.length(); index++) {
            ingredientsStringArray.add((String) ingredients.get(index));
        }
        return new Sandwich(mainName, alsoKnownAsStringArray, placeOfOrigin, description, image, ingredientsStringArray);
    }
}
