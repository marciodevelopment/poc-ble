package com.example.blepoc.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.example.blepoc.MainActivity;

public class Utils {

    public static void toast(Context activityContext, String message) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show();

    }

    public static boolean checkBluetooh(BluetoothAdapter adapter) {
        return adapter != null && adapter.isEnabled();
    }

    public static void requestUserBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    }
}
