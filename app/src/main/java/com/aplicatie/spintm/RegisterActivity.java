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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initializare baza de date Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Aceasta conditie if ne arata daca user-ul e sau nu autentificat
        if (mAuth.getCurrentUser() != null) {
            finish();
            return;
        }

        //buton inregistrare --> foloseste metoda registerUser() de mai jos
        Button registerBtn = findViewById(R.id.loginBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


        //TextView --> se inchide RegisterActivity si se deschide LoginActivity
        TextView textViewSwitchToLogin = findViewById(R.id.tvSwitchtoLogin);
        textViewSwitchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });

        //textview care te duce direct la Main Activity fara logare sau inregistrare
        TextView tvFaraCont = findViewById(R.id.tvFaraCont);
        tvFaraCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }

        private void registerUser() {
            EditText Nume = findViewById(R.id.Nume);
            EditText Email = findViewById(R.id.LoginEmail);
            EditText Parola = findViewById(R.id.LoginPassword);

            String nume = Nume.getText().toString();
            String email = Email.getText().toString();
            String password = Parola.getText().toString();

            if (nume.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vă rugăm să completați toate câmpurile!", Toast.LENGTH_LONG).show();
                return;
            }

            //daca user-ul s-a inregistrat corect, atunci datele lui se introduc automat in baza de date
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(nume, email, password);
                                FirebaseDatabase.getInstance().getReference("users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                  @Override
                                                                                  public void onComplete(@NonNull Task<Void> task) {
                                                                                      //inchide RegisterActivity --> deschide MainActivity
                                                                                      showMainActivity();
                                                                                  }
                                                                              }

                                        );
                            } else {
                                //daca nu sunt introduse corect datele --> error message
                                Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

        private void showMainActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //permite inchidere Register si deschidere Login
        private void switchToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        }

}
