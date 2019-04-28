package com.aiml.agwarriors.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
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

public class RegisterYieldActivity extends BaseActivity implements IActivity, AdapterView.OnItemSelectedListener {
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private final int MAX_DURATION = 31;
    private TextView mTextview_regyield_place_to_sell_value;
    private List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
    private EditText mEdittext_regyield_duration;
    private ImageView mImageview_regyield_cal;
    private Calendar mCalendear;
    private DatePickerDialog.OnDateSetListener mDate;
    private Button mBtn_broadcast;
    private YieldListModel mModel;
    private EditText mEdittext_regyield_crop;
    private Spinner mSpinner_regyield_crop_type;
    private EditText mQty;
    private Spinner mSpinner_regyield_unit;
    private TextView mTextview_regyield_lot_no_value;
    private Button mButton_regyield_Analysis;
    private EditText mEdittext_regyield_cost;
    private Spinner mSpinner_regyield_cost;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_regyield);
        init();
        initView();
    }

    @Override
    public void init() {
        mModel = new YieldListModel();
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
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        ImageView notification = (ImageView) findViewById(R.id.imageview_header_notification);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("Register Yield");
    }

    @Override
    public void initView() {
        initHeader();
        mEdittext_regyield_crop = (EditText) findViewById(R.id.edittext_regyield_crop);
        Button button_regyield_smartanalysis = (Button) findViewById(R.id.button_regyield_smartanalysis);
        button_regyield_smartanalysis.setOnClickListener(this);
        mSpinner_regyield_crop_type = (Spinner) findViewById(R.id.spinner_regyield_crop_type);
        mQty = (EditText) findViewById(R.id.edittext_regyield_yieldin);
        mSpinner_regyield_unit = (Spinner) findViewById(R.id.spinner_regyield_unit);
        mEdittext_regyield_duration = (EditText) findViewById(R.id.edittext_regyield_duration);
        mImageview_regyield_cal = (ImageView) findViewById(R.id.imageview_regyield_cal);
        mImageview_regyield_cal.setOnClickListener(this);
        mTextview_regyield_lot_no_value = (TextView) findViewById(R.id.textview_regyield_lot_no_value);
        mButton_regyield_Analysis = (Button) findViewById(R.id.button_regyield_Analysis);
        mButton_regyield_Analysis.setOnClickListener(this);
        mTextview_regyield_place_to_sell_value = findViewById(R.id.textview_regyield_place_to_sell_value);
        mTextview_regyield_place_to_sell_value.setOnClickListener(this);
        mEdittext_regyield_cost = (EditText) findViewById(R.id.edittext_regyield_cost);
        mSpinner_regyield_cost = (Spinner) findViewById(R.id.spinner_regyield_cost);
        mBtn_broadcast = (Button) findViewById(R.id.button_regyield_freeze);
        mBtn_broadcast.setOnClickListener(this);

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
                    Toast.makeText(RegisterYieldActivity.this, "Duration will not be more than 31 days", Toast.LENGTH_LONG).show();
                    editable.clear();
                }*/
            }
        });

        mSpinner_regyield_crop_type.setOnItemSelectedListener(this);
        mSpinner_regyield_unit.setOnItemSelectedListener(this);
        mSpinner_regyield_cost.setOnItemSelectedListener(this);
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
        super.onClick(view);
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
            case R.id.button_regyield_freeze:
                Toast.makeText(RegisterYieldActivity.this, "Successfully sent broadcast to buyers ",Toast.LENGTH_LONG).show();
                addDataIntoList();
                finish();
                break;
            case R.id.button_regyield_smartanalysis:
                Toast.makeText(RegisterYieldActivity.this, "Coming soon",Toast.LENGTH_LONG).show();
                break;
            case R.id.button_regyield_Analysis:
                Toast.makeText(RegisterYieldActivity.this, "Coming soon",Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void addDataIntoList() {
        mModel.setBidCostPerUnit(null);
        mModel.setUserID(Constant.USER_INFO_LIST.get(0).getID());
        mModel.setMessageTo("BUYER_B123");
        mModel.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
        mModel.setYield(mEdittext_regyield_crop.getText().toString());
        mModel.setYieldType(mSpinner_regyield_crop_type.getSelectedItem().toString());
        mModel.setDate(mEdittext_regyield_duration.getText().toString());
        mModel.setQTY(mQty.getText().toString());
        mModel.setQTYType(mSpinner_regyield_unit.getSelectedItem().toString());
        mModel.setCostPerUnit(mEdittext_regyield_cost.getText().toString());
        mModel.setCostUnit(mSpinner_regyield_cost.getSelectedItem().toString());
        mModel.setPlaceToSell(mTextview_regyield_place_to_sell_value.getText().toString());
        mModel.setStatus("Sent Broadcast");
        mModel.setStatusValue(YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER);
        mTextview_regyield_lot_no_value.setText("LOT"+"_"+mModel.getYield()+"_"+mModel.getYieldType()+"_"+System.currentTimeMillis());
        mModel.setLotnumber(mTextview_regyield_lot_no_value.getText().toString().replace("/",""));
        saveToYieldDB(mModel);
    }

    private void saveToYieldDB(YieldListModel pModel) {
        long row = 0;
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            row = table.insert(pModel);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("RegisterYieldActivity", "saveToYieldDB: " + row + e.getMessage());
        }
    }

    private void showCalender() {
        new DatePickerDialog(RegisterYieldActivity.this, mDate, mCalendear
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         switch (adapterView.getId()){
             case R.id.spinner_regyield_crop_type:
                 break;
             case R.id.spinner_regyield_unit:
                 if(mSpinner_regyield_cost.getSelectedItemPosition() != i) {
                     mSpinner_regyield_cost.setSelection(i);
                 }
                 break;
             case R.id.spinner_regyield_cost:
                 if(mSpinner_regyield_unit.getSelectedItemPosition() != i) {
                     mSpinner_regyield_unit.setSelection(i);
                 }
                 break;
         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
