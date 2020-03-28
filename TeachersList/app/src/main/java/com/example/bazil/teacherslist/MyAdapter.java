package com.example.bazil.teacherslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<EventModel> objectList;
    private LayoutInflater inflater;

    public MyAdapter(Context context,List<EventModel> objectList){
        //System.out.println("in constructor of my adapter");
        inflater = LayoutInflater.from(context);
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //System.out.println("in on create view holder of my adapter");
        View view = inflater.inflate(R.layout.list_event,viewGroup,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //System.out.println("in on bind view holder of my adapter");
        EventModel current = objectList.get(i);
        myViewHolder.setData(current,i);
        myViewHolder.mCardView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title,date,time;
        private String sub, describe;
        private ImageView icDelete;
        private int position;
        private EventModel currentObject;
        DBOperations dbo;

        public View mCardView;


        public MyViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textTitle);
            date = (TextView) itemView.findViewById(R.id.textDate);
            time = (TextView) itemView.findViewById(R.id.textTime);
            icDelete = (ImageView) itemView.findViewById(R.id.deleteImage);
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            mCardView.setOnClickListener(this);
            icDelete.setOnClickListener(this);
        }

        public void setData(EventModel currentObject, int i) {
            this.title.setText(currentObject.getTitle());
            this.date.setText("Date : "+currentObject.getDate());
            this.time.setText("Time : "+currentObject.getTime());
            sub = currentObject.getSubTitle();
            describe = currentObject.getDescribe();
            this.position = i;
            this.currentObject = currentObject;
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            switch(view.getId()){
                case R.id.cardView : final int i = (int) view.getTag();
                    //Toast.makeText(view.getContext(),Integer.toString(i),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, LookEvent.class);
                    Bundle b = new Bundle();
                    b.putString("title",title.getText().toString()); //Your title
                    b.putString("date",date.getText().toString());
                    b.putString("time",time.getText().toString());
                    b.putString("subtitle",sub);
                    b.putString("desc",describe);
                    //System.out.println("desc : "+ describe);

                    intent.putExtras(b); //Put your id to your next Intent
                    context.startActivity(intent);
                    break;
                case R.id.deleteImage:
                    //Toast.makeText(view.getContext(),"clicked on delete image",Toast.LENGTH_SHORT).show();
                    dbo = new DBOperations(view.getContext());
                    dbo.deleteEvent(currentObject.getDate(),currentObject.getTime(),currentObject.getTitle());
                    objectList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, objectList.size());
                    break;
            }
        }

    }
}
