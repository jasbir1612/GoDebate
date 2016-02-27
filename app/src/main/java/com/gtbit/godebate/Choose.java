package com.gtbit.godebate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gc.materialdesign.views.ButtonFlat;

public class Choose extends AppCompatActivity implements View.OnClickListener {

    ButtonFlat chooseTopic,createTopic, chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        createTopic = (ButtonFlat) findViewById(R.id.create_topic);
        chooseTopic = (ButtonFlat) findViewById(R.id.choose_topic);
        chat = (ButtonFlat) findViewById(R.id.chat);

        chooseTopic.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.choose_topic)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }
}
