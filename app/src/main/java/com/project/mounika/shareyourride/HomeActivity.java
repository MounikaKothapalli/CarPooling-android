package com.project.mounika.shareyourride;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle extras=new Bundle();
         extras = getIntent().getExtras();
         username = extras.getString("username");
        TextView search=(TextView) findViewById(R.id.searchtext);
        TextView offer=(TextView) findViewById(R.id.offerText);
        TextView view=(TextView) findViewById(R.id.ViewTrips);
        TextView update=(TextView) findViewById(R.id.updateMyDetails);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Search A ride screen
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        offer.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Navigating to Ride Offer screen
                Intent intent2 = new Intent(getApplicationContext(), OfferActivity.class);
                intent2.putExtra("username",username);
                startActivity(intent2);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Navigating to Update screen
                Intent i = new Intent(getApplicationContext(), UpdateActivity.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Navigating to View Trips screen
                Intent i = new Intent(getApplicationContext(), ViewTripsActivity.class);
                i.putExtra("username",username);
                startActivity(i);
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
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
