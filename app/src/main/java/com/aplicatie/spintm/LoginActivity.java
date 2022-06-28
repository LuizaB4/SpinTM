package com.aplicatie.spintm;

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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initializare baza de date Firebase Auth
        //daca user-ul e logat --> inchidere activitate
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

        Button loginBtn = findViewById(R.id.loginBtn);
        // apasare buton --> autentificare user
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });
        //inchidere login --> deschidere register
        TextView tvSwitchtoRegister = findViewById(R.id.tvSwitchtoRegister);
        tvSwitchtoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToRegister();
            }
        });
    }

    private void authenticateUser(){
        EditText LoginEmail = findViewById(R.id.LoginEmail);
        EditText LoginPassword = findViewById(R.id.LoginPassword);

        String email = LoginEmail.getText().toString();
        String password = LoginPassword.getText().toString();

        //daca valorile introduse sunt incorecte --> error message
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vă rugăm să completați toate câmpurile!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToRegister() {
        Intent intent = new  Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }
}