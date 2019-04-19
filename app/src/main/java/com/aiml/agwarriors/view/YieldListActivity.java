package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.adapters.YieldListAdapter;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class YieldListActivity extends BaseActivity implements IActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<YieldListModel> mList;
    private Button mButton_list_yield;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_yield);
        init();
        initView();
    }


    @Override
    public void init() {
        mList = Constant.mList;
        //initList();
    }

//    private void initList() {
//        mList = new ArrayList<YieldListModel>();
//        YieldListModel model = new YieldListModel();
//        model.setLotnumber("LOT_F1_Y1_4192019");
//        model.setYield("Mango");
//        model.setYieldType("Begumpalli");
//        model.setDate("4/19/2019");
//        model.setCostPerUnit("100");
//        model.setStatus("Sent broadcast");
//        mList.add(model);
//    }


    @Override
    public void initView() {
        initHeader();
        initRecyclerView();
        mButton_list_yield = (Button)findViewById(R.id.button_list_yield);
        mButton_list_yield.setOnClickListener(this);
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        ImageView notification = (ImageView) findViewById(R.id.imageview_header_notification);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("Yield");
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_list_yield_holder);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new YieldListAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Toast.makeText(YieldListActivity.this, "position: "+position, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_SEND_BROADCAST,mList.get(position));
                navigateTo(YieldListActivity.this, ReadBroadcastYieldActivity.class,bundle, false);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constant.mList.size() == 0){
            finish();
        }
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
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
            case R.id.button_list_yield:
                navigateTo(YieldListActivity.this, RegisterYieldActivity.class, true);
                break;
        }
        super.onClick(view);
    }

}
