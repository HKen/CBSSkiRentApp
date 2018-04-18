package com.smallpay.app.ski.rent.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyou.http.subsciber.IProgressDialog;

/**
 * Created by ken on 15/4/23.
 */
public class TPrompt {
    public static void showConfirmDialog(Context context, String title, String message, String positiveText, DialogInterface.OnClickListener positive, String negativeText, DialogInterface.OnClickListener cancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null && !title.equals("")) {
            builder.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(positiveText, positive);
        builder.setNegativeButton(negativeText, cancel);
        builder.create().show();
    }

}
