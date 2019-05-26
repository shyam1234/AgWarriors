package com.aiml.agwarriors.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.CostMLResponseDataModel;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.restfulManager.WebApplication;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.ImageUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.okhttp.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;


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
    private ImageView map;
    private List<CostMLResponseDataModel> mPredictedCostList;
    private BarChart mBarchart_cost;
    //--For Granting Runtime Permission
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    protected static final int GALLERY_PICTURE = 101;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_regyield);
        init();
        initView();
    }
    /**
     * Open camera
     */
    private static final int CAMERA_REQUEST = 1888;

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
        title.setText("Record Yield");
    }
    File captureMediaFile;

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
    byte[] bytesDocumentsTypePicture = null;

    private void fetchCost() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ProductType", "Mango Raw");
            jsonObject.put("Area", "Bangalore city Market");
            jsonObject.put("DateToPredict", mEdittext_regyield_duration.getText().toString());
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
            WebApplication.getInstance().getPostResponse(Constant.URL_COST, jsonArray.toString(), new WebApplication.IWebApp() {
                @Override
                public void onResponse(final String response) {
                    if (response != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPredictedCostList = parseResponse(response);
                                addMPBarChart();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Request request, IOException e) {
                     Toast.makeText(RegisterYieldActivity.this, "Failed to fetch data", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
        }
    }

    private void addMPBarChart() {
        int index = 0 ;
        ArrayList<String> listDate = new ArrayList<String>();
        ArrayList<BarEntry> listCost = new ArrayList<BarEntry>();
        for(CostMLResponseDataModel holder: mPredictedCostList){
            listDate.add(holder.getDate());
            listCost.add(new BarEntry(holder.getPrice(), index++));
        }
        BarDataSet BardatasetCost = new BarDataSet(listCost, "Price");
        BarData BARDATA = new BarData(listDate,BardatasetCost);
        BardatasetCost.setColors(ColorTemplate.COLORFUL_COLORS);
        mBarchart_cost.setData(BARDATA);
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
        mModel.setStatus("Sent Broadcast By Verified Farmer");
        mModel.setStatusValue(YieldListModel.STATUS_SENT_BRAODCAST_TO_BUYER);
        mTextview_regyield_lot_no_value.setText("LOT" + "_" + mModel.getYield() + "_" + mModel.getYieldType() + "_" + System.currentTimeMillis());
        mModel.setLotnumber(mTextview_regyield_lot_no_value.getText().toString().replace("/", ""));
        if(bytesDocumentsTypePicture!=null) {
            mModel.setImageArray(bytesDocumentsTypePicture);
        }
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
    private Button mBtn_regyield_take_pic;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner_regyield_crop_type:
                break;
            case R.id.spinner_regyield_unit:
                if (mSpinner_regyield_cost.getSelectedItemPosition() != i) {
                    mSpinner_regyield_cost.setSelection(i);
                }
                break;
            case R.id.spinner_regyield_cost:
                if (mSpinner_regyield_unit.getSelectedItemPosition() != i) {
                    mSpinner_regyield_unit.setSelection(i);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * [ { "Date" : "05/10/19" , "price" : 3934 },
     * { "Date" : "05/15/19" , "price" : 4324 }  ]
     *
     * @param pResp
     */
    private List<CostMLResponseDataModel> parseResponse(String pResp) {
        List<CostMLResponseDataModel> list = new ArrayList<CostMLResponseDataModel>();
        try {
            JSONArray jsonArray = new JSONArray(pResp);
            if (jsonArray != null) {
                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(index);
                    if (jsonObject != null) {
                        CostMLResponseDataModel holder = new CostMLResponseDataModel();
                        holder.setDate(jsonObject.getString("Date"));
                        holder.setPrice(jsonObject.getString("price"));
                        list.add(holder);
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            return list;
        }
    }
    private ImageView mImg_regyield_yield_pic;

    @Override
    public void init() {
        mModel = new YieldListModel();
        initCalendar();
        mCalendear = Calendar.getInstance();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDgNfUmVAi0PTVWEpqIGrMBoP-RGFWNjcA");
        }
        checkAndRequestPermissions();
    }

    @Override
    public void initView() {
        initHeader();
        View fragment = findViewById(R.id.fragment_regyield_locate_buyer);
        map = (ImageView) findViewById(R.id.imageview_map);
        map.setVisibility(View.GONE);
        fragment.setVisibility(View.GONE);
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
        mBarchart_cost = (BarChart)findViewById(R.id.barchart_cost);
        mBarchart_cost.setVisibility(View.GONE);

        mBtn_regyield_take_pic = (Button) findViewById(R.id.btn_regyield_take_pic);
        mBtn_regyield_take_pic.setOnClickListener(this);

        mImg_regyield_yield_pic = (ImageView) findViewById(R.id.img_regyield_yield_pic);
        mImg_regyield_yield_pic.setOnClickListener(this);

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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_regyield_take_pic:
                openCamera();
                //Toast.makeText(RegisterYieldActivity.this,"Working",Toast.LENGTH_SHORT).show();
                break;
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
                Toast.makeText(RegisterYieldActivity.this, "Successfully sent broadcast to buyers ", Toast.LENGTH_LONG).show();
                addDataIntoList();
                finish();
                break;
            case R.id.button_regyield_smartanalysis:
                mBarchart_cost.setVisibility(View.VISIBLE);
                fetchCost();
                break;
            case R.id.button_regyield_Analysis:
                map.setVisibility(View.VISIBLE);
                //Toast.makeText(RegisterYieldActivity.this, "Coming soon",Toast.LENGTH_LONG).show();
                break;
        }
    }

    //Rate limit is 100 requests per second (QPS).
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
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
            }/*else  if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImg_regyield_yield_pic.setImageBitmap(photo);
        }*/ else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
                Bitmap bitmap;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    if (data != null) {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
                        new ImageUtils().setCompressBitmap(bitmap,mImg_regyield_yield_pic);
                    } else {
                        Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bitmap = BitmapFactory.decodeFile(captureMediaFile.getAbsolutePath());
                    bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
                    new ImageUtils().setCompressBitmap(bitmap,mImg_regyield_yield_pic);
                }

            } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
                if (data != null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  data.getData());
                    bytesDocumentsTypePicture = new ImageUtils().getBytesFromBitmap(bitmap);
                    new ImageUtils().setCompressBitmap(bitmap,mImg_regyield_yield_pic);
                } else {
                    Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), " some_error_while_uploading  ", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){}
    }

    private void openCamera() {
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, CAMERA_REQUEST);
          showCameraPopup();
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"),CAMERA_REQUEST);
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private void showCameraPopup(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Yield Photo");
        myAlertDialog.setMessage("How do you want to set yield picture?");
        myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_PICTURE);
            }
        });

        myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                captureMediaFile = ImageUtils.getOutputMediaFile(getApplicationContext());

                if (captureMediaFile != null) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, captureMediaFile);
                    } else {
                        Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", captureMediaFile);
                        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    }

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, CAMERA_REQUEST);
                    } else {
                       Toast.makeText(RegisterYieldActivity.this,"Error while upload",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterYieldActivity.this,"File Server Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        myAlertDialog.show();
    }
}
