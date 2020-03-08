package com.bird_brown.favoritebrowser01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "sqlite"; //データベース名
    private static int DB_VERSION = 1; //バージョン

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //テーブル作成用SQL文の設定
        String sql = "CREATE TABLE Sites (";
        sql += "title TEXT NOT NULL,";
        sql += "url TEXT NOT NULL);";

        //テーブル作成用SQL文を実行
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

    }
}
