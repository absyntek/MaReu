package com.example.mareu.ui.meeting_list;

import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.R;
import com.example.mareu.ui.new_meeting.NewMeetingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MeetingListActivity extends AppCompatActivity {

    private MeetingFragment mMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( view.getContext() , NewMeetingActivity.class );
                view.getContext().startActivity(intent);
            }
        });
        configureAndShowFragment();
    }

    private void configureAndShowFragment(){
        mMeetingFragment = (MeetingFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (mMeetingFragment == null){
            mMeetingFragment = new MeetingFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.container,mMeetingFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent("SORT_ACTION");
        switch (item.getItemId()){
            case R.id.sort_by_name:
                Log.e("room", "onOptionsItemSelected: ROOM");
                intent.putExtra("SORTBY", "ROOM");
                break;
            case R.id.sort_by_date:
                intent.putExtra("SORTBY", "DATE");
                break;

        }
        LocalBroadcastManager.getInstance(MeetingListActivity.this).sendBroadcast(intent);
        return super.onOptionsItemSelected(item);
    }
}
