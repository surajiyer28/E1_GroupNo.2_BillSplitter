package com.firstapp.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText na,uname,email,pwd,cpwd;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        na = (EditText) findViewById(R.id.name1);
        uname = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email_id);
        pwd = (EditText) findViewById(R.id.password);
        cpwd = (EditText) findViewById(R.id.cpassword);



        db = FirebaseFirestore.getInstance();


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
            Map<String,Object> user = new HashMap<>();
            user.put("name",name);
            user.put("username",username);
            user.put("email",emailid);
            user.put("password",pass);

            db.collection("user")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Registration successful",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Registration failed",Toast.LENGTH_SHORT).show();

                }
            });



        }
    }


}