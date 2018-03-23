package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError(getResources().getString(R.string.detail_error_null));
        }

        //get position from extra
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        //if EXTRA_POSITION wasn't there, it returned DEFAULT_POSITION
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError(getResources().getString(R.string.detail_error_position));
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError(getResources().getString(R.string.detail_error_message));
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError(String errorDetail) {
        finish();
        Toast.makeText(this, getResources().getString(R.string.error)+"\n"+errorDetail, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //find the views
        TextView tVOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView tVLabelOrigin = (TextView) findViewById(R.id.label_origin);
        TextView tVLabelAlsoKnown = (TextView) findViewById(R.id.label_also_known);
        TextView tVAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView tVIngredients = (TextView) findViewById(R.id.ingredients_tv);
        TextView tVDescription = (TextView) findViewById(R.id.description_tv);

        //populate the fields
        tVDescription.setText(sandwich.getDescription());

        //check size of also known as
        if(sandwich.getAlsoKnownAs().size()!=0) {
            //not empty
            tVAlsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }
        else {
            //empty, so hide the view
            tVAlsoKnownAs.setVisibility(View.GONE);
            tVLabelAlsoKnown.setVisibility(View.GONE);
        }

        //check place of origin
        String origin = sandwich.getPlaceOfOrigin();
        if (origin.length()==0) {
            //no origin, so hide it
            tVOrigin.setVisibility(View.GONE);
            tVLabelOrigin.setVisibility(View.GONE);
        }
        else {
            tVOrigin.setText(origin);
        }

        if(sandwich.getIngredients().size()!=0) {
            tVIngredients.setText(TextUtils.join(", ", sandwich.getIngredients()));
        }
    }
}
