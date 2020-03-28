package com.example.bazil.teacherslist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener {

    DBOperations dbo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            //System.out.println("start here");
            super.onCreate(savedInstanceState);
            //System.out.println("after on super before set content");
            setContentView(R.layout.activity_main);
            //System.out.println("after content before first time");

            dbo = new DBOperations(this);


            if (isFirstTime()) {
                // What you do when the Application is Opened First time Goes here
                //Toast.makeText(this, "Opened for the first time", Toast.LENGTH_SHORT).show();
                showHowTo();
            }
            //System.out.println("after first time before toolbar");

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            //System.out.println("after toolbar before fab");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //System.out.println("clicked the plus icon and create a new activity");
                    Cursor result = dbo.getBasicData();
                    if(result.getCount() == 0){
                        Toast.makeText(MainActivity.this,"SAVE DATA INTO SETTINGS FIRST!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent makeEvent = new Intent(MainActivity.this, makeEvent.class);
                        startActivity(makeEvent);
                    }
                }
            });

            //System.out.println("after the fab stuff");


            //LINKING MYADAPTER TO RECYCLER VIEW FOR SHOWING EVENTS

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Cursor res = dbo.getEventData(date);

            //System.out.println("before taking recycler view");

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            Adapter ada = new Adapter(this);
            recyclerView.setAdapter(ada);


            MyAdapter adapter = new MyAdapter(this, EventModel.getObjectData(res));
            recyclerView.setAdapter(adapter);

            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        catch(Exception e){
            //System.out.println("error in on create of main activity "+e);
        }

    }


    //HERE I CHECK IF THE APPLICATION IS OPENED FOR THE FIRST TIME
    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
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
        switch (id) {
            case  R.id.action_settings:
                //System.out.println("before intent in clicking the settings option");
                Intent iList = new Intent(this, Listing.class);
                startActivity(iList);
                break;
            case R.id.action_view:
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(this, this, year, month, day);
                dialog.show();
                break;
            case R.id.action_how:
                showHowTo();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showHowTo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("HOW TO?");
        builder.setMessage("1.Go to SETTINGS and add the things you do on daily basis.\n\n 2.Create EVENT whenever you do some daily activity. \n\n 3.SEARCH the events according to the date.\n\n4.VIEW those events by clicking on the event.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK",null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        //Toast.makeText(this, "Date has been picked - "+year+"-"+monthOfYear+"-"+dayOfMonth, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Search.class);
        Bundle b = new Bundle();
        b.putInt("year",year);
        b.putInt("month",monthOfYear+1);
        b.putInt("day",dayOfMonth);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
