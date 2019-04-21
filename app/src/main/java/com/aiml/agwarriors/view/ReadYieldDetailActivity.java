package com.aiml.agwarriors.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.Utils;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

public class ReadYieldDetailActivity extends BaseActivity implements IActivity {
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView mTextview_regyield_place_to_sell_value;
    private EditText mEdittext_regyield_duration;
    private ImageView mImageview_regyield_cal;
    private Button mBtn_broadcast;
    private YieldListModel model;
    private EditText mEdittext_regyield_crop;
    private EditText mQty;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_regyield);
        init();
        initView();
    }

    @Override
    public void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            model = (YieldListModel) bundle.get(Constant.KEY_SEND_BROADCAST);
        }

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDgNfUmVAi0PTVWEpqIGrMBoP-RGFWNjcA");
        }
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("Yield Details");
        if (model != null) {
            if (model.getFrom() == YieldListModel.FROM_NOTIFICATION) {
                switch (model.getStatusValue()) {
                    case YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER:
                        title.setText("Notification Yield Details");
                        break;
                    default:
                        title.setText("Notification Yield Details");
                        break;
                }
            }
        }
    }

    @Override
    public void initView() {
        initHeader();
        mEdittext_regyield_crop = (EditText) findViewById(R.id.edittext_regyield_crop);
        mEdittext_regyield_crop.setEnabled(false);
        Button button_regyield_smartanalysis = (Button) findViewById(R.id.button_regyield_smartanalysis);
        button_regyield_smartanalysis.setVisibility(View.GONE);
        Spinner spinner_regyield_crop_type = (Spinner) findViewById(R.id.spinner_regyield_crop_type);
        spinner_regyield_crop_type.setEnabled(false);
        mQty = (EditText) findViewById(R.id.edittext_regyield_yieldin);
        mQty.setEnabled(false);
        Spinner spinner_regyield_unit = (Spinner) findViewById(R.id.spinner_regyield_unit);
        spinner_regyield_unit.setEnabled(false);
        mEdittext_regyield_duration = (EditText) findViewById(R.id.edittext_regyield_duration);
        mEdittext_regyield_duration.setEnabled(false);
        mImageview_regyield_cal = (ImageView) findViewById(R.id.imageview_regyield_cal);
        mImageview_regyield_cal.setVisibility(View.GONE);
        TextView textview_regyield_lot_no_value = (TextView) findViewById(R.id.textview_regyield_lot_no_value);
        Button button_regyield_Analysis = (Button) findViewById(R.id.button_regyield_Analysis);
        button_regyield_Analysis.setVisibility(View.GONE);
        mTextview_regyield_place_to_sell_value = findViewById(R.id.textview_regyield_place_to_sell_value);
        EditText edittext_regyield_cost = (EditText) findViewById(R.id.edittext_regyield_cost);
        edittext_regyield_cost.setEnabled(false);
        Spinner spinner_regyield_cost = (Spinner) findViewById(R.id.spinner_regyield_cost);
        spinner_regyield_cost.setEnabled(false);
        mBtn_broadcast = (Button) findViewById(R.id.button_regyield_freeze);
        mBtn_broadcast.setVisibility(View.GONE);
        Button dismiss = (Button) findViewById(R.id.button_regyield_dismiss);
        dismiss.setOnClickListener(this);
        LinearLayout lin_holder_btn_accept_reject = (LinearLayout) findViewById(R.id.lin_holder_btn_accept_reject);
        Button button_regyield_accept = (Button) findViewById(R.id.button_regyield_accept);
        Button button_regyield_reject = (Button) findViewById(R.id.button_regyield_reject);
        button_regyield_accept.setOnClickListener(this);
        button_regyield_reject.setOnClickListener(this);
        dismiss.setVisibility(View.VISIBLE);
        lin_holder_btn_accept_reject.setVisibility(View.GONE);
        LinearLayout lin_regyield_bid_cost = (LinearLayout) findViewById(R.id.lin_regyield_bid_cost);
        EditText edittext_regyield_bid_cost = (EditText)findViewById(R.id.edittext_regyield_bid_cost);
        if (model != null) {
            mEdittext_regyield_crop.setText(model.getYield());
            spinner_regyield_crop_type.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.crop_type), model.getYieldType()));
            mQty.setText(model.getQTY());
            spinner_regyield_unit.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.unit), model.getQTYType()));
            mEdittext_regyield_duration.setText(model.getDate());
            textview_regyield_lot_no_value.setText(model.getLotnumber());
            mTextview_regyield_place_to_sell_value.setText(model.getPlaceToSell());
            edittext_regyield_cost.setText(model.getCostPerUnit());
            spinner_regyield_cost.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.unit), model.getCostUnit()));

            if (model.getFrom() == YieldListModel.FROM_NOTIFICATION) {
                dismiss.setVisibility(View.GONE);
                lin_holder_btn_accept_reject.setVisibility(View.VISIBLE);
                switch (model.getStatusValue()){
                    case YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER:
                        lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                        edittext_regyield_bid_cost.setEnabled(true);
                        break;
                    case YieldListModel.STATUS_DISMISS_BY_SELLER:
                    case YieldListModel.STATUS_NOTIFY_TO_SELLER:
                        lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                        edittext_regyield_bid_cost.setEnabled(false);
                        break;
                    case YieldListModel.STATUS_ACCEPT_PROPOSAL:
                    case YieldListModel.STATUS_REJECT_PROPOSAL:
                        lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                        edittext_regyield_bid_cost.setEnabled(false);
                        break;
                    case YieldListModel.STATUS_DISMISS_BY_BUYER:
                        lin_regyield_bid_cost.setVisibility(View.GONE);
                        //status update as dismissed by broadcaster
                        break;
                }
            }
        }


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
            case R.id.button_regyield_reject:
                //Remove from DB
                if (Constant.mList.size() > 0) {
                    for (YieldListModel m : Constant.mList) {
                        if (m.getLotnumber().equalsIgnoreCase(model.getLotnumber())) {
                            //Constant.mList.remove(m);
                            Toast.makeText(ReadYieldDetailActivity.this, "Successfully rejected", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                finish();
                break;
            case R.id.button_regyield_accept:
                Toast.makeText(ReadYieldDetailActivity.this, "Accepted", Toast.LENGTH_LONG).show();
                break;
            case R.id.button_regyield_dismiss:
                Toast.makeText(ReadYieldDetailActivity.this, "Successfully dismissed", Toast.LENGTH_LONG).show();
                //Remove from DB
                if (Constant.mList.size() > 0) {
                    for (YieldListModel m : Constant.mList) {
                        if (m.getLotnumber().equalsIgnoreCase(model.getLotnumber())) {
                            Constant.mList.remove(m);
                        }
                    }
                }
                finish();
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
