package com.firstapp.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;
    int PICTURE = 100;
    Drawable d = null;
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
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageview);


    }

    public void register(View view) {
        String name = na.getText().toString();
        String username = uname.getText().toString();
        String emailid = email.getText().toString();
        String pass = pwd.getText().toString();
        String cpass = cpwd.getText().toString();
        Drawable img = imageView.getDrawable();
        if(name.equals("") || username.equals("") || emailid.equals("") || pass.equals("") || cpass.equals("")) {
            Toast.makeText(getApplicationContext(), "All fields are required.",Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(cpass)){
            Toast.makeText(getApplicationContext(), "Passwords do not match.",Toast.LENGTH_SHORT).show();
        }

        else if(img == null){
            Toast.makeText(getApplicationContext(), "Profile image is required.",Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(getApplicationContext(), "Registered Successfully.",Toast.LENGTH_SHORT).show();
            Intent RegisterPage = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(RegisterPage);
        }
    }

    public void getImage(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }
}