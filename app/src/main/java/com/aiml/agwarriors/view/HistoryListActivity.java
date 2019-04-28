package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.adapters.NotificationAdapter;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.TableUserInfoDataModel;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class HistoryListActivity extends BaseActivity implements IActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<YieldListModel> mHistoryYieldList;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification);
        init();
        initView();
    }


    @Override
    public void init() {
        mHistoryYieldList = getHistoryYieldData(Constant.USER_INFO_LIST.get(0).getID());
    }


    @Override
    public void initView() {
        initHeader();
        initRecyclerView();
    }

    private void initHeader() {
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title.setText("History Yield Transaction");
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_notification);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NotificationAdapter(mHistoryYieldList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                switch (view.getId()) {
                    case R.id.lin_row_list_yield:
                        Bundle bundle = new Bundle();
                        mHistoryYieldList.get(position).setFrom(YieldListModel.FROM_HISTORY);
                        bundle.putSerializable(Constant.KEY_SEND_BROADCAST, mHistoryYieldList.get(position));
                        //Toast.makeText(HistoryListActivity.this, "Coming Soon", Toast.LENGTH_LONG).show();
                        navigateTo(HistoryListActivity.this, ReadYieldDetailActivity.class, bundle, false);
                        break;
                }
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
        mHistoryYieldList = getHistoryYieldData(Constant.USER_INFO_LIST.get(0).getID());
        if (mHistoryYieldList.size() == 0) {
            Toast.makeText(HistoryListActivity.this, "History transaction not found", Toast.LENGTH_LONG).show();
            finish();
        }
        if (mAdapter != null) {
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

        }
        super.onClick(view);
    }

    private ArrayList<YieldListModel> getHistoryYieldData(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            if (Constant.USER_INFO_LIST.get(0).getTYPE().equalsIgnoreCase(TableUserInfoDataModel.TYPE_USER_BUYER)) {
                list = table.getHistoryListForBuyer(pUID);
            }else{
                list = table.getHistoryList(pUID);
            }
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("HistoryListActivity", "getHistoryYieldData: " + e.getMessage());
        } finally {
            return list;
        }
    }
}
