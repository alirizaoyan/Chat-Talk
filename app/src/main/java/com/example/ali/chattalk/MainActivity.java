package com.example.ali.chattalk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null){
            //profil activity
            finish();
            startActivity(new Intent(getApplicationContext(),SecimActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonRegister=(Button) findViewById(R.id.buttonRegister);

        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Lütfen email girin.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Lütfen şifre girin.",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Kaydediliyor...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //kullanıcı kaydı başarılı.
                    finish();
                    startActivity(new Intent(getApplicationContext(),SecimActivity.class));
                }else {
                    Toast.makeText(MainActivity.this,"Kayıt başarısız",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
