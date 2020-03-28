package com.example.bazil.teacherslist;


import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class EventModel {
    String title, date, time, subTitle, describe;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubTitle() { return subTitle; }

    public void setSubTitle(String subTitle) { this.subTitle = subTitle; }

    public String getDescribe() { return describe; }

    public void setDescribe(String describe) { this.describe = describe; }

    //METHOD TO ATTACH ALL THE DATA TO A LIST
    public static List<EventModel> getObjectData(Cursor res) {
        List<EventModel> datalist = new ArrayList<>();

        while (res.moveToNext()) {
            //System.out.println("inside for loop for getting data");
            EventModel em = new EventModel();
            em.setDate(res.getString(0));
            em.setTime(res.getString(1));
            em.setTitle(res.getString(2));
            em.setSubTitle(res.getString(3));
            em.setDescribe(res.getString(4));

            datalist.add(em);
        }
        return datalist;
    }
}