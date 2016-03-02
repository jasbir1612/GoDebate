package com.gtbit.godebate;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button host, join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        host = (Button) findViewById(R.id.host);
        join = (Button) findViewById(R.id.join);

        String str = getIntent().getStringExtra("position");
        Toast.makeText(Main2Activity.this, str, Toast.LENGTH_SHORT).show();
        
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostRoom();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinRoom();
            }
        });

    }

    private void joinRoom() {
        Intent i = new Intent(Main2Activity.this, ClientActivity.class);
        startActivity(i);
    }

    private void hostRoom() {
        Intent i = new Intent(Main2Activity.this, HostActivity.class);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported on this device, now exiting.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
