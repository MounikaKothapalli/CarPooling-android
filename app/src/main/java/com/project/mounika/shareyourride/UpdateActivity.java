package com.project.mounika.shareyourride;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {
String username="";
    EditText nam,Phn,Pwd;
    TextView email;
    DBHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        Button update = findViewById(R.id.btnUpdate);
        Button cancel = findViewById(R.id.btn_UpdateCancel);
        db= new DBHelper(this);
        nam=findViewById(R.id.editNameUpdate);
        email= findViewById(R.id.txtuser);
        Phn=findViewById(R.id.editphn);
        Pwd=findViewById(R.id.edit_pwd);
        email.setText(username);
        String[][]  array_list = db.Select("Select * from User U where UPPER(U.Email)= '"+username.toUpperCase()+"'");
        Phn.setText(array_list[0][2]);
        nam.setText(array_list[0][1]);
        Pwd.setText(array_list[0][4]);
        db= new DBHelper(this);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Phn.setText("");
                nam.setText("");
                Pwd.setText("");
            }

        });
        update.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                db.Update("Update User set Name='"+nam.getText().toString().trim()+"',PhoneNumber='"+Phn.getText().toString().trim()+"',Password='"+Pwd.getText().toString().trim()+"' Where Email='"+username+"'" );
            AlertBox("Your Details Updated Successfully");
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
            Intent intent = new Intent(UpdateActivity.this, HomeActivity.class);
            intent.putExtra("username",username);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
