package com.gtbit.godebate;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jasbir Singh on 3/2/2016.
 */
public class MessageFeedAdapter extends ArrayAdapter<MessageBox> {

    Context mContext;
    ClipboardManager clipboard;

    public MessageFeedAdapter(Context context, ArrayList<MessageBox> messages) {
        super(context, R.layout.message_row, messages);
        mContext=context;
        clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MessageBox message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.message_row, parent, false);
        }

        TextView senderView = (TextView) convertView.findViewById(R.id.name);
        final TextView messageView = (TextView) convertView.findViewById(R.id.message);
        TextView timeView = (TextView) convertView.findViewById(R.id.time);

        if (message.isSelf()) {
            senderView.setGravity(Gravity.RIGHT);
            messageView.setGravity(Gravity.RIGHT);

            RelativeLayout.LayoutParams rightAlign =
                    new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
            rightAlign.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }else{
            senderView.setGravity(Gravity.LEFT);
            messageView.setGravity(Gravity.LEFT);

            RelativeLayout.LayoutParams leftAlign =
                    new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    );
            leftAlign.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        messageView.setText(message.getMessage());
        senderView.setText(message.getSender());
        timeView.setText(message.getTime());

        if (message.getMessage().length() > 0) {
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipData clip = ClipData.newPlainText("message", message.getMessage());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, "Message copied to clipboard", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
        } else {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }

        return convertView;
    }
}
