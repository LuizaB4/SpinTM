package com.aplicatie.spintm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;

public class Rezultat extends AppCompatActivity {

    Button btnSwitchtoMain;
    ImageView Image;
    TextView Informatii;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);
        Button btnSwitchtoMain = (Button) findViewById(R.id.btnSwitchtoMain);

        btnSwitchtoMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Rezultat.this, MainActivity.class));
            }
        });

        int[] imageViewRezultat = new int[] {R.drawable.test1, R.drawable.test2, R.drawable.test3};

        // Get the ImageView
        
        setContent(R.layout.activity_rezultat);
        ImageView mImageView = (ImageView)findViewById(R.id.imageViewRezultat);

        // Get a random between 0 and images.length-1
        int imageId = (int)(Math.random() * imageViewRezultat.length);

        // Set the image
        mImageView.setBackgroundResource(imageViewRezultat[imageId]);

    }

    private void setContent(int activity_rezultat) {
    }

}
