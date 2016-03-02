package com.gtbit.godebate;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jasbir Singh on 3/2/2016.
 */
public class MessageBox {
    private String sender;
    private String message;
    private Date time;

    private boolean self;

    public MessageBox(String sender, String message, Date time, boolean self) {
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.self = self;
    }

//    public MessageBox(String sender, Date time, boolean self) {
//        this(sender, "", time, self);
//
//    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm");
        return dateFormatter.format(time);
    }

    public boolean isSelf() {
        return self;
    }


}
