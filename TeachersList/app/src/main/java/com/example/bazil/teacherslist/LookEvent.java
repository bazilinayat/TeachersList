package com.example.bazil.teacherslist;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LookEvent extends AppCompatActivity {

    String title, date, time, subtitle, describe;
    TextView lookTitle, lookDate, lookTime, lookSub, lookDesc;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_event);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        lookTitle = (TextView) findViewById(R.id.textTitleView);
        lookDate = (TextView) findViewById(R.id.textDateView);
        lookTime = (TextView) findViewById(R.id.textTimeView);
        lookSub = (TextView) findViewById(R.id.textSubView);
        lookDesc = (TextView) findViewById(R.id.textDescView);

        //System.out.println("look desc : "+lookDesc);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            title = b.getString("title");
            date = b.getString("date");
            time = b.getString("time");
            subtitle = b.getString("subtitle");
            describe = b.getString("desc");
        }
        //System.out.println(describe+ ": desc" );

        this.setTitle(title);

        lookTitle.setText("Title : "+title+"\n\n");
        if(subtitle == null) {
            lookSub.setText("");
        }
        else{
            lookSub.setText("Class : "+subtitle+"\n\n");
        }
        lookDate.setText(date+"\n\n");
        lookTime.setText(time+"\n\n");

        String empty = new String();
        if(describe.equals(empty)){
            lookDesc.setText("Description : No Description");
        }
        else {
            lookDesc.setText("Description : " + describe);
        }

    }
}
