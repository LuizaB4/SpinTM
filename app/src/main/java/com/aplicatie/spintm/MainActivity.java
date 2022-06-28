package com.aplicatie.spintm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button spinBtn;
    ImageView wheel;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            //buton logout
        setContentView(R.layout.activity_main);
        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        } );

        //initializare
        spinBtn = findViewById(R.id.spinBtn);
        wheel = findViewById(R.id.wheel);
        Handler hd = new Handler();

        //clasa Random
        Random random = new Random();

        // on click listener pentru butonul de spin
        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //buton spin disabled pentru useri atunci cand wheel se invarte
                spinBtn.setEnabled(false);

                // citeste valori aleatorii pentru numere intre 10 - 30
                int spin = random.nextInt(5)+10;

                 // imaginea wheel are 12 divizii deci rotatia inmultita cu 360/12 = 30 grade
                spin = spin * 30;

                // timer pentru fiecare miscare
                timer = new CountDownTimer(spin*14,1) {
                    @Override
                    public void onTick(long l) {
                        // rotatia propriu zisa a imaginii wheel
                        float rotation = wheel.getRotation() + 7;
                        wheel.setRotation(rotation);
                    }

                    @Override
                    public void onFinish() {
                        // dupa ce wheel s-a terminat de rotit, butonul spin devine enabled
                        spinBtn.setEnabled(true);

                    }
                }.start();


                hd.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(MainActivity.this, Rezultat.class);
                        startActivity(intent);


                    }

                }, 7000); // delay de 7 secunde
            }


            });
        ;

    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}