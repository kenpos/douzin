package com.app.ayahumohumo.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLHelper helper;
    SQLiteDatabase db;

    private void insert() {
        // TODO Auto-generated method stub
        try {
            // トランザクション開始 event TEXT, date TEXT, place TEXT, bikou TEXT
            db.beginTransaction();
            ContentValues value = new ContentValues();
            Log.d("create table", "");
            value.put("event","コミケ");
            value.put("date", "㋇15日");
            value.put("place", "有明");
            value.put("bikou", "お金を落とす");
            db.insert("event", null, value);
            // 全件正常に挿入したら、トランザクション成功
            db.setTransactionSuccessful();
            Toast.makeText(this, "イベント名", Toast.LENGTH_SHORT).show();

        } finally{
            // トランザクションの終了
            db.endTransaction();
            Toast.makeText(this, "登録しました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        helper = new SQLHelper(this);
        db = helper.getReadableDatabase();

        String table = "'event'";
        String[] columns = {"*"};
        String selection = null;
        String[] selectionArgs = null;
        String limit = null;
        Cursor c = db.query(table, columns, selection, selectionArgs, null, null, null, limit);
        c.moveToFirst();

        insert();

        LinearLayout cardLinear = (LinearLayout) this.findViewById(R.id.cardLinear);
        cardLinear.removeAllViews();

        int i = 0;
        while (c.moveToNext()) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.test_card, null);
            CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
            TextView event = (TextView) linearLayout.findViewById(R.id.event);
            TextView date_text = (TextView) linearLayout.findViewById(R.id.date_text);
            TextView place_text = (TextView) linearLayout.findViewById(R.id.place_text);
            TextView bikou_text = (TextView) linearLayout.findViewById(R.id.bikou_text);

            event.setText(c.getString(1).toString());
            date_text.setText(c.getString(2).toString());
            place_text.setText(c.getString(3).toString());
            bikou_text.setText(c.getString(4).toString());

            cardView.setTag(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, String.valueOf(v.getTag()) + "番目のCardViewがクリックされました", Toast.LENGTH_SHORT).show();
                }
            });
            cardLinear.addView(linearLayout, i);
            i++;
        }
        c.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
