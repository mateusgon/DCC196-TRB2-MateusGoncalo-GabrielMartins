package com.example.gabriel.dcc196trabalho01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParticipanteDbHelper extends SQLiteOpenHelper{

    public final static int DATABASE_VERSION = 1;
    public final static String DATABASE_NAME = "Participante.db";

    public ParticipanteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AppContract.Participante.CREATE_PARTICIPANTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AppContract.Participante.DROP_PARTICIPANTE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}