package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.utils.Utils;

public class LocatePackagingActivity extends BaseActivity implements IActivity {


    private Button mBtn_locate_cold_search;
    private ImageView mImgViewMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_packaging);
        init();
        initView();
    }

    @Override
    public void init() {
    }


    @Override
    public void initView() {
        initHeader();
        mBtn_locate_cold_search = (Button)findViewById(R.id.btn_locate_cold_search);
        mBtn_locate_cold_search.setOnClickListener(this);
        mImgViewMap = (ImageView)findViewById(R.id.imageview_map);
        mImgViewMap.setOnClickListener(this);
        mImgViewMap.setVisibility(View.GONE);
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("Locate Packaging Facility");
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
        switch (view.getId()){
            case R.id.btn_locate_cold_search:
                mImgViewMap.setVisibility(View.VISIBLE);
                Utils.hideKeyboard(LocatePackagingActivity.this);
                break;
            case R.id.imageview_map:
                Toast.makeText(LocatePackagingActivity.this,"Getting more detail of selected packaging store", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
