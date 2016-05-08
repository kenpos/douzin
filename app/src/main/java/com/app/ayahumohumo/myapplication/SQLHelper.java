package com.app.ayahumohumo.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ikeda_lab on 2016/05/08.
 */
public class SQLHelper extends SQLiteOpenHelper {

    //コンテキスト
    public SQLHelper(Context context){
        //データベースの更新を行う。　super（コンテキスト,データベース名,null,データベースのバージョン）
        //プログラムの処理的にデータベース名かバージョンを上げると一から全てデータベースを作り直してくれるよ
        super(context,"douzin.db",null,2);
        System.out.println("DBです");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Create_Table(db);
    }

    public void Create_Table (SQLiteDatabase db) {
        Log.d("デバッグ", "データベース作ります");
        db.execSQL("CREATE TABLE IF NOT EXISTS event (_id INTEGER  primary key autoincrement, event TEXT, date TEXT, place TEXT, bikou TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS artist( circle TEXT, pen_name TEXT, site_link TEXT, pixiv TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS buy_list( circle TEXT, artist TEXT, event TEXT, price INTEGER, haiti TEXT)");

        db.execSQL("insert into event(event, date, place, bikou) values ('コミックマーケット90', '8月15日', '有明', 'お金沢山落とす');");
        db.execSQL("insert into event(event, date, place, bikou) values ('例大祭6', '8月15日', '有明', 'お金沢山落とす');");
        db.execSQL("insert into event(event, date, place, bikou) values ('コミンティア', '8月15日', '有明', 'お金沢山落とす');");
        db.execSQL("insert into event(event, date, place, bikou) values ('コミックマーケット88', '8月15日', '有明', 'お金沢山落とす');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.d("デバッグ","データベース消します");
        db.execSQL("DROP TABLE event");
        Create_Table(db); // 新バージョンでテーブルを作成し直す
    }
}
