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
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.Utils;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

import java.util.ArrayList;

import static com.aiml.agwarriors.model.YieldListModel.FROM_HISTORY;
import static com.aiml.agwarriors.model.YieldListModel.FROM_NOTIFICATION;
import static com.aiml.agwarriors.model.YieldListModel.FROM_PROPOSAL;
import static com.aiml.agwarriors.model.YieldListModel.FROM_REG_YIELD;

public class ReadYieldDetailActivity extends BaseActivity implements IActivity {
    private final int AUTOCOMPLETE_REQUEST_CODE = 1;
    private TextView mTextview_regyield_place_to_sell_value;
    private EditText mEdittext_regyield_duration;
    private ImageView mImageview_regyield_cal;
    private Button mBtn_broadcast;
    private YieldListModel model;
    private EditText mEdittext_regyield_crop;
    private EditText mQty;
    private EditText mEdittext_regyield_bid_cost;
    private Button mButton_regyield_accept;
    private Button mButton_regyield_reject;
    //private ArrayList<YieldListModel> mFarmerInfo;

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

        //mFarmerInfo = getFarmerUserID(model);

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
            switch (model.getFrom()) {
                case FROM_PROPOSAL:
                    title.setText("Proposal Details");
                    break;
                case FROM_HISTORY:
                    title.setText("History Details");
                    break;
                case FROM_NOTIFICATION:
                    title.setText("Notification Details");
                    break;
                case FROM_REG_YIELD:
                    title.setText("Yield Registration Details");
                    break;
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
        mButton_regyield_accept = (Button) findViewById(R.id.button_regyield_accept);
        mButton_regyield_reject = (Button) findViewById(R.id.button_regyield_reject);
        mButton_regyield_accept.setOnClickListener(this);
        mButton_regyield_reject.setOnClickListener(this);
        dismiss.setVisibility(View.VISIBLE);
        lin_holder_btn_accept_reject.setVisibility(View.GONE);
        LinearLayout lin_regyield_bid_cost = (LinearLayout) findViewById(R.id.lin_regyield_bid_cost);
        mEdittext_regyield_bid_cost = (EditText) findViewById(R.id.edittext_regyield_bid_cost);

        if (model != null) {
            mEdittext_regyield_bid_cost.setText(model.getBidCostPerUnit());
            mEdittext_regyield_crop.setText(model.getYield());
            spinner_regyield_crop_type.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.crop_type), model.getYieldType()));
            mQty.setText(model.getQTY());
            spinner_regyield_unit.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.unit), model.getQTYType()));
            mEdittext_regyield_duration.setText(model.getDate());
            textview_regyield_lot_no_value.setText(model.getLotnumber());
            mTextview_regyield_place_to_sell_value.setText(model.getPlaceToSell());
            edittext_regyield_cost.setText(model.getCostPerUnit());
            spinner_regyield_cost.setSelection(Utils.getSpinnerPosition(getResources().getStringArray(R.array.unit), model.getCostUnit()));

            switch (model.getFrom()) {
                case FROM_PROPOSAL:
                    dismiss.setVisibility(View.GONE);
                    mButton_regyield_accept.setText("Notify");
                    mButton_regyield_reject.setText("Reject");
                    lin_holder_btn_accept_reject.setVisibility(View.VISIBLE);
                    break;
                case FROM_NOTIFICATION:
                    if (Constant.USER_INFO_LIST.get(0).getTYPE().equalsIgnoreCase(TableUserInfoDataModel.TYPE_USER_BUYER)) {
                        dismiss.setVisibility(View.GONE);
                        lin_holder_btn_accept_reject.setVisibility(View.GONE);
                        lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                        mEdittext_regyield_bid_cost.setEnabled(false);
                        if (model.getBidCostPerUnit() == null) {
                            mEdittext_regyield_bid_cost.setText("Pending");
                        }
                    } else {
                        switch (model.getStatusValue()) {
                            case YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER:
                                lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                                mEdittext_regyield_bid_cost.setEnabled(false);
                                dismiss.setVisibility(View.VISIBLE);
                                lin_holder_btn_accept_reject.setVisibility(View.GONE);
                                break;
                            case YieldListModel.STATUS_DISMISS_BY_SELLER:
                                dismiss.setVisibility(View.GONE);
                                lin_regyield_bid_cost.setVisibility(View.GONE);
                                lin_holder_btn_accept_reject.setVisibility(View.GONE);
                                break;
                            case YieldListModel.STATUS_NOTIFY_TO_SELLER:
                                lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                                mEdittext_regyield_bid_cost.setEnabled(false);
                                dismiss.setVisibility(View.GONE);
                                lin_holder_btn_accept_reject.setVisibility(View.VISIBLE);
                                break;
                            case YieldListModel.STATUS_ACCEPT_PROPOSAL:
                            case YieldListModel.STATUS_REJECT_PROPOSAL:
                                mEdittext_regyield_bid_cost.setEnabled(false);
                                lin_regyield_bid_cost.setVisibility(View.VISIBLE);
                                lin_holder_btn_accept_reject.setVisibility(View.GONE);
                                break;
                            case YieldListModel.STATUS_DISMISS_BY_BUYER:
                                dismiss.setVisibility(View.GONE);
                                lin_regyield_bid_cost.setVisibility(View.GONE);
                                lin_holder_btn_accept_reject.setVisibility(View.GONE);
                                //status update as dismissed by broadcaster
                                break;
                        }
                    }
                    break;
                case FROM_REG_YIELD:
                    break;
                case FROM_HISTORY:
                    break;
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
        if (((String) Constant.USER_INFO_LIST.get(0).getID().subSequence(0, 5)).equalsIgnoreCase("Buyer")) {
            if (mEdittext_regyield_bid_cost.getText() != null) {
                model.setBidCostPerUnit(mEdittext_regyield_bid_cost.getText().toString());
            } else {
                Toast.makeText(ReadYieldDetailActivity.this, "Bid should not be empty", Toast.LENGTH_LONG).show();
                return;
            }
            switch (view.getId()) {
                case R.id.button_regyield_reject:
                    model.setStatusValue(YieldListModel.STATUS_DISMISS_BY_BUYER);
                    model.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
                    model.setStatus("Rejected By " + model.getMessageFrom());
                    model.setMessageTo(model.getUserID());
                    updateYieldDB(model);
                    Toast.makeText(ReadYieldDetailActivity.this, "Rejected Proposal By " + Constant.USER_INFO_LIST.get(0).getID(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case R.id.button_regyield_accept:
                    model.setStatusValue(YieldListModel.STATUS_NOTIFY_TO_SELLER);
                    model.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
                    model.setStatus("Notified By " + model.getMessageFrom());
                    model.setMessageTo(model.getUserID());
                    updateYieldDB(model);
                    Toast.makeText(ReadYieldDetailActivity.this, "Accepted Proposal By " + Constant.USER_INFO_LIST.get(0).getID(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        } else {
            //Former section
            model.setMessageTo("BUYER_B123");
            switch (view.getId()) {
                case R.id.button_regyield_reject:
                    model.setStatusValue(YieldListModel.STATUS_REJECT_PROPOSAL);
                    model.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
                    model.setStatus("Rejected By " + model.getMessageFrom());
                    updateYieldDB(model);
                    Toast.makeText(ReadYieldDetailActivity.this, "Rejected Proposal By " + Constant.USER_INFO_LIST.get(0).getID(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case R.id.button_regyield_accept:
                    model.setStatusValue(YieldListModel.STATUS_ACCEPT_PROPOSAL);
                    model.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
                    model.setStatus("Accepted By " + model.getMessageFrom());
                    updateYieldDB(model);
                    Toast.makeText(ReadYieldDetailActivity.this, "Accepted Proposal By " + Constant.USER_INFO_LIST.get(0).getID(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case R.id.button_regyield_dismiss:
                    model.setStatusValue(YieldListModel.STATUS_DISMISS_BY_SELLER);
                    model.setMessageFrom(Constant.USER_INFO_LIST.get(0).getID());
                    model.setStatus("Dismissed By " + model.getMessageFrom());
                    updateYieldDB(model);
                    Toast.makeText(ReadYieldDetailActivity.this, "Successfully dismissed By " + Constant.USER_INFO_LIST.get(0).getID(), Toast.LENGTH_LONG).show();
                    finish();
                    break;
            }
        }
        super.onClick(view);
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

    private void updateYieldDB(YieldListModel pModel) {
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            table.update(pModel);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("ReadYieldDetailActivity", "updateYieldDBForBuyer: " + e.getMessage());
        }
    }


    private ArrayList<YieldListModel> getFarmerUserID(YieldListModel pModel) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getUserIDForLotAndBuyer(pModel.getLotnumber(), pModel.getUserID());
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("NotificationActivity", "getFarmerUserID: " + e.getMessage());
        } finally {
            return list;
        }
    }

}