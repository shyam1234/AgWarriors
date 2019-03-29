package com.aiml.agwarriors.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.model.MainScreenModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MainScreenAdapter extends RecyclerView.Adapter<MainScreenAdapter.MyViewHolder> {
    private List<MainScreenModel> mList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView item;

        public MyViewHolder(View v) {
            super(v);
            item = v.findViewById(R.id.textview_row_main_item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainScreenAdapter(List<MainScreenModel> pList) {
        mList = pList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_list, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.item.setText(mList.get(position).getLabel());
        holder.item.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }
}
