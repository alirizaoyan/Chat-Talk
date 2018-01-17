package com.example.ali.chattalk;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;

    private DatabaseReference databaseReference;

    private EditText editTextName,editTextAdress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextAdress=(EditText)findViewById(R.id.editTextAdress);
        editTextName=(EditText)findViewById(R.id.editTextName);
        buttonSave=(Button)findViewById(R.id.buttonSave);

        FirebaseUser user= firebaseAuth.getCurrentUser();

        textViewUserEmail=(TextView)findViewById(R.id.textViewUserEmail);

        textViewUserEmail.setText("Merhaba "+ user.getEmail());

        buttonLogout=(Button)findViewById(R.id.buttonLogout);

        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String add = editTextAdress.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,add);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this,"Kaydedildi",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        if(view==buttonSave){
            saveUserInformation();
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
