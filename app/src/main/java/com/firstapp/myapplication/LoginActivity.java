package com.firstapp.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        b1 = (Button)findViewById(R.id.login);
        b2 = (Button)findViewById(R.id.register);
        ed1 = (EditText)findViewById(R.id.username);
        ed2 = (EditText)findViewById(R.id.password);



    }

    public void login(View view) {
        if(ed1.getText().toString().equals("aanch") &&
                ed2.getText().toString().equals("aanch")) {
            Toast.makeText(getApplicationContext(),
                    "Login Success",Toast.LENGTH_SHORT).show();
            Intent Login = new Intent(LoginActivity.this, GroupListActivity.class);
            startActivity(Login);

        }else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();




        }
    }

    public void register(View view) {
        Intent RegisterPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(RegisterPage);

    }
}