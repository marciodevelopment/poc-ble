package com.example.blepoc.ble;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.blepoc.util.Utils;

public class BroadcastReceiverState extends BroadcastReceiver {
    Context activityContext;

    public BroadcastReceiverState(Context activityContext) {
        this.activityContext = activityContext;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (!action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
            return;

        final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
        switch (state) {
            case BluetoothAdapter.STATE_OFF:
                Utils.toast(activityContext, "Bluetooth is off");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                Utils.toast(activityContext, "Bluetooth is turning off");
                break;
            case BluetoothAdapter.STATE_ON:
                Utils.toast(activityContext, "Bluetooth is ON");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                Utils.toast(activityContext, "Bluetooth is turning ON");
                break;

        }
    }


}

