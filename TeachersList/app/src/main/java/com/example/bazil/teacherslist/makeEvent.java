package com.example.bazil.teacherslist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class makeEvent extends AppCompatActivity {

    //these are the columns names in basic data
    //and the data we want in the title spinner
    String[] title = {"Lecture","Administration","Test","Supervision","Practical"};
    Spinner sp1, sp2;
    EditText et1;
    TextView tv1;
    DBOperations dbo;
    String title1, describe;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        sp1 = (Spinner) findViewById(R.id.spinnerTitle);
        sp2 = (Spinner) findViewById(R.id.spinnerClass);
        et1 = (EditText) findViewById(R.id.editDesc);

        tv1 = (TextView) findViewById(R.id.textClass);

        fillSpinner1();

        Button button = (Button) findViewById(R.id.nextBtn);

        Button mButton = findViewById(R.id.addBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //values of the spinners and textedit
                try{
                    describe = et1.getText().toString().trim();
                    title1 = sp1.getSelectedItem().toString();
                }
                catch(Exception e){
                    Toast.makeText(makeEvent.this,"SAVE DATA INTO SETTINGS FIRST!!",Toast.LENGTH_SHORT).show();
                }
                if(title1 == "Lecture") {
                    String classs = sp2.getSelectedItem().toString();
                    insertEventLec(title1,describe,classs);
                }
                else{
                    if(title1 == null || describe == null){
                        Toast.makeText(makeEvent.this,"SAVE DATA INTO SETTINGS FIRST!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        insertEvent(title1, describe);
                    }

                }

                //WE CALL inserEvent and then we go to main activity

                Intent intent = new Intent(makeEvent.this,MainActivity.class);
                startActivity(intent);
            }
        });

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String title = sp1.getSelectedItem().toString();
                if(title == "Lecture"){
                    tv1.setVisibility(View.VISIBLE);
                    sp2.setVisibility(View.VISIBLE);
                }
                else{
                    tv1.setVisibility(View.GONE);
                    sp2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }




    public void fillSpinner1(){
        try {

            dbo = new DBOperations(this);

            Cursor res = dbo.getBasicData();
            if (res.getCount() == 0) {
                //System.out.println("nothig to show here");
                return;
            } else {
                List<String> list = new ArrayList<String>();
                while(res.moveToNext()) {
                    for (int i = 0; i < 5; i++) {
                        //System.out.println("in side for");
                        if (res.getInt(i) == 1) {
                            //System.out.println(res.getInt(i)+" is for "+title[i]+" at "+i);
                            list.add(title[i]);
                        }
                    }
                    list.add("Just a thought");
                    if(res.getInt(0) == 1){
                        fillSpinner2();
                    }

                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(dataAdapter);
            }
        }
        catch(Exception e){
            //System.out.println("error in fill spinner 1 "+ e);
        }

    }

    public void fillSpinner2(){
        try {

            dbo = new DBOperations(this);

            Cursor res = dbo.getClassData();
            if (res.getCount() == 0) {
                //System.out.println("nothig to show here");
                return;
            } else {
                List<String> list = new ArrayList<String>();
                while(res.moveToNext()) {
                    list.add(res.getString(0));
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(dataAdapter);
            }
        }
        catch(Exception e){
            //System.out.println("error in fill spinner 2 "+ e);
        }
    }

    public void insertEventLec(String title,String describe, String classs){
        DBOperations dbo = new DBOperations(this);
        dbo.insertIntoEventLecture(title,describe,classs);
    }
    public void insertEvent(String title,String describe){
        DBOperations dbo = new DBOperations(this);
        dbo.insertIntoEvent(title,describe);
    }
}