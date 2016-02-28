package com.gtbit.godebate;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

                            String name = topicInput.getText().toString();
                            Intent i = new Intent(Choose.this, Main2Activity.class);
                            i.putExtra("Topic", name);
                            startActivity(i);


                        }
                    });

                    final AlertDialog dialog = builder.show();
                    dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                    topicInput.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                            if (charSequence.length() > 0 && charSequence.length() <= 22) {
                                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            } else {
                                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                        }
                    });

                }
            }
        }



    }
}
