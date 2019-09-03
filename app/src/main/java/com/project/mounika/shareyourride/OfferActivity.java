package com.project.mounika.shareyourride;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import android.widget.TimePicker;

import java.util.Calendar;

public class OfferActivity extends AppCompatActivity {
    DBHelper db;
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        final EditText sourceText = findViewById(R.id.txtSource);
        final EditText destinationText = findViewById(R.id.txtDest);
        final EditText timeText = findViewById(R.id.editOfr_Time);
        final EditText phText = findViewById(R.id.txtPhone);
        final EditText vehicle = findViewById(R.id.vehicleValu);
        final EditText price = findViewById(R.id.txtPrice);
        final EditText time = findViewById(R.id.time);
        final EditText CarNo = findViewById(R.id.txtCar);
        final Button offerRide = findViewById(R.id.btnOfr);
        final Button cancel = findViewById(R.id.btnCancel);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        db= new DBHelper(this);
        timeText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(OfferActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker myDatePick, int getyear, int getMonth, int getDay) {
                                timeText.setText((getMonth+1) + "/" + getDay + "/" + getyear);
                            }
                        },year,month,dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }

        });
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                int hour = now.get(java.util.Calendar.HOUR_OF_DAY);
                int minute = now.get(java.util.Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker getTimePck, int getHour, int getMin) {
                        StringBuffer strBuf = new StringBuffer();
                        strBuf.append("Your select time is ");
                        strBuf.append(getHour);
                        strBuf.append(":");
                        strBuf.append(getMin);

                        time.setText(getHour+":"+getMin);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(OfferActivity.this, onTimeSetListener, hour, minute, true);

                timePickerDialog.show();
            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sourceText.setText("");
                destinationText.setText("");
                timeText.setText("");
                phText.setText("");
                vehicle.setText("");
                price.setText("");
                time.setText("");
                CarNo.setText("");
            }

        });

        offerRide.setOnClickListener(new View.OnClickListener() {
            String[][]  array_list = db.Select("Select * from User U where UPPER(U.Email)= '"+username.toUpperCase()+"'");

            @Override
            public void onClick(View v) {
                if (!validate(sourceText) || !validate(destinationText) || !validate(timeText)
                        || !validate(phText) || !validate(time) ||!validate(CarNo)||!validate(vehicle)||!validate(price) )
                    return;
                else if (sourceText.getText().toString().trim().equals(destinationText.getText().toString().trim())){
                    AlertBox("Source and Destination must be different");
                    return;
                }
                else{
                    db.Insert("INSERT INTO `DriverTrips`(`Source`,`Destination`,`Email`,`Name`,`CarNo`,`Date`,`Time`,`Vehicle`,`Slots`,`Price`,`Status`) " +
                            "VALUES ('"+sourceText.getText().toString().trim()+"','"+destinationText.getText().toString().trim()+"','"+ array_list[0][3] +"','"+ array_list[0][1] +"'" +
                            ",'"+ CarNo.getText().toString().trim() +"','"+timeText.getText().toString().trim()+"','"+time.getText().toString().trim()+"','"+vehicle.getText().toString().trim()+"'," +
                            "'"+phText.getText().toString().trim()+"','"+price.getText().toString().trim()+"','Active');\n");
                    AlertBox("Ride Created Successfully☺");

                }
            }
        });
    }
    public void AlertBox(String str) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
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
        builder.setMessage(str).setPositiveButton("OK", dialogClickListener).show();
    }
    boolean validate (EditText editText)
    {
        String txt = editText.getText().toString().trim();
        if (txt.isEmpty() || txt.equals(null))
        {
            editText.setError("Please Enter a value");
            return false;
        }
        else
        {
            editText.setError(null);
            return true;
        }
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
            Intent intent = new Intent(OfferActivity.this, HomeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(OfferActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
