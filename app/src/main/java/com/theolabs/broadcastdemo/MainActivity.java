package com.theolabs.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView;
    private RecyclerView.LayoutManager layoutManager;
    private BroadcastReceiver broadcastReceiver;

    private ArrayList<IncomingNumber>arrayList=new ArrayList<>();
    private AdapterRecycler adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerId);
        textView=(TextView)findViewById(R.id.emptyTextId);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter=new AdapterRecycler(arrayList);
        recyclerView.setAdapter(adapter);

        readFromDb();

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                readFromDb();
            }
        };
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(broadcastReceiver,new IntentFilter(DbContract.UPADATE_UI_FILTER));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void readFromDb(){

        arrayList.clear();
        DbHelper dbHelper=new DbHelper(this);
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        Cursor cursor=dbHelper.readNumber(database);

        if (cursor.getCount()>0){

            while (cursor.moveToNext()){
                String number;
                int id;

                number=cursor.getString(cursor.getColumnIndex(DbContract.INCOMING_NUMBER));
                id=cursor.getInt(cursor.getColumnIndex("id"));
                arrayList.add(new IncomingNumber(id,number));

            }
            cursor.close();
            dbHelper.close();
            adapter.notifyDataSetChanged();
            textView.setVisibility(View.GONE);
        }

    }
}
