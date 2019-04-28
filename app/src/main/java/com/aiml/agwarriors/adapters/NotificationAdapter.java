package com.aiml.agwarriors.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aiml.agwarriors.R;
import com.aiml.agwarriors.model.YieldListModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private List<YieldListModel> mList;

    // Provide a suitable constructor (depends on the kind of dataset)
    public NotificationAdapter(List<YieldListModel> pList) {
        mList = pList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification_type_one, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mList.get(position).getBidCostPerUnit() !=null ){
            holder.lin_bid_holder.setVisibility(View.VISIBLE);
            holder.proposedCostPerUnit.setText("Rs."+mList.get(position).getBidCostPerUnit()+"/"+mList.get(position).getCostUnit());
        }
        holder.lot_no.setText(mList.get(position).getLotnumber());
        holder.yield.setText(mList.get(position).getYield());
        holder.yield_type.setText(mList.get(position).getYieldType());
        holder.date.setText(mList.get(position).getDate());
        holder.costPerUnit.setText("Rs."+mList.get(position).getCostPerUnit()+"/"+mList.get(position).getCostUnit());
        holder.status.setText(mList.get(position).getStatus());
        holder.place_holder.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lin_bid_holder;
        public final TextView lot_no;
        private final TextView yield;
        private final TextView yield_type;
        private final TextView date;
        private final TextView costPerUnit;
        private final TextView status;
        private final LinearLayout place_holder;
        private final TextView proposedCostPerUnit;
//        private final Button btn_reject;
//        private final Button btn_accept;

        public MyViewHolder(View v) {
            super(v);
            lin_bid_holder = v.findViewById(R.id.lin_bid_holder);
            lot_no = v.findViewById(R.id.textview_regyield_lot_no_value);
            yield = v.findViewById(R.id.textview_regyield_crop_value);
            yield_type = v.findViewById(R.id.textview_regyield_crop_type_value);
            date = v.findViewById(R.id.textview_regyield_duration_value);
            costPerUnit = v.findViewById(R.id.textview_regyield_cost);
            proposedCostPerUnit = v.findViewById(R.id.textview_notification_proposed_cost_value);
            status = v.findViewById(R.id.textview_notification_status_value);
            place_holder = v.findViewById(R.id.lin_row_list_yield);
           // btn_reject = v.findViewById(R.id.btn_notification_reject);
           // btn_accept = v.findViewById(R.id.btn_notification_accept);
        }
    }
}
