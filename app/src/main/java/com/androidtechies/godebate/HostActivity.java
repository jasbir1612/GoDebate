package com.androidtechies.godebate;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jasbir Singh on 3/1/2016.
 */
public class HostActivity extends AppCompatActivity {

    public static final int REQUEST_DISCOVERABLE = 1;

    Toolbar toolbar;
    private EditText mMessage;

    boolean isTopic;
    private String mUsername;
    private String mChatRoomName;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothSocket> mSockets;
    private AcceptThread mAcceptThread;

    private ChatManager mChatManager;
    ImageView group;
    TextView title, penName;
    String topic;
    String topicAgain;
    public static final String Default = null;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        topicAgain = getIntent().getStringExtra("topic");

        title = (TextView) findViewById(R.id.toolbar_title);
        penName = (TextView) findViewById(R.id.pen_name);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        group = (ImageView) findViewById(R.id.group);
        group.setImageResource(R.drawable.ic_action_group);

        sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);


        title.setText(topicAgain);

        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.back);
        setSupportActionBar(toolbar);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAcceptThread != null) {
                    mAcceptThread.cancel();
                }
                initializeBluetooth();

            }
        });

        Button mSendButton = (Button) findViewById(R.id.send);
        mMessage = (EditText) findViewById(R.id.message);
        mChatManager = new ChatManager(this, true);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        initializeRoom();
    }


    public void initializeRoom() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Retrieve username
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = sharedPref.getString("username", mBluetoothAdapter.getName());
        initializeBluetooth();

//         Set up ChatRoom naming input
        final EditText nameInput = new EditText(this);
        nameInput.setSingleLine();
        nameInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);


//         Set up ChatRoom naming dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Ready?");
        builder.setView(nameInput);
//        nameInput.setText("Yes");
        nameInput.setHint("Enter you pen name");
        builder.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                mChatRoomName = nameInput.getText().toString();

                if (getActionBar() != null) {
                    getActionBar().setTitle(mChatRoomName);
                }
                penName.setText(nameInput.getText().toString());


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
                finish();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finish();
            }
        });

        // Show the dialog and disable the submit button until the name is longer than 0 characters
        final AlertDialog dialog = builder.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

//        nameInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//                if (charSequence.length() > 0) {
//                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
//                } else {
//                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {}
//        });
    }

    private void initializeBluetooth() {
        mSockets = new ArrayList<BluetoothSocket>();

        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(i, REQUEST_DISCOVERABLE);
    }

    private void sendMessage() {
        byte[] byteArray;

        if (mMessage.getText().toString().length() == 0) {
            return;
        }

        try {
            byte[] messageBytes = mMessage.getText().toString().getBytes();
            byteArray = mChatManager.buildPacket(
                    ChatManager.MESSAGE_SEND,
                    mUsername,
                    messageBytes
            );
        } catch (Exception e) {
            return;
        }

        mChatManager.writeMessage(byteArray);
        mMessage.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED && requestCode == REQUEST_DISCOVERABLE) {
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
            Toast.makeText(this, "Searching for users...", Toast.LENGTH_SHORT).show();
//        } else if (resultCode == RESULT_OK) {
////            Uri image = data.getData();
////            String[] filePathColumn = {MediaStore.Images.Media.DATA};
////            Cursor cursor = getContentResolver().query(image, filePathColumn, null, null, null);
//
////            cursor.moveToFirst();
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String picturePath = cursor.getString(columnIndex);
////
////            cursor.close();
        } else if (requestCode == REQUEST_DISCOVERABLE) {
            Toast.makeText(this, "New users cannot join your chat room", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mAcceptThread != null) {
            mAcceptThread.cancel();
        }

        if (mSockets != null) {
            for (BluetoothSocket socket : mSockets) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close socket");
                    System.err.println(e.toString());
                }
            }
        }
    }

    private void manageSocket(BluetoothSocket socket) {
        mChatManager.startConnection(socket);
        mSockets.add(socket);
        byte[] byteArray;

        byteArray = mChatManager.buildPacket(
                ChatManager.MESSAGE_NAME,
                mUsername,
                mChatRoomName.getBytes()
        );

        Toast.makeText(this, "User connected", Toast.LENGTH_SHORT).show();
        mChatManager.writeChatRoomName(byteArray);
    }

    private class AcceptThread extends Thread {

        private final BluetoothServerSocket mmServerSocket;
        private boolean isAccepting;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            isAccepting = true;

            try {
                tmp = mBluetoothAdapter.
                        listenUsingRfcommWithServiceRecord(
                                mChatRoomName, java.util.UUID.fromString(Main2Activity.UUID)
                        );
            } catch (IOException e) {
                System.err.println("Failed to set up Accept Thread");
                System.err.println(e.toString());
            }

            mmServerSocket = tmp;
        }

        public void run() {
            while (isAccepting) {
                final BluetoothSocket socket;

                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }

                if (socket != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            manageSocket(socket);
                        }
                    });
                }
            }
        }

        public void cancel() {
            try {
                isAccepting = false;
                mmServerSocket.close();
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
