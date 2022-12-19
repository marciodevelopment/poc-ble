package com.example.blepoc.ble;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import com.example.blepoc.util.Utils;

public class BtleDevice {
    private BluetoothDevice device;
    private Context context;
    private int rssi;

    public BtleDevice(BluetoothDevice device, Context context) {
        this.device = device;
        this.context = context;
    }

    public String getAdress() {
        return device.getAddress();
    }

    @SuppressLint("MissingPermission")
    public String getName() {
        return device.getName();
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

}
