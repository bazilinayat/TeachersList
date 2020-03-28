package com.example.bazil.teacherslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBOperations extends SQLiteOpenHelper {

    //DATABASE NAME TO BE CREATED
    public static final String DB_NAME = "list.db";

    //TABLE NAME AND ITS ATTRIBUTES
    public static final String TAB_BASIC = "basic";
    public static final String ID = "id";
    public static final String B_C_0 = "Lecture";
    public static final String B_C_1 = "Administration";
    public static final String B_C_2 = "Test";
    public static final String B_C_3 = "Supervision";
    public static final String B_C_4 = "Practical";

    //TABLE NAME AND ITS ATTRIBUTE TO STORE CLASSES
    public static final String TAB_CLASS = "class";
    public static final String C_C_1 = "c_name";

    //TABLE NAME AND ITS ATTRIBUTES TO STORE EVENTS
    public static final String TAB_EVENT = "event";
    public static final String E_C_1 = "date";
    public static final String E_C_2 = "time";
    public static final String E_C_3 = "title";
    public static final String E_C_4 = "subtitle";
    public static final String E_C_5 = "describe";

/*    public DBOperations(){
        System.out.print(" hey there");
    }*/

    public DBOperations(Context context){
        super(context,DB_NAME,null,1);
        //context - , name of the database to build - ,factory - null, version of the database
        //THIS SETENCE WILL CREATE A DATABASE
        //System.out.println("database created");
    }



    @Override
    public void onCreate(SQLiteDatabase dbo) {
        dbo.execSQL("create table "+TAB_BASIC+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+B_C_0+" INTEGER,"+B_C_1+" INTEGER,"+B_C_2+" INTEGER,"+B_C_3+" INTEGER,"+B_C_4+" INTEGER);");
        //System.out.println("basic table created");
        dbo.execSQL("create table "+TAB_CLASS+"("+C_C_1+" TEXT);");
        //System.out.println("class table created");
        //DATE AND TIME IN SQLite ARE STORED AS NUMERIC, TEXT, ADN INTEGER WE GO WITH TEXT
        dbo.execSQL("create table "+TAB_EVENT+"("+E_C_1+" TEXT,"+E_C_2+" TEXT,"+E_C_3+" TEXT," +E_C_4+" TEXT,"+E_C_5+" TEXT);");
        //System.out.println("event table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbo, int i, int i1) {
        dbo.execSQL("DROP TABLE IF EXISTS " + TAB_BASIC);
        onCreate(dbo);
    }


    //FROM HERE WE CREATE METHODS FOR INSERT, UPDATE AND RETRIEVE
    public void insertIntoBasic(Adapter ada){
        //RUN THE SQL COMMANDS
        SQLiteDatabase db = this.getWritableDatabase();

        //SEE THE VALUES OF THE DIFFERENT ARRAYS FROM ADAPTER CLASS
        String[] yesNo = ada.getYesNo();
        String[] classes = ada.getClasses();
        boolean[] checked = ada.getCheckedItems();

        //DEFINE CONTENT VALUES AND PUT DATA OF BASIC TABLE INTO IT
        ContentValues cv = new ContentValues();
        if(yesNo[0] == "yes"){
            cv.put(B_C_0,1);
        }
        else{
            cv.put(B_C_0,0);
        }
        if(yesNo[1] == "yes"){
            cv.put(B_C_1,1);
        }
        else{
            cv.put(B_C_1,0);
        }
        if(yesNo[2] == "yes"){
            cv.put(B_C_2,1);
        }
        else{
            cv.put(B_C_2,0);
        }
        if(yesNo[3] == "yes"){
            cv.put(B_C_3,1);
        }
        else{
            cv.put(B_C_3,0);
        }
        if(yesNo[4] == "yes"){
            cv.put(B_C_4,1);
        }
        else{
            cv.put(B_C_4,0);
        }
        Cursor res= db.rawQuery("select * from basic",null);
        if(res.getCount() == 0){
            long result = db.insert(TAB_BASIC,null,cv);
            if(result == -1){
                //System.out.println("not inserted into basic");
            }
            else {
                //System.out.println("inserted into basic");
            }
        }
        else{
            db.update(TAB_BASIC,cv,"ID = ?",new String[] {" 1" });
            //System.out.println("updated basic table ");
        }


        if(yesNo[0] == "yes"){
            ContentValues cv1 = new ContentValues();
            for(int i=0;i<checked.length;i++){
                if(checked[i]){
                    cv1.put(C_C_1,classes[i]);
                    long result1 = db.insert(TAB_CLASS,null,cv1);
                    if(result1 == -1){
                        //System.out.println("not inserted into class");
                    }
                    else{
                        //System.out.println("inserted into class");
                    }
                }
            }
        }

    }

    public void insertIntoEvent(String title1,String descs){
/*WAY TO FIND THE CURRENT DATE AND TIME USING JAVA CLASSES

String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime());
*/
        SQLiteDatabase db = this.getWritableDatabase();
        if(descs == "") {
            db.execSQL("insert into event(date,time,title,describe) values(date('now','localtime'),time('now','localtime'),'" + title1 + "',NULL)");
        }
        else {
            db.execSQL("insert into event(date,time,title,describe) values(date('now','localtime'),time('now','localtime'),'" + title1 + "','" + descs + "')");
        }

    }

    public void insertIntoEventLecture(String title1,String descs,String classes){
/*WAY TO FIND THE CURRENT DATE AND TIME USING JAVA CLASSES

String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime());
*/
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into event(date,time,title,subtitle,describe) values(date('now','localtime'),time('now','localtime'),'"+title1+"','"+classes+"','"+descs+"')");
    }

    public void deleteClasses(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from class");
    }

    public void deleteEvent(String date, String time, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from event where date = '"+date+"' and time = '"+time+"' and title = '"+title+"'");
    }

    public Cursor getBasicData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select lecture,administration,test,supervision,practical from basic where id=1",null);
        return res;
    }

    public Cursor getClassData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from class;",null);
        return res;
    }

    public Cursor getEventData(String todayDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from event where date = '"+todayDate+"' order by time desc", null);
        return res;
    }


}
