package com.bird_brown.favoritebrowser01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SiteOperator {
    private DBHelper helper;

    public SiteOperator(DBHelper helper) {
        this.helper = helper;
    }

    public boolean addSite(Site site) {
        //SQLiteDatabaseクラスの取得（書込み用）
        SQLiteDatabase database = helper.getWritableDatabase();

        //テーブルに登録するレコードの設定準備
        ContentValues value = new ContentValues();
        value.put("title", site.getTitle());
        value.put("url", site.getUrl());

        //Siteテーブルに登録
        boolean judge = database.insert("Sites", null, value) != -1 ? true : false;

        //データベースをクローズ
        database.close();

        return judge;
    }

    public ArrayList<Site> getSites() {
        //サイト情報格納用配列の生成
        ArrayList<Site> sites = new ArrayList<Site>();

        //SQLiteDatabaseクラスの取得（読込用）
        SQLiteDatabase database = helper.getReadableDatabase();

        //Siteテーブルからデータを取得する列を設定
        String[] columns = {"title", "url"};

        //Siteテーブルからレコードを取得
        Cursor cursor = database.query("Sites", columns, null, null, null, null, null);

        //取得したレコードが存在すれば、Siteクラスに格納後、配列に追加する
        while (cursor.moveToNext()) {
            sites.add(new Site(cursor.getString(0), cursor.getString(1)));
        }

        //データベースをクローズ
        database.close();

        //サイト情報を返す
        return sites;
    }
}
