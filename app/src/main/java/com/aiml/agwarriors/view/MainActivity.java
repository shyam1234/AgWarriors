package com.aiml.agwarriors.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aiml.agwarriors.IActivity;
import com.aiml.agwarriors.R;
import com.aiml.agwarriors.adapters.MainScreenAdapter;
import com.aiml.agwarriors.model.MainScreenModel;
import com.aiml.agwarriors.utils.RecyclerTouchListener;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity implements IActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<MainScreenModel> mList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Dashboard");
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

    @Override
    public void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_holder);
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
                //Values are passing to activity & to fragment as well
                Toast.makeText(MainActivity.this, "Single Click on position        :" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :" + position,
                        Toast.LENGTH_LONG).show();
            }
        }));
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
}
