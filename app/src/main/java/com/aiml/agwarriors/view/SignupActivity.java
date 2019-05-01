package com.aiml.agwarriors.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.database.TableUserInfo;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.utils.AppLog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SignupActivity extends BaseActivity implements IActivity {
    private Button mButton_signup_sign_up;
    private GoogleMap mMap;
    private double mLat, mLong;
    private EditText mEdititext_signup_username;
    private EditText mEdititext_signup_password;
    private EditText mEdititext_signup_confirmation_password;
    private EditText mEdititext_signup_phone_number;
    private RadioGroup mRadiogroup_signup_holder;
    private Button mButton_locate_me;
    private ImageView mImageview_map;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_signup);
        init();
        initView();
    }

    @Override
    public void init() {
//        getSupportActionBar().hide();
        setUpMapIfNeeded();
    }

    @Override
    public void initView() {
        initHeader();
        mButton_locate_me = (Button)findViewById(R.id.button_locate_me);
        mEdititext_signup_username = (EditText) findViewById(R.id.edititext_signup_username);
        mEdititext_signup_password = (EditText) findViewById(R.id.edititext_signup_password);
        mEdititext_signup_confirmation_password = (EditText) findViewById(R.id.edititext_signup_confirmation_password);
        mEdititext_signup_phone_number = (EditText) findViewById(R.id.edititext_signup_phone_number);
        mRadiogroup_signup_holder = (RadioGroup) findViewById(R.id.radiogroup_signup_holder);
        mImageview_map = (ImageView) findViewById(R.id.imageview_map);
        mImageview_map.setVisibility(View.GONE);
        mButton_signup_sign_up = findViewById(R.id.button_signup_sign_up);
        mButton_signup_sign_up.setOnClickListener(this);
        mButton_locate_me.setOnClickListener(this);
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        ImageView notification = (ImageView) findViewById(R.id.imageview_header_notification);
        title.setText("SignUp");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);

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
            case R.id.button_locate_me:
                mImageview_map.setVisibility(View.VISIBLE);
                break;
            case R.id.button_signup_sign_up:
                TableUserInfoDataModel model = new TableUserInfoDataModel();
                model.setUSERNAME(mEdititext_signup_username.getText().toString());
                model.setPASS(mEdititext_signup_password.getText().toString());
                model.setADDRESS(mEdititext_signup_phone_number.getText().toString());
                model.setMOBILENO(mEdititext_signup_phone_number.getText().toString());


                model.setTYPE(((RadioButton) findViewById(mRadiogroup_signup_holder.getCheckedRadioButtonId())).getText().toString());
                model.setID(model.getTYPE().toUpperCase() + "_" + model.getUSERNAME().toUpperCase());
                model.setLONG("" + mLong);
                model.setLAT("" + mLat);
                saveSignupData(model);
                Toast.makeText(SignupActivity.this, "Successfully " + model.getUSERNAME() + " has been registered as " + model.getTYPE(), Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_signup_locate_me)).getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    UiSettings uiSettings = mMap.getUiSettings();
                    uiSettings.setCompassEnabled(false);
                    uiSettings.setZoomControlsEnabled(true);
                    uiSettings.setMyLocationButtonEnabled(true);
                    LatLng india = new LatLng(-34, 151);
                    mMap.addMarker(new MarkerOptions().position(india).title("Marker in India"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(india));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    mMap.setMyLocationEnabled(true);
                    if (mMap != null) {
                        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                            @Override
                            public void onMyLocationChange(Location arg0) {
                                LatLng obj = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(obj).title("It's Me!"));
                                mLat = obj.latitude;
                                mLong = obj.longitude;
                            }
                        });
                    }
                }


            });

        }
    }

    private void saveSignupData(TableUserInfoDataModel pModel) {
        long row = 0;
        try {
            TableUserInfo table = new TableUserInfo();
            table.openDB(this);
            row = table.insert(pModel);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("SignUp", "saveSignupData: " + row + e.getMessage());
        }
    }
}
