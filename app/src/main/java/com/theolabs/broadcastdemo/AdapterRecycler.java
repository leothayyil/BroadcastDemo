package com.theolabs.broadcastdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Prejin on 11/4/2017.
 */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.MyViewHolder> {
    private ArrayList<IncomingNumber>arrayList=new ArrayList<>();

    public AdapterRecycler(ArrayList<IncomingNumber>arrayList){
        this.arrayList=arrayList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.id.setText(Integer.toString(arrayList.get(position).getId()));
        holder.number.setText(arrayList.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,number;
        public MyViewHolder(View itemView) {
            super(itemView);

            id=(TextView)itemView.findViewById(R.id.idtxt);
            number=(TextView)itemView.findViewById(R.id.numbertxt);
        }
    }
}
