package com.example.blepoc.ble;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.blepoc.MainActivity;
import com.example.blepoc.util.Utils;


public class ScannerBtle extends ScanCallback {
    private MainActivity mainActivity;
    private long scanPeriod;
    private int signalStrength;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private boolean scanning;
    private Handler handler = new Handler();



    public ScannerBtle(MainActivity mainActivity, long scanPeriod, int signalStrength) {
        this.mainActivity = mainActivity;
        this.handler = new Handler();
        this.scanPeriod = scanPeriod;
        this.signalStrength = signalStrength;
        final BluetoothManager bluetoothManager = (BluetoothManager) mainActivity.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    public boolean isScanning() {
        return scanning;
    }

    public void start() {
        if (!Utils.checkBluetooh(bluetoothAdapter)) {
            Utils.toast(mainActivity.getApplication(), "Bluetooth esta desligado.");
            mainActivity.stopScan();
            return;
        }
        mainActivity.startScan();
        startScanDevice();
    }

    @SuppressLint("MissingPermission")
    private void startScanDevice() {
        if (scanning) {
            stop();
            return;
        }
        Utils.toast(mainActivity.getApplication(), "Starting BLE Scan...");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }, scanPeriod);

        scanning = true;
        bluetoothLeScanner.startScan(this);

    }

    @SuppressLint("MissingPermission")
    public void stop() {
        scanning = false;
        bluetoothLeScanner.stopScan(this);
        mainActivity.stopScan();
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        super.onScanResult(callbackType, result);
        handler.post(new Runnable() {
            @Override
            public void run() {
                mainActivity.addDevice(result.getDevice(), result.getRssi());
            }
        });

    }
}
