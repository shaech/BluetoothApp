package com.shachi.projects.bluetoothapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button mstartBT;
    TextView mMsg;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    int REQUEST_ENABLE_BT = 0;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mstartBT = findViewById(R.id.startBt);
        mMsg = findViewById(R.id.textMessage);


        mstartBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMsg.setText(" ");
                if(mBluetoothAdapter==null){

                     Toast.makeText(MainActivity.this, "No Bluetooth Device Found", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(mBluetoothAdapter.isEnabled()){
                        mMsg.append(" Bluetooth is enabled.. \n");
                        getPairedDevices();

                        mMsg.append("\n Disabling Bluetooth.");
                        mBluetoothAdapter.disable();

                        }
                    else{
                      //  mBluetoothAdapter.enable();
                        mMsg.append(" \n Bluetooth is disabled.. \n");

                        Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        setResult(REQUEST_ENABLE_BT, intentBtEnabled);
                        startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);

                    }
                }

            }

        });

    }

    private void getPairedDevices() {

        mMsg.append("\n Finding Paired Devices... \n");

        if(mBluetoothAdapter.startDiscovery()){
            mMsg.append("\n Devices Found: \n ***********");
            pairedDevices = mBluetoothAdapter.getBondedDevices();

            for(BluetoothDevice bt : pairedDevices) mMsg.append("\n "+bt.getName());

        }
        else
            mMsg.append("Device Not Found \n");

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                mMsg.append("Bluetooth Enabled...");
                getPairedDevices();

            } else { // RESULT_CANCELED as user refuse or failed
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
