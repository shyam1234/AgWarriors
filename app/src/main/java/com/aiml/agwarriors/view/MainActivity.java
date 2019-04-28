package com.aiml.agwarriors.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.adapters.MainScreenAdapter;
import com.aiml.agwarriors.constant.Constant;
import com.aiml.agwarriors.database.TableYield;
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.MainScreenModel;
import com.aiml.agwarriors.model.YieldListModel;
import com.aiml.agwarriors.utils.AppLog;
import com.aiml.agwarriors.utils.CustomDialogbox;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements IActivity {

    private final String YIELD = "Yield";
    private final String NOTIFICATION = "Notification";
    private final String HISTORY = "History";
    private final String GENERAL_INFO = "General Information";
    private final String ANALYTICS = "Analytics";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MainScreenModel> mList;
    private RelativeLayout mNotification_holder;
    private TextView mTextview_header_notification_count;
    private ArrayList<YieldListModel> mListNotification;
    private ArrayList<YieldListModel> mListYieldBroadCast;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        init();
        switch (Constant.USER_INFO_LIST.get(0).getTYPE()) {
            case Constant.USER_FARMER:
                mListNotification = getNotification(Constant.USER_INFO_LIST.get(0).getID());
                mListYieldBroadCast = getBroadcastForSeller(Constant.USER_INFO_LIST.get(0).getID());
                initListForFarmer();
                break;
            case Constant.USER_BUYER:
                mListNotification = getNotificationForBuyerFromDB(Constant.USER_INFO_LIST.get(0).getID());
                mListYieldBroadCast = getBoradcastListForBuyer(Constant.USER_INFO_LIST.get(0).getID());
                initListForBuyer();
                break;
        }
        initView();
    }

    @Override
    public void init() {
        mList = new ArrayList<MainScreenModel>();
    }


    private void initListForFarmer() {
        mList.add(new MainScreenModel(YIELD));
        mList.add(new MainScreenModel(HISTORY));
        mList.add(new MainScreenModel(GENERAL_INFO));
        mList.add(new MainScreenModel(ANALYTICS));
    }

    private void initListForBuyer() {
        mList.add(new MainScreenModel(NOTIFICATION));
        mList.add(new MainScreenModel(HISTORY));
        mList.add(new MainScreenModel(ANALYTICS));
    }

    private void initHeader() {
        TextView textview_logout = (TextView) findViewById(R.id.textview_loogout);
        textview_logout.setVisibility(View.VISIBLE);
        textview_logout.setOnClickListener(this);
        mNotification_holder = (RelativeLayout) findViewById(R.id.rel_header_notification);
        mTextview_header_notification_count = (TextView) findViewById(R.id.textview_header_notification_count);
        ImageView back = (ImageView) findViewById(R.id.imageview_back);
        TextView title = (TextView) findViewById(R.id.textview_title);
        title.setText(R.string.app_name);
        back.setOnClickListener(this);
        mNotification_holder.setOnClickListener(this);
    }

    @Override
    public void initView() {
        initHeader();
        initRecyclerView();
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerview_main_holder);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MainScreenAdapter(mList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                switch (mList.get(position).getLabel()) {
                    case YIELD:
                        if (mListYieldBroadCast.size() > 0) {
                            navigateTo(MainActivity.this, YieldListActivity.class, false);
                        } else {
                            navigateTo(MainActivity.this, RegisterYieldActivity.class, false);
                        }
                        break;
                    case HISTORY:
                        navigateTo(MainActivity.this, HistoryListActivity.class, false);
                        break;
                    case GENERAL_INFO:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case ANALYTICS:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case NOTIFICATION:
                        navigateTo(MainActivity.this, BuyerProposalActivity.class, false);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                // Toast.makeText(MainActivity.this, "Long press on position :" + position,
                //    Toast.LENGTH_LONG).show();
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
        switch (Constant.USER_INFO_LIST.get(0).getTYPE()) {
            case Constant.USER_FARMER:
                mListNotification = getNotification(Constant.USER_INFO_LIST.get(0).getID());
                mListYieldBroadCast = getBroadcastForSeller(Constant.USER_INFO_LIST.get(0).getID());
                if (mListNotification != null && mListNotification.size() > 0) {
                    mNotification_holder.setVisibility(View.VISIBLE);
                    mTextview_header_notification_count.setText(""+mListNotification.size());
                } else {
                    mNotification_holder.setVisibility(View.GONE);
                }
                break;
            case Constant.USER_BUYER:
                mListNotification = getNotification(Constant.USER_INFO_LIST.get(0).getID());
                mListYieldBroadCast = getBoradcastListForBuyer(Constant.USER_INFO_LIST.get(0).getID());
                if (mListNotification != null && mListNotification.size() > 0) {
                    mNotification_holder.setVisibility(View.VISIBLE);
                    mTextview_header_notification_count.setText(""+mListNotification.size());
                } else {
                    mNotification_holder.setVisibility(View.GONE);
                }
                break;
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
    public void onBackPressed() {
        popupCloseAppAcknowledge();
    }

    private void popupCloseAppAcknowledge() {
        final CustomDialogbox dialogbox = new CustomDialogbox(this, CustomDialogbox.TYPE_YES_NO);
        dialogbox.setTitle("Do you want to exit?");
        dialogbox.show();
        dialogbox.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        dialogbox.getBtnNo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogbox.dismiss();
            }
        });
        dialogbox.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface mDialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
    }


    private ArrayList<YieldListModel> getNotification(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getNotificationList(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("MainActivity", "getNotification: " + e.getMessage());
        } finally {
            return list;
        }
    }

    private ArrayList<YieldListModel> getNotificationForBuyerFromDB(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getNotificationListForBuyer(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("MainActivity", "getNotificationForBuyerFromDB: " + e.getMessage());
        } finally {
            return list;
        }
    }

    private ArrayList<YieldListModel> getBroadcastForSeller(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getBroadcastForSeller(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("MainActivity", "getBroadcastForSeller: " + e.getMessage());
        } finally {
            return list;
        }
    }

    private ArrayList<YieldListModel> getBoradcastListForBuyer(String pUID) {
        ArrayList<YieldListModel> list = new ArrayList<>();
        try {
            TableYield table = new TableYield();
            table.openDB(this);
            list = table.getProposalForBuyer(pUID);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog("MainActivity", "getBoradcastListForBuyer: " + e.getMessage());
        } finally {
            return list;
        }
    }

}
