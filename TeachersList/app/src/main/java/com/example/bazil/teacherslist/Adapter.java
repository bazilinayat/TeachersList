package com.example.bazil.teacherslist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Adapter extends RecyclerView.Adapter<Adapter.Item>{

    //yesNo HAS FOUR ELEMENTS ACCORDING TO THE ME
    //0 - LECTURE, 1 - ADMIN, 2- TEST, 3 - SUPERVISION
    //WE CAN ADD MORE ACCORDING TO OUR NEED
    String[] yesNo = {"no","no","no","no","no"};
    String[] classes = {"F.Y.B.Sc.", "S.Y.B.Sc.", "T.Y.B.Sc.", "M.Sc.I", "M.Sc.II"};
    boolean[] checkedItems = {false, false, false, false, false};

    Context context;
    String[] items = {"Do you take Lectures?","Do you do Administrative work?","Do you take Tests?","Do you go for Supervisions?","Do yoiu take Practicals?"};;

    DBOperations dbo;

    public Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.custom_row,viewGroup,false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull Item item, int i) {
        item.textView.setText(items[i]);
        item.mCardView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public View mCardView;
        public Item(View itemView){
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int i = (int) view.getTag();
            //Toast.makeText(view.getContext(),Integer.toString(i),Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("");
            builder.setMessage(items[i]);

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing, but close the dialog
                    switch(i){
                        case 0: yesNo[0]="yes";
                            dbo = new DBOperations(context);
                            dbo.deleteClasses();

                            //IF THE USER CLICKS YES FOR LECTURES THEN WE ASK HIM/HER LECTURES OF WHAT CLASSES
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Choose some animals");

                            // add a checkbox list
                            builder1.setMultiChoiceItems(classes, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    // user checked or unchecked a box
                                }
                            });
                            // add OK and Cancel buttons
                            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // user clicked OK
                                    if(checkedItems[0]== false && checkedItems[1]== false && checkedItems[2]== false && checkedItems[3]== false && checkedItems[4]== false){
                                        Toast.makeText(context,"SELECT AT LEAST ONE CLASS!!",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            builder1.setNegativeButton("Cancel", null);

                            // create and show the alert dialog
                            AlertDialog dialog1 = builder1.create();
                            dialog1.show();
                            break;
                        case 1: yesNo[1]="yes";
                            break;
                        case 2: yesNo[2]="yes";
                            break;
                        case 3: yesNo[3]="yes";
                         break;
                        case 4: yesNo[4]="yes";
                            break;
                    }

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    switch(i){
                        case 0: yesNo[0]="no";
                            break;
                        case 1: yesNo[1]="no";
                            break;
                        case 2: yesNo[2]="no";
                            break;
                        case 3: yesNo[3]="no";
                            break;
                        case 4: yesNo[4]="no";
                            break;
                    }
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    //on click stuff
    public void yesNoMessage(){
    }


    //GETTERS SO I CAN GET THEM IN MAIN ACTIVITY


    public String[] getYesNo() {
        return yesNo;
    }

    public String[] getClasses() {
        return classes;
    }

    public boolean[] getCheckedItems() {
        return checkedItems;
    }
}
