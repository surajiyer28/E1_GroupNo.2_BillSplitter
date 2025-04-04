package com.firstapp.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class CreateNewGroupActivity extends AppCompatActivity {
    private EditText editText;

    private void saveGroup() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_group_activity);
        Toolbar toolbar = findViewById(R.id.createNewGroupToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Create New Group");

        editText = findViewById(R.id.createNewGroupName);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        finish(); // initiate finish if user clicks on back button
        return true;
    }

    public void saveGroup(View view) {
        final String name = editText.getText().toString();

        // check if the name is empty
        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
            return;
        }
        GroupViewModel groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        GroupEntity group = new GroupEntity(name); // create group object that needs to be inserted into the database
        group.gCurrency = "USD-($)"; // set default currency

        // if database already contains group object return and do not save
        List<GroupEntity> groups = groupViewModel.getAllGroupsNonLiveData();
        for(GroupEntity item:groups) {
            if(item.gName.equals(group.gName)) {
                Toast.makeText(this, "Group already exists", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // else store the group object in database
        groupViewModel.insert(group);
        Toast.makeText(this, "Group created successfully", Toast.LENGTH_SHORT).show();
    }
}