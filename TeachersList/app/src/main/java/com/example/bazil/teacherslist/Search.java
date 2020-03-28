package com.example.bazil.teacherslist;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class Search extends AppCompatActivity {

    int year, month, day;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            year = b.getInt("year");
            month = b.getInt("month");
            day = b.getInt("day");
        }

        String date = year+"-"+month+"-"+day;

        this.setTitle("Events of : "+date);

        DBOperations dbo = new DBOperations(this);
        Cursor res = dbo.getEventData(date);

        //System.out.println("before taking recycler view");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //Adapter ada = new Adapter(this);
        //recyclerView.setAdapter(ada);


        MyAdapter adapter = new MyAdapter(this, EventModel.getObjectData(res));
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
