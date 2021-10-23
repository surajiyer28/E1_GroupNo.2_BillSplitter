package com.firstapp.myapplication;


import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        b1 = (Button)findViewById(R.id.login);
        b2 = (Button)findViewById(R.id.register);
        ed1 = (EditText)findViewById(R.id.username);
        ed2 = (EditText)findViewById(R.id.password);
        db = FirebaseFirestore.getInstance();



    }

    public void login(View view) {
        db.collection("user").whereEqualTo("username",ed1.getText().toString()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> user = document.getData();
                                if(user.get("password").equals(ed2.getText().toString())){
                                    Intent homepage = new Intent(LoginActivity.this, GroupListActivity.class);
                                    startActivity(homepage);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

    }

    public void register(View view) {
        Intent RegisterPage = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(RegisterPage);

    }
}