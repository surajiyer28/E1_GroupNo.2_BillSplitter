package com.firstapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawer;
    public static final String EXTRA_TEXT_GNAME = "com.firstapp.myapplication.EXTRA_TEXT_GNAME";
    private List<GroupEntity> groupNames = new ArrayList<>();
    private GroupListActivityViewAdapter adapter;
    private GroupViewModel groupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set toolbar
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
           getSupportActionBar().setElevation(0); // removes shadow/elevation between toolbar and status bar
        }
        setTitle("");
         //set drawer
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // prepare recycler view
        RecyclerView recyclerView = findViewById(R.id.group_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        // second parameter below -> attach this activity as a listener to every item in the group list
        adapter = new GroupListActivityViewAdapter(GroupListActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        groupViewModel = ViewModelProviders.of(this).get(GroupViewModel.class);
        groupViewModel.getAllGroups().observe(this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(List<GroupEntity> groupEntities) {
                // Recreate the recycler view by notifying adapter with the changes
                // here groupEntities is the list of current items in the groupList
                groupNames = groupEntities;
                adapter.saveToList(groupEntities);
                // check if there are no groups
                TextView emptyListMsgTV = (TextView) findViewById(R.id.noGroupsMsg);
                if (adapter.getItemCount() == 0) {
                    emptyListMsgTV.setText("No groups found :(\nPlease create a new group");
                }
            }
        });



        adapter.setOnItemClickListener(new GroupListActivityViewAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(int position) {
                // get group name of the item the user clicked on from groupNames array
                String gName = groupNames.get(position).gName;

                Intent intent = new Intent(GroupListActivity.this,HandleOnGroupClickActivity.class);
                intent.putExtra(EXTRA_TEXT_GNAME,gName);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // method for handling clicks on our buttons
    @Override
    public void onClick(View v) {
        Intent intent;
        intent = new Intent(this, CreateNewGroupActivity.class);startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.createNewGroup) {
            Intent intent;
            intent = new Intent(this, CreateNewGroupActivity.class);startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }




    @Override
    public void onPause() {
        if(adapter.multiSelect) {
            adapter.actionMode.finish();
            adapter.multiSelect = false;
            adapter.selectedItems.clear();
            adapter.notifyDataSetChanged();
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            // close the drawer if user clicks on back button while drawer is open
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}