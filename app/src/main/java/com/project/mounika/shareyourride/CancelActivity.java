package com.project.mounika.shareyourride;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CancelActivity extends AppCompatActivity {
    DBHelper db;
    String childText,username,groupPosition;
    String[] splitData;
    Bundle extras=new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);
        db = new DBHelper(this);

        extras = getIntent().getExtras();
        username = extras.getString("username");
        childText = extras.getString("childText");
        groupPosition=extras.getString("groupPosition");
        splitData= childText.split("\n");
        TextView viewText=findViewById(R.id.text_ViewRideDetails);
        viewText.setText(childText);
        Button cancel=findViewById(R.id.btnCancelView);

        if(checkMyDate(splitData[4].split(":")[1]) &&splitData[5].split(":")[1].toUpperCase().equals("ACTIVE") ){
        cancel.setVisibility(View.VISIBLE);
        }
        else
            cancel.setVisibility(View.GONE);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
OpinionBox("Are You sure you want to cancel this ride.");




            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.btnGoToHome:
                GoToHome("home");
                break;
            case R.id.btnlogout:
                GoToHome("logout");
                break;

        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }
    public void GoToHome(String from){
        if(from.equals("home")) {
            Intent intent = new Intent(CancelActivity.this, HomeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(CancelActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    public void OpinionBox(String str) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        if(groupPosition.equals("0")) {
                            db.Update("update DriverTrips set Status='Cancelled' where ID="+splitData[0].split(":")[1]);
                            db.Update("update RiderTrips set Status='Cancelled' where ID="+splitData[0].split(":")[1]);
                        }
                        else {
                            db.Update("update RiderTrips set Status='Cancelled' where ID="+splitData[0].split(":")[1] );
                            String[][] arraylist=db.Select("Select * from DriverTrips where   ID="+splitData[0].split(":")[1]+" Order by Date Desc");
                            if(arraylist.length>0)
                                db.Update("update DriverTrips set Slots='"+(Integer.parseInt(arraylist[0][8])+1)+"' where ID="+splitData[0].split(":")[1] );

                        }
                        Intent i = new Intent(getApplicationContext(), ViewTripsActivity.class);
                        i.putExtra("username",username);
                        startActivity(i);
                        break;
                    }
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str).setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
    public boolean checkMyDate(String myTravelDate){
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
        String todayDate = formatDate.format(new Date());
        Date  d=new Date();
        try {
            d=new SimpleDateFormat("MM/dd/yyyy").parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date1=new Date();
        try {
            date1= new SimpleDateFormat("MM/dd/yyyy").parse(myTravelDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(date1.compareTo(d)>=0 ){
            return  true;
        }
        return false;
    }
}