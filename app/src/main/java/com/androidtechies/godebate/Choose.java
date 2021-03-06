package com.androidtechies.godebate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Choose extends AppCompatActivity implements View.OnClickListener {

    Button chooseTopic,createTopic, chat;
    public static EditText topicInput;
    Toolbar toolbar;
    TextView titlebar;
    ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titlebar = (TextView) findViewById(R.id.toolbar_title);
        settings = (ImageView) findViewById(R.id.group);
        titlebar.setText("ChatDebate");
//        toolbar.setTitle("ChatDebate");

        settings.setImageResource(R.drawable.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Choose.this, About.class);
                startActivity(i);
            }
        });

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        createTopic = (Button) findViewById(R.id.create_topic);
        chooseTopic = (Button) findViewById(R.id.choose_topic);
        chat = (Button) findViewById(R.id.chat);
        chooseTopic.setOnClickListener(this);
        chat.setOnClickListener(this);
        createTopic.setOnClickListener(this);

        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(1400);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        chooseTopic.setAnimation(animation);
        createTopic.setAnimation(animation);
        chat.setAnimation(animation);
        chooseTopic.startAnimation(fadeIn);
        createTopic.startAnimation(fadeIn);
        chat.startAnimation(fadeIn);


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
                    topicInput = new EditText(this);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Enter the debate topic");
                    builder.setView(topicInput);
//                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    builder.setPositiveButton("Start Debate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            SharedPreferences share = getSharedPreferences("MyData", MODE_PRIVATE);
//                            SharedPreferences.Editor editor = share.edit();
//                            editor.putString("name", topicInput.getText().toString());
//                            editor.apply();

                            Intent i = new Intent(Choose.this, Main2Activity.class);
                            i.putExtra("topic", topicInput.getText().toString());
                            startActivity(i);


                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            imm.hideSoftInputFromWindow(topicInput.getWindowToken(), 0);
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
                            if (charSequence.length() > 0) {
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
