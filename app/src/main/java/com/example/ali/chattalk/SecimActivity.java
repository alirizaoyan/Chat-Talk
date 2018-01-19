package com.example.ali.chattalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.InCallService;
import android.view.Menu;
import android.view.MenuItem;

public class SecimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_camera) {
            finish();
            startActivity(new Intent(getApplicationContext(),CameraActivity.class));
        }
        if(id== R.id.action_photo){
            finish();
            startActivity(new Intent(getApplicationContext(),PhotoActivity.class));
        }
        if (id== R.id.action_profile){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        if(id==R.id.action_video_call){
            finish();
            startActivity(new Intent(getApplicationContext(), VideoCallActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
