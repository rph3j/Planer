package com.example.planer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            finish();
            return;
        }

        Button Register = findViewById((R.id.Register));
        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerUser();
            }
        });

        FloatingActionButton textViewSwitchToLogin = findViewById(R.id.tvSwitchToLogin);
        textViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });
    }
     private void registerUser(){
         EditText etEmailAddress = findViewById(R.id.etEmailAddress);
         EditText etPassword = findViewById(R.id.etPassword);
         EditText etLogin = findViewById(R.id.etLogin);

         String email = etEmailAddress.getText().toString();
         String password = etPassword.getText().toString();
         String login = etLogin.getText().toString();

         if(email.isEmpty() || password.isEmpty() || email.isEmpty()){
             Toast.makeText(this, "Wypelnij wszystkie pola", Toast.LENGTH_LONG).show();
             return;
         }

         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             User user = new User(login, email);
                             FirebaseDatabase.getInstance().getReference("users")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     showMainActivity();
                                 }

                             });
                         } else {
                             Toast.makeText(RegisterActivity.this, "Stworzono już użytkownika o takim loginie/e-mailu",
                                     Toast.LENGTH_LONG).show();

                         }
                     };
                 });
     }

     private void showMainActivity(){
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
         finish();
     }

    private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}