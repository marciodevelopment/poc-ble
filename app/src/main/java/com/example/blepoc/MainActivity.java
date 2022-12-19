package com.example.blepoc;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;

import com.example.blepoc.ble.BroadcastReceiverState;
import com.example.blepoc.ble.BtleDevice;
import com.example.blepoc.ble.ListAdapterBtle;
import com.example.blepoc.ble.ScannerBtle;
import com.example.blepoc.util.Utils;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blepoc.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    // https://github.com/NordicSemiconductor/bluetooth-numbers-database/blob/master/v1/service_uuids.json
    private ActivityMainBinding binding;
    private final String TAG = "poc";

    public static final int REQUEST_ENABLE_BT = 1;
    private Map<String, BtleDevice> nBtleDeviceMap;
    private ArrayList<BtleDevice> nBtDevicesArrayList;
    private ListAdapterBtle adapter;
    private BroadcastReceiverState nBroadcastReceiverState;
    private ScannerBtle nScannerBtle;

    private Button btn_Scan;

    private BluetoothGattCharacteristic bluetoothGattCharacteristic;

    private EditText txtInformation;

    private UUID UUID_BLOOD_PRESSURE_MEASUREMENT =  UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    private UUID BATTERY_SERVICE =  UUID.fromString("0000180f-0000-0000-0000-000000000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Utils.toast(getApplicationContext(), "BLE não suportado");
            finish();
        }

        if (!hasPermissions(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
            }
        }


        nBroadcastReceiverState = new BroadcastReceiverState(getApplicationContext());
        nBtleDeviceMap = new HashMap<>();
        nBtDevicesArrayList = new ArrayList<>();
        nScannerBtle = new ScannerBtle(this, 7500, -80);

       adapter = new ListAdapterBtle(this, R.layout.btle_device_list_item, nBtDevicesArrayList);

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        ((ScrollView) findViewById(R.id.scrollView)).addView(listView);

        btn_Scan = findViewById(R.id.btn_scan);
        btn_Scan.setOnClickListener(this);
        txtInformation = findViewById(R.id.txtInformation);

        Log.d("poc", "on create finalizou");

    }

    private boolean hasPermissions(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onClick(View view) {
        Log.d("poc", "click botão scan");
        nScannerBtle.start();
    }

    public void addDevice(BluetoothDevice device, int rssi) {
        String address = device.getAddress();
        if (nBtleDeviceMap.containsKey(address)) {
            nBtleDeviceMap.get(address).setRssi(rssi);
            return;
        }
        BtleDevice deviceBtle = new BtleDevice(device, getApplication());
        deviceBtle.setRssi(rssi);
        nBtleDeviceMap.put(address, deviceBtle);
        nBtDevicesArrayList.add(deviceBtle);

        adapter.notifyDataSetChanged();
    }

    public void startScan() {
        btn_Scan.setText("scanning....");
        nBtDevicesArrayList.clear();
        nBtleDeviceMap.clear();
        adapter.notifyDataSetChanged();
    }

    public void stopScan() {
        btn_Scan.setText("Start Scan");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(nBroadcastReceiverState, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(nBroadcastReceiverState);
        stopScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopScan();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
        Utils.toast(this, "item selecionado " + this.nBtDevicesArrayList.get(index).getName());
        connectDevice(this.nBtDevicesArrayList.get(index));
    }

    @SuppressLint("MissingPermission")
    private void connectDevice(BtleDevice btleDevice) {
        btleDevice.getDevice().connectGatt(this, false, gattCallBack);
    }


    BluetoothGattCallback gattCallBack = new BluetoothGattCallback() {


        @SuppressLint("MissingPermission")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            }
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {

            }
        }

        @SuppressLint("MissingPermission")
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            // o que vem ?

            //displayGattServices(gatt.getServices());

            //bluetoothGattCharacteristic = gatt.getService(UUID_BLOOD_PRESSURE_MEASUREMENT).getCharacteristics().get(0);
            /*
            for (BluetoothGattService service: gatt.getServices()) {
                for (BluetoothGattCharacteristic charect: service.getCharacteristics()) {
                    gatt.setCharacteristicNotification(charect, true);
                    gatt.readCharacteristic(charect);
                }
            }
            */

            //gatt.setCharacteristicNotification(bluetoothGattCharacteristic, true);

            gatt.getService(BATTERY_SERVICE);
            bluetoothGattCharacteristic = gatt.getServices().get(2).getCharacteristics().get(0);

            gatt.readCharacteristic(bluetoothGattCharacteristic);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (characteristic.equals(bluetoothGattCharacteristic)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.toast(MainActivity.this, "Chractestic change");
                    }
                });
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, bluetoothGattCharacteristic, status);
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    broadcastUpdate(bluetoothGattCharacteristic);
                }
        }

    };

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        ArrayList<HashMap<String, String>> gattServiceData =
                new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();

        StringBuilder information = new StringBuilder();
        information.append(" New Service ").append("\n");
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData =
                    new HashMap<String, String>();
            information.append("Service UUID " + gattService.getUuid().toString()).append("\n");

            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            for (BluetoothGattCharacteristic gattCharacteristic :
                    gattCharacteristics) {
                information.append("Characteristic UUID " + gattService.getUuid().toString()).append("\n");
            }

            information.append(" New Service ").append("\n");

        }
        txtInformation.setText(information.toString());
    }

    private void broadcastUpdate(final BluetoothGattCharacteristic characteristic) {
        final byte[] data = characteristic.getValue();
        characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 2);
        int value = 0;
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for(byte byteChar : data) {
                stringBuilder.append(String.format("%02X ", byteChar));
                value = (value << 8) + (byteChar & 0xFF);
            }

            Log.d(TAG, stringBuilder.toString());


        }

        /*
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
                        stringBuilder.toString());
            }
        }

         */

           /*
        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data) + "\n" +
                        stringBuilder.toString());
            }
        }
            */
    }

    private void addLog(String text) {
        this.txtInformation.setText(
            this.txtInformation.getText() + "\n" + text);
    }


}
