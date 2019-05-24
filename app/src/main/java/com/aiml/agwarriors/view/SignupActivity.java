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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.database.TableUserInfo;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.utils.AppLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SignupActivity extends BaseActivity implements IActivity, OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Button mButton_signup_sign_up;
    private double mLat, mLong;
    private EditText mEdititext_signup_username;
    private EditText mEdititext_signup_password;
    private EditText mEdititext_signup_confirmation_password;
    private EditText mEdititext_signup_phone_number;
    private RadioGroup mRadiogroup_signup_holder;
    private Button mButton_locate_me;
    private ImageView mImageview_map;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mMap;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;

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
//        setUpMapIfNeeded();
    }

    @Override
    public void initView() {
        initHeader();
        mButton_locate_me = (Button) findViewById(R.id.button_locate_me);
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
//        if (mMap == null) {
//            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
//        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }



    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
