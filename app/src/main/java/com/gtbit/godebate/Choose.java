package com.gtbit.godebate;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        chat.setOnClickListener(this);
        createTopic.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if(id == R.id.choose_topic)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        else {
            if (id == R.id.chat) {
                Intent i = new Intent(this, Main2Activity.class);
                startActivity(i);
            }
            else {
                if (id == R.id.create_topic) {
                    final EditText topicInput = new EditText(this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Enter the debate topic");
                    builder.setView(topicInput);
                    builder.setPositiveButton("Start Debate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    final AlertDialog dialog = builder.show();
                    dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                }
            }
        }



    }
}
