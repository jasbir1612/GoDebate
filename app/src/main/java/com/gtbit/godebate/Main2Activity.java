package com.gtbit.godebate;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    Button host, join;
    Toolbar toolbar;


    public static final String UUID = "28286a80-137b-11e4-bbe8-0002a5d5c51b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        host = (Button) findViewById(R.id.host);
        join = (Button) findViewById(R.id.join);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Get Started");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(1400);
        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        host.setAnimation(animation);
        join.setAnimation(animation);
        host.startAnimation(fadeIn);
        join.startAnimation(fadeIn);

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
        Intent i = new Intent(this, ClientActivity.class);
        startActivity(i);
    }

    private void hostRoom() {
        Intent i = new Intent(this, HostActivity.class);
        startActivity(i);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_edit_name) {
//            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//            String username = sharedPref.getString("username", bluetoothAdapter.getName());
//            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//
//            final EditText nameInput = new EditText(this);
//            nameInput.setSingleLine();
//            nameInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
//            nameInput.setText(username);
//            nameInput.setSelectAllOnFocus(true);
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Enter your username");
//            builder.setView(nameInput);
//            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int which) {
//                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
//                    sharedPref.edit().putString("username", nameInput.getText().toString()).apply();
//                }
//            });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
//                }
//            });
//
//            final AlertDialog dialog = builder.show();
//            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
//
//            nameInput.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                    if (charSequence.length() > 0 && charSequence.length() <= 22) {
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                    } else {
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                    }
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {}
//            });
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

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
