package com.example.ali.chattalk;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class VideoCallActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    private static String API_KEY="46042312";
    private static String SESSION_ID="1_MX40NjA0MjMxMn5-MTUxNjM2MzE4NjYwNX5IVDNEK0lSaEhQZlZPeURLTHZDWHpYT2t-fg";
    private static String TOKEN="T1==cGFydG5lcl9pZD00NjA0MjMxMiZzaWc9ZGY1MDI1MDMwZWI2ZTk4ZmJkZmRlZWQyNTA5NTAxMjY0YzdiZGRmZDpzZXNzaW9uX2lkPTFfTVg0ME5qQTBNak14TW41LU1UVXhOak0yTXpFNE5qWXdOWDVJVkRORUswbFNhRWhRWmxaUGVVUkxUSFpEV0hwWVQydC1mZyZjcmVhdGVfdGltZT0xNTE2MzYzMjQxJm5vbmNlPTAuNTkyMTQyOTA3NTQ2NTY5MiZyb2xlPXB1Ymxpc2hlciZleHBpcmVfdGltZT0xNTE4OTU4ODg2JmluaXRpYWxfbGF5b3V0X2NsYXNzX2xpc3Q9";
    private static String LOG_TAG=MainActivity.class.getSimpleName();
    private static final int RC_SETTINGS = 123;

    private Session session;

    private FrameLayout PublisherContainer;
    private FrameLayout SubscriberContainer;

    private Publisher publisher;
    private Subscriber subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestPermissions();

        PublisherContainer = (FrameLayout) findViewById(R.id.publisher_container);
        SubscriberContainer = (FrameLayout) findViewById(R.id.subscriber_container);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
    @AfterPermissionGranted(RC_SETTINGS)  // İzinleri aldıktan sonra tekrar kontrol etmek için.
    private void requestPermissions(){
        String[] perm ={Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
        if(EasyPermissions.hasPermissions(this,perm)){

            session = new Session.Builder(this,API_KEY,SESSION_ID).build();
            session.setSessionListener(this);
            session.connect(TOKEN);
        }else{
            EasyPermissions.requestPermissions(this,"This app needs to access your Camera an Mic",RC_SETTINGS,perm);
        }
    }

    @Override
    public void onConnected(Session session) {

        publisher = new Publisher.Builder(this).build();
        publisher.setPublisherListener(this);

        PublisherContainer.addView(publisher.getView());
        session.publish(publisher);
    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

        if(subscriber==null){
            subscriber = new Subscriber.Builder(this,stream).build();
            session.subscribe(subscriber);

            SubscriberContainer.addView(subscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

        if(subscriber != null){
            subscriber=null;
            SubscriberContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

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

