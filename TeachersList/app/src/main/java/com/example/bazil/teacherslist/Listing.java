package com.example.bazil.teacherslist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Listing extends AppCompatActivity{

    RecyclerView recyclerView;

    private static  Adapter ada;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ada = new Adapter(this);
        recyclerView.setAdapter(ada);

        //THIS IS WHERE WE CLICK THE BUTTON AND SAVE THE DATA AND WE GO FORWARD TO MAIN ACTIVITY
        //THERE IN MAIN ACTIVITY WE CAN ADD EVENTS
        Button button = (Button) findViewById(R.id.nextBtn);

        Button mButton = findViewById(R.id.nextBtn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //WE CALL insertBasic and then we go to main activity
                String[] yes = ada.getYesNo();
                if(yes[0] == "no" && yes[1] == "no" && yes[2] == "no" && yes[3] == "no" && yes[4] == "no"){
                    Toast.makeText(Listing.this,"ANSWER AT LEAST ONE QUESTION!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean[] check = ada.getCheckedItems();
                    if(yes[0] == "yes" && check[0]== false && check[1]== false && check[2]== false && check[3]== false && check[4]== false){
                        Toast.makeText(Listing.this,"SELECT AT LEAST ONE CLASS!!",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        insertBasic();
                        Intent intent = new Intent(Listing.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    void insertBasic(){
        //HERE WE INSERT INTO THE SQLite THE BASIC DATA OF THE USER
        //LIKE LECTURES, SUPERVISION AND STUFF
        DBOperations dbo = new DBOperations(this);
        dbo.insertIntoBasic(ada);
    }

}
