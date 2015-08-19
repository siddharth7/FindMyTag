package com.example.rohantiwari.tag;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView objectName;
    //public ImageView objectPhoto;
    public TextView objectAddress;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        objectName = (TextView)itemView.findViewById(R.id.item_name);
        //objectPhoto = (ImageView)itemView.findViewById(R.id.device_photo);
        objectAddress = (TextView)itemView.findViewById(R.id.object_address);
        //Log.d("recycle view","aadada");
    }

    @Override
    public void onClick(View view) {
        String name = objectName.getText().toString();
        String address = objectAddress.getText().toString();
        Intent i = new Intent(view.getContext(), Device_Menu.class);
        i.putExtra("Device_Name",name);
        i.putExtra("Device_Address",address);
        view.getContext().startActivity(i);

    }
}