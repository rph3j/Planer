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

        Button Register = findViewById((R.id.bntRegister));
        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerUser();
            }
        });

        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
     private void registerUser() {
         EditText etEmailAddress = findViewById(R.id.inputEmail);
         EditText etPassword = findViewById(R.id.inputPassword);
         EditText etLogin = findViewById(R.id.inputUsername);
         EditText etConfirm = findViewById(R.id.inputConfirmPassword);

         String email = etEmailAddress.getText().toString();
         String password = etPassword.getText().toString();
         String login = etLogin.getText().toString();
         String confirm = etConfirm.getText().toString();

         if (email.isEmpty() || password.isEmpty() || login.isEmpty() || confirm.isEmpty()) {
             Toast.makeText(this, "Wypelnij wszystkie pola", Toast.LENGTH_LONG).show();
             return;
         }
         if (password.length() < 8) {
             Toast.makeText(this, "Hasło powinno mieć conajmniej 8 znaków", Toast.LENGTH_LONG).show();
             return;
         }
         int czyWileka = 0;
         int czyMała = 0;
         int czyZankSpecjalny = 0;

         for(int i = 0; i < password.length();i++)
         {
             int v = password.charAt(i);
             if(v > 96 && v < 123)
                 czyMała = 1;
             else {
                 if (v > 64 && v < 91)
                     czyWileka = 1;
                 else
                     czyZankSpecjalny = 1;
             }
         }
         if (czyMała != 1 || czyWileka != 1 || czyZankSpecjalny != 1) {
             Toast.makeText(this, "Hasło powinno się składać z wilekich i małych liter oraz znaków specjalnych", Toast.LENGTH_LONG).show();
             return;
         }
         for(int i = 0; i < password.length();i++)
         {
             if(password.charAt(i) != confirm.charAt(i)) {
                 Toast.makeText(this, "Nie poprawne potwierdzenie hasła " + confirm + password, Toast.LENGTH_LONG).show();
                 return;
             }
         }

         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(RegisterActivity.this, "Użytkownik utworzony",
                                     Toast.LENGTH_LONG).show();
                             User user = new User(login,email);
                             FirebaseDatabase.getInstance().getReference("users")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user);
                                     Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                     startActivity(intent);
                                     finish();


                         } else {
                             Toast.makeText(RegisterActivity.this, "Nie udało się stworzyć użytkownika",
                                     Toast.LENGTH_LONG).show();
                         }
                     }
                 });
     }
}