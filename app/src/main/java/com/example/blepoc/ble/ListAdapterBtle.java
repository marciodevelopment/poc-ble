package com.example.blepoc.ble;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.blepoc.R;

import java.util.ArrayList;

public class ListAdapterBtle extends ArrayAdapter<BtleDevice> {
    //Activity activity;
    int layoutResourceId;
    ArrayList<BtleDevice> devices;
    Context context;

    public ListAdapterBtle(@NonNull Context context, int resource, @NonNull ArrayList<BtleDevice> objects) {
        super(context, resource, objects);
        this.context = context;
        //this.activity = activity;
        layoutResourceId = resource;
        this.devices = devices;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        BtleDevice device =  super.getItem(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResourceId, parent,false);
        }
        String name = device.getName();
        String address = device.getAdress();
        int rssi = device.getRssi();

        TextView tv_name = view.findViewById(R.id.tv_name);
        tv_name.setText("No name");
        if (name != null && name.length() > 0) {
            tv_name.setText(name);
        }

        TextView tv_rssi = view.findViewById(R.id.tv_rssi);
        tv_rssi.setText("RSSI: " + Integer.toString(rssi));

        TextView tv_macaddr = view.findViewById(R.id.tv_macaddr);
        tv_macaddr.setText("No address");
        if (address != null & address.length() > 0) {
            tv_macaddr.setText(address);
        }
        return view;
  }
}
