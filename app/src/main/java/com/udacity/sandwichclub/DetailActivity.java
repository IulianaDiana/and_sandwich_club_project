package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mActivityBinding;

    Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        Toast.makeText(DetailActivity.this, sandwich.getMainName(), Toast.LENGTH_SHORT).show();
        mActivityBinding.setSandwich(sandwich);
        mActivityBinding.executePendingBindings();
        String akaString;
        if (sandwich.getAlsoKnownAs() == null || sandwich.getAlsoKnownAs().isEmpty()) {
            akaString = "Its name is the only way it is known";
        } else {
            StringBuilder akaStringBuilder = new StringBuilder();
            for (String aka : sandwich.getAlsoKnownAs()) {
                String akaa = aka + ", ";
                akaStringBuilder.append(akaa);
            }
            akaString = akaStringBuilder.toString().replaceAll(", $", "");
        }
        mActivityBinding.alsoKnownTv.setText(akaString);

        String ingredientsString;
        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (String ingredient : sandwich.getIngredients()) {
            String ingredienti = ingredient + ", ";
            (ingredientsStringBuilder).append(ingredienti);
        }
        ingredientsString = ingredientsStringBuilder.toString().replaceAll(", $", "");
        mActivityBinding.ingredientsTv.setText(ingredientsString);

        String placeOfOrigin;
        if (sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOrigin = "Unknown";
        } else {
            placeOfOrigin = sandwich.getPlaceOfOrigin();
        }
        mActivityBinding.originTv.setText(placeOfOrigin);
    }
}
