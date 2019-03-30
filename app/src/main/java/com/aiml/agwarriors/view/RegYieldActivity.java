package com.aiml.agwarriors.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aiml.agwarriors.IActivity;
import com.aiml.agwarriors.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class RegYieldActivity extends BaseActivity implements IActivity, View.OnClickListener {
    private int AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView mTextview_regyield_place_to_sell_value;
    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_regyield);
        init();
        initView();
    }

    @Override
    public void init() {
        initHeader();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDgNfUmVAi0PTVWEpqIGrMBoP-RGFWNjcA");
        }
    }

    private void initHeader() {
        getSupportActionBar().setTitle("Register Yield");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(android.R.drawable.star_on);
    }

    @Override
    public void initView() {
        mTextview_regyield_place_to_sell_value = findViewById(R.id.textview_regyield_place_to_sell_value);
        mTextview_regyield_place_to_sell_value.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textview_regyield_place_to_sell_value:
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(this);

                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                break;
        }
    }

    //Rate limit is 100 requests per second (QPS).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("map", "Place: " + place.getName() + ", " + place.getId());
                mTextview_regyield_place_to_sell_value.setText(place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("map", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
