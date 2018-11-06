package com.example.taweesak.myappsqliterv.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarm.db";
    private static final String TABLE_ALARM = "alarmTable";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";


    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARM_TABLE = "CREATE	TABLE " + TABLE_ALARM + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                "" + COLUMN_TITLE + " TEXT," + COLUMN_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);

    }

    public List<Model> listModel() {
        String sql = "select * from " + TABLE_ALARM;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Model> alarmNoti = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                alarmNoti.add(new Model(id, title, content));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alarmNoti;
    }

    public void addAlarm(Model model) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
        values.put(COLUMN_CONTENT, model.getContent());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ALARM, null, values);
    }

    public void updateAlarm(Model model) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, model.getTitle());
        values.put(COLUMN_CONTENT, model.getContent());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_ALARM, values, COLUMN_ID + "	= ?", new String[]{String.valueOf(model.getId())});
    }

    public void deleteAlarm(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, COLUMN_ID + "	= ?", new String[]{String.valueOf(id)});
    }

    /*public Model findAlarm(String title) {
        String query = "Select * FROM " + TABLE_ALARM + " WHERE " + COLUMN_TITLE + " = " + "title";
        SQLiteDatabase db = this.getWritableDatabase();
        Model mModel = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String alarmNotiTitle = cursor.getString(1);
            String alarmNotiContent = cursor.getString(2);
            mModel = new Model(id, alarmNotiTitle, alarmNotiContent);
        }
        cursor.close();
        return mModel;
    }*/

}
