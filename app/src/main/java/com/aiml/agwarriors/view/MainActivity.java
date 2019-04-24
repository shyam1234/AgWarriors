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
import com.aiml.agwarriors.interfaces.IActivity;
import com.aiml.agwarriors.model.MainScreenModel;
import com.aiml.agwarriors.utils.CustomDialogbox;
import com.aiml.agwarriors.utils.RecyclerTouchListener;
import com.aiml.agwarriors.utils.Utils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements IActivity {

    private final int YIELD = 0;
    private final int HISTORY = 1;
    private final int GENERAL_INFO = 2;
    private final int ANALYTICS = 3;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MainScreenModel> mList;
    private RelativeLayout mNotification_holder;
    private TextView mTextview_header_notification_count;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    @Override
    public void init() {
        mList = new ArrayList<MainScreenModel>();
        initList();
    }

    private void initList() {
        mList.add(new MainScreenModel("Yield"));
        mList.add(new MainScreenModel("History"));
        mList.add(new MainScreenModel("General Information"));
        mList.add(new MainScreenModel("Analytics"));

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

//        if (!Utils.isNotificationForSeller(Constant.mList)) {
//            mNotification_holder.setVisibility(View.GONE);
//        }else{
//            mNotification_holder.setVisibility(View.VISIBLE);
//            mTextview_header_notification_count.setText(Utils.getNotificationCountForSeller(Constant.mList));
//        }
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
                switch (position) {
                    case YIELD:
                        if (Constant.mList.size() > 0) {
                            navigateTo(MainActivity.this, YieldListActivity.class, false);
                        } else {
                            navigateTo(MainActivity.this, RegisterYieldActivity.class, false);
                        }
                        break;
                    case GENERAL_INFO:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case HISTORY:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        break;
                    case ANALYTICS:
                        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_SHORT).show();
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
        if (!Utils.isNotificationForSeller(Constant.mList)) {
            mNotification_holder.setVisibility(View.GONE);
        } else {
            mNotification_holder.setVisibility(View.VISIBLE);
            mTextview_header_notification_count.setText(Utils.getNotificationCountForSeller(Constant.mList));
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



}
