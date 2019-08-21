package com.project.mounika.shareyourride;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    EditText username,passwd;
    ArrayList<User> myitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DBHelper(this);
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);
        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        username=findViewById(R.id.userName);
        passwd=findViewById(R.id.LoginPass);
        username.setText("");
        passwd.setText("");
        registerScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String[][]  array_list = db.Select("Select * from User U where UPPER(U.Email)= '"+username.getText().toString().trim().toUpperCase()+"'");
                if(array_list.length==0){
                    AlertBox("Given User is not registered.Please Register before login.");
                }
                else{
                    if(array_list[0][3].toUpperCase().equals(username.getText().toString().toUpperCase().trim())&& array_list[0][4].equals(passwd.getText().toString().trim())) {
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        i.putExtra("username",username.getText().toString().trim());
                        startActivity(i);
                    }
                    else{
                        AlertBox("Wrong Username or Password.");
                    }
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
}
