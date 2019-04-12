package com.aiml.agwarriors.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.interfaces.IActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegYieldActivity extends BaseActivity implements IActivity, View.OnClickListener {
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private final int MAX_DURATION = 31;
    private TextView mTextview_regyield_place_to_sell_value;
    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
    private EditText mEdittext_regyield_duration;
    private ImageView mImageview_regyield_cal;
    private Calendar mCalendear;
    private DatePickerDialog.OnDateSetListener mDate;

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
        initCalendar();
        mCalendear = Calendar.getInstance();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDgNfUmVAi0PTVWEpqIGrMBoP-RGFWNjcA");
        }
    }

    private void initCalendar() {
        mDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                mCalendear.set(Calendar.YEAR, year);
                mCalendear.set(Calendar.MONTH, monthOfYear);
                mCalendear.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mEdittext_regyield_duration.setText(sdf.format(mCalendear.getTime()));
    }

    private void initHeader() {
        getSupportActionBar().setTitle("Register Yield");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(android.R.drawable.star_on);
    }

    @Override
    public void initView() {
        mImageview_regyield_cal = (ImageView) findViewById(R.id.imageview_regyield_cal);
        mImageview_regyield_cal.setOnClickListener(this);
        mTextview_regyield_place_to_sell_value = findViewById(R.id.textview_regyield_place_to_sell_value);
        mTextview_regyield_place_to_sell_value.setOnClickListener(this);

        mEdittext_regyield_duration = (EditText) findViewById(R.id.edittext_regyield_duration);
        mEdittext_regyield_duration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*if (Integer.parseInt((editable.toString().isEmpty() ? "0" : editable.toString())) > MAX_DURATION) {
                    Toast.makeText(RegYieldActivity.this, "Duration will not be more than 31 days", Toast.LENGTH_LONG).show();
                    editable.clear();
                }*/
            }
        });
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
            case R.id.imageview_regyield_cal:
                showCalender();
                break;
        }
    }

    private void showCalender() {
        new DatePickerDialog(RegYieldActivity.this, mDate, mCalendear
                .get(Calendar.YEAR), mCalendear.get(Calendar.MONTH),
                mCalendear.get(Calendar.DAY_OF_MONTH)).show();
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
