package com.firstapp.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText na,uname,email,pwd,cpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        na = (EditText) findViewById(R.id.name1);
        uname = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email_id);
        pwd = (EditText) findViewById(R.id.password);
        cpwd = (EditText) findViewById(R.id.cpassword);

    }

    public void register(View view) {
        String name = na.getText().toString();
        String username = uname.getText().toString();
        String emailid = email.getText().toString();
        String pass = pwd.getText().toString();
        String cpass = cpwd.getText().toString();

        if(name.equals("") || username.equals("") || emailid.equals("") || pass.equals("") || cpass.equals("")) {
            Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(cpass)){
            Toast.makeText(getApplicationContext(), "Passwords do not match.",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Registered successfully",Toast.LENGTH_SHORT).show();
            Intent RegisterPage = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(RegisterPage);
        }
    }
}