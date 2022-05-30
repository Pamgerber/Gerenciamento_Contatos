package com.example.agendatelefone.aplicativo;

import android.content.Context;
import android.graphics.drawable.Icon;

import androidx.appcompat.app.AlertDialog;

import com.example.agendatelefone.R;

public class MessageBox {

    public static void showInfo(Context ctx, String title, String msg)
    {
        show(ctx, title, msg, android.R.drawable.ic_dialog_info);
    }

    public static void showAlert(Context ctx, String title, String msg)
    {
        show(ctx, title, msg, android.R.drawable.ic_dialog_alert);
    }

    public static void show(Context ctx, String title, String msg)
    {
        show(ctx, title, msg,0);
    }

    public static void show(Context ctx, String title, String msg, int IconId)
    {
        AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
        dlg.setIcon(IconId);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton(R.string.lnl_ok, null);
        dlg.show();
    }
}
