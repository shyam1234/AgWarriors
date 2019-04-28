package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.adapters.YieldListAdapter;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class YieldListActivity extends BaseActivity implements IActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mButton_list_yield;
    private ArrayList<YieldListModel> mListYieldBroadCast;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_list_yield);
        init();
        initView();
    }


    @Override
    public void init() {
        mListYieldBroadCast = getBoradcastList(Constant.USER_INFO_LIST.get(0).getID());
    }


    @Override
    public void initView() {
        initHeader();
        initRecyclerView();
        mButton_list_yield = (Button) findViewById(R.id.button_list_yield);
        mButton_list_yield.setOnClickListener(this);
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("Yield");
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_list_yield_holder);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new YieldListAdapter(mListYieldBroadCast);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Bundle bundle = new Bundle();
                mListYieldBroadCast.get(position).setFrom(YieldListModel.FROM_REG_YIELD);
                bundle.putSerializable(Constant.KEY_SEND_BROADCAST, mListYieldBroadCast.get(position));
                navigateTo(YieldListActivity.this, ReadYieldDetailActivity.class, bundle, false);
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
        readYieldDB();
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


    private void readYieldDB() {
        try {
            mListYieldBroadCast = getBoradcastList(Constant.USER_INFO_LIST.get(0).getID());
            if (mListYieldBroadCast.size() == 0) {
                finish();
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            AppLog.errLog("YieldListActivity", "readYieldDB: " + e.getMessage());
        }
    }

    private ArrayList<YieldListModel> getBoradcastList(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getBroadcastForSeller(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("YieldListActivity", "getBoradcastList: " + e.getMessage());
        } finally {
            return list;
        }
    }
}
