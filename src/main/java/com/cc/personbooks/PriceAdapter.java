package com.cc.personbooks;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.model.RentModel;

import java.util.ArrayList;

public class PriceAdapter extends RecyclerView.Adapter {

    private static final String TAG = PriceAdapter.class.getSimpleName();
    private ArrayList<RentModel> datequeryArrayList;
    private OnRecyclerViewListener onRecyclerViewListener;

    public static interface OnRecyclerViewListener {
        void onItemClick(int position);

        boolean onItemLongClick(int position);
    }

    public void setDatequeryArrayList(ArrayList<RentModel> datequeryArrayList) {
        this.datequeryArrayList = datequeryArrayList;
        notifyDataSetChanged();
    }

    public PriceAdapter() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_elecwater, null, false);
        // PersonViewHolder viewHolder = new PersonViewHolder(inflate);

        return new PersonViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonViewHolder viewHolder = (PersonViewHolder) holder;
        viewHolder.position = position;
        viewHolder.totle_value.setText(datequeryArrayList.get(position).getSumElec()+"");
        viewHolder.water_value.setText(datequeryArrayList.get(position).getSumwater()+"");
        viewHolder.costelec_num.setText(datequeryArrayList.get(position).getCostelec()+"");
        viewHolder.costwater_value.setText(datequeryArrayList.get(position).getCostwater()+"");
    }

    @Override
    public int getItemCount() {
        if (datequeryArrayList == null)
            return 0;
        else
            return datequeryArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        int position;
        TextView elec;
        TextView totle_value;
        TextView costelec_num;
        TextView water;
        TextView water_value;
        TextView costwater_value;
        TextView time_data;

        public PersonViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        public void initView(View itemView) {

            elec = (TextView) itemView.findViewById(R.id.elec);
            totle_value = (TextView) itemView.findViewById(R.id.totle_elec_value);
            costelec_num = (TextView) itemView.findViewById(R.id.costelec_num);
            water = (TextView) itemView.findViewById(R.id.water);
            water_value = (TextView) itemView.findViewById(R.id.totle_water_value);
            costwater_value = (TextView) itemView.findViewById(R.id.costwater_value);
            time_data = (TextView) itemView.findViewById(R.id.time_data);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "onClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
