package com.example.ali.chattalk;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class CameraActivity extends AppCompatActivity {

    private Button mRecordView, mPlayView;
    private VideoView mVideoView;
    private int ACTIVITY_START_CAMERA_APP= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecordView = (Button) findViewById(R.id.recordButton);
        mPlayView = (Button) findViewById(R.id.playButton);
        mVideoView = (VideoView) findViewById(R.id.videoView);

        mRecordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callVideoApp = new Intent();
                callVideoApp.setAction(MediaStore.ACTION_VIDEO_CAPTURE);

                startActivityForResult(callVideoApp,ACTIVITY_START_CAMERA_APP);
            }
        });
        mPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVideoView.start();
            }
        });
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode== RESULT_OK){
            Uri videoUri = data.getData();
            mVideoView.setVideoURI(videoUri);
        }
    }
    @Override
    public boolean  onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent backMainTest = new Intent(this,SecimActivity.class);
        startActivity(backMainTest);
        finish();
    }
}
