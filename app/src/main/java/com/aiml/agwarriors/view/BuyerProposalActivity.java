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
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class BuyerProposalActivity extends BaseActivity implements IActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<YieldListModel> mNotificationList;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification);
        init();
        initView();
    }


    @Override
    public void init() {
        mNotificationList = getProposalForBuyer(Constant.USER_INFO_LIST.get(0).getID());
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
        title.setText("Yield Proposal");
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerview_notification);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NotificationAdapter(mNotificationList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                switch (view.getId()) {
                    case R.id.lin_row_list_yield:
                      //  Toast.makeText(NotificationActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        mNotificationList.get(position).setFrom(YieldListModel.FROM_PROPOSAL);
                        bundle.putSerializable(Constant.KEY_SEND_BROADCAST, mNotificationList.get(position));
                        navigateTo(BuyerProposalActivity.this, ReadYieldDetailActivity.class, bundle, false);
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
        mNotificationList = getProposalForBuyer(Constant.USER_INFO_LIST.get(0).getID());
        if (mNotificationList.size() == 0) {
            Toast.makeText(BuyerProposalActivity.this,"Notification not found",Toast.LENGTH_LONG).show();
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

    private ArrayList<YieldListModel> getProposalForBuyer(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getProposalForBuyer(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("NotificationActivity", "getProposalForBuyer: " + e.getMessage());
        } finally {
            return list;
        }
    }
}
