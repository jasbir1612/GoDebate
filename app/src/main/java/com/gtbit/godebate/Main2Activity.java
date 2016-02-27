package com.gtbit.godebate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }
}
