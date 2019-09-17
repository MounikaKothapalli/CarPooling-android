package com.project.mounika.shareyourride;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ViewTripsActivity extends AppCompatActivity {
    DBHelper db ;
    Button cancel;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;

    HashMap<String, List<ListItem>> listDataChild;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtrips);
        db= new DBHelper(this);
         cancel=findViewById(R.id.btnCancelledView);
        expListView = (ExpandableListView) findViewById(R.id.EXPTrips);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        try {
            prepareListData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView
                .setOnGroupClickListener(new  ExpandableListView.OnGroupClickListener() {

                                             @Override
                                             public boolean onGroupClick(ExpandableListView parent, View v,
                                                                         int groupPosition,
                                                                         long id) {

                                                 if(!expListView.isGroupExpanded(groupPosition))
                                                 expListView.expandGroup(groupPosition);
                                                 else
                                                     expListView.collapseGroup(groupPosition);
                                                 return true;
                                             }
                                         });

        expListView
                .setOnChildClickListener(new  ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(
                            ExpandableListView parent, View v,
                            int groupPosition, int childPosition,
                            long id) {
                      String Childtext=  listDataChild.get(listDataHeader.get(groupPosition))
                                .get(childPosition).getLstItmLabel();
                        Intent i = new Intent(getApplicationContext(), CancelActivity.class);
                        i.putExtra("childText",Childtext);
                        i.putExtra("username",username);
                        i.putExtra("groupPosition",String.valueOf(groupPosition));
                        startActivity(i);
                     return true;
                    }
                });
    }
    private void prepareListData() throws ParseException {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ListItem>>();

        String[][] arraylist=db.Select("Select * from DriverTrips where   UPPER(Email)='"+username.toUpperCase()+"' Order by Date Desc");
        ArrayList<ListItem> ridesOffered = new ArrayList<ListItem>();
        ArrayList<ListItem> ridesBooked = new ArrayList<ListItem>();
        String[][] arraylistride=db.Select("Select * from RiderTrips where   UPPER(Email)='"+username.toUpperCase()+"' Order by Date desc");

        listDataHeader.add("Rides Offered ("+arraylist.length+")");
        listDataHeader.add("Rides Booked ("+arraylistride.length+")");
        ArrayList<Trips> t=new ArrayList<Trips>();
        boolean check=true;
        for(int i = 0 ; i < arraylist.length;i++)
        {
            if(!arraylist[i][9].toUpperCase().equals("Cancelled".toUpperCase())){
                check=false;
            }
            else
                check=true;
            if(!checkMyDate(arraylist[i][5])&&!check)
                arraylist[i][9]="Trip Done";
            t.add(new Trips(arraylist[i][0],arraylist[i][1],arraylist[i][4],arraylist[i][5],arraylist[i][9],"Rides Offered"));

            ridesOffered.add(new ListItem("ID:"+arraylist[i][11]+"\n Source:"+arraylist[i][0]+"\n Destination:"+arraylist[i][1]+"\n Car No:"+arraylist[i][4]+"\n Travel Date:"+arraylist[i][5].trim()+"\n Status:"+arraylist[i][9].trim()+"\n No.Of Slots:"+arraylist[i][8].trim()+"\nActivity:Ride Offered",check));


        }
        for(int j = 0 ; j < arraylistride.length;j++)
        {

            if(!arraylistride[j][7].toUpperCase().equals("Cancelled".toUpperCase())){
                check=false;
            }
            else
            check=true;
            if(!checkMyDate(arraylistride[j][5])&&!check)
                arraylistride[j][7]="Trip Done";
            t.add(new Trips(arraylistride[j][0],arraylistride[j][1],arraylistride[j][4],arraylistride[j][5],arraylistride[j][7],"Rides Booked"));
            ridesBooked.add(new ListItem("ID:"+arraylistride[j][11]+"\n Source:"+arraylistride[j][0]+"\n Destination:"+arraylistride[j][1]+"\n CarNo:"+arraylistride[j][4]+"\n Travel Date:"+arraylistride[j][5].trim()+"\n Status:"+arraylistride[j][7]+"\n No.of Slots:"+arraylistride[j][9].trim()+"\nActivity:Ride Booked",check));
        }



        listDataChild.put(listDataHeader.get(0), ridesOffered);
        listDataChild.put(listDataHeader.get(1), ridesBooked);

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
            Intent intent = new Intent(ViewTripsActivity.this, HomeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(ViewTripsActivity.this, MainActivity.class);
            startActivity(intent);
        }
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
