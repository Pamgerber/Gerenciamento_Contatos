package com.example.agendatelefone.database;

import android.content.Context;
import android.database.sqlite.*;


public class DataBase extends SQLiteOpenHelper{

    public static final String getCreateContato = "nome";

    public DataBase(Context context)
    {
        super(context, "AGENDA", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateContato());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
