package com.example.fastelavnsbolle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    private Button LogudKnap;
    private Button FindBageriKnap;
    private Button OmOsKnappen;
    private ImageView InspirationTil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LogudKnap = (Button) findViewById(R.id.logud_Knap);

        LogudKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        InspirationTil = (ImageView) findViewById(R.id.Inspiration);
        InspirationTil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, UserInspirationActivity.class);
                startActivity(intent);

            }
        });

        FindBageriKnap = (Button) findViewById(R.id.FindBageriButton);
        FindBageriKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, FindBageriActivity.class);
                startActivity(intent);

            }
        });

        OmOsKnappen = (Button) findViewById(R.id.OmOsbutton2);
        OmOsKnappen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, OmOsActivity.class);
                startActivity(intent);

            }
        });

    }
}