package com.example.rohantiwari.tag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

//
//    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, parent, null);
//        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
//        return rcv;
//    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_list, viewGroup, false);

        return new RecyclerViewHolders(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.objectName.setText(itemList.get(position).getName());
        holder.objectAddress.setText(itemList.get(position).getAddr());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}