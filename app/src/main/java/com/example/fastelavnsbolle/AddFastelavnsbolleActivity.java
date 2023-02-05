package com.example.fastelavnsbolle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddFastelavnsbolleActivity extends AppCompatActivity {


    private Button TilfoejFastelavnsbolleButton;

    private EditText NavnPaaFastelavnsbolle, TypeAfFastelavnsbolle, SmagFastelavnsbolle, BageriBagBollen;
    private ProgressDialog PDialog;

    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fastelavnsbolle);

        TilfoejFastelavnsbolleButton = (Button) findViewById(R.id.TilfoejBolleButton);

        NavnPaaFastelavnsbolle = (EditText) findViewById(R.id.fastelavnsbolleNavn);
        TypeAfFastelavnsbolle = (EditText) findViewById(R.id.fastelavnsbolleType);
        SmagFastelavnsbolle = (EditText) findViewById(R.id.fastelavnsbolleSmag);
        BageriBagBollen = (EditText) findViewById(R.id.fastelavnsbolleBageri);

        PDialog = new ProgressDialog(this);

        TilfoejFastelavnsbolleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpretFastelavnsbolle();
            }
        });
    }


        private void OpretFastelavnsbolle() {

        String BolleNavn = NavnPaaFastelavnsbolle.getText().toString();
        String BolleType = TypeAfFastelavnsbolle.getText().toString();
        String BolleSmag = SmagFastelavnsbolle.getText().toString();
        String BolleBageri = BageriBagBollen.getText().toString();

        if (TextUtils.isEmpty(BolleNavn)) {
                Toast.makeText(this, "Skriv venligst navn paa bolle..", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(BolleType)) {
                Toast.makeText(this, "Skriv venligst Type af bolle..", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(BolleSmag)) {
                Toast.makeText(this, "Skriv venligst Smag..", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(BolleBageri)) {
            Toast.makeText(this, "Skriv venligst Bageri..", Toast.LENGTH_SHORT).show();
            } else {
                PDialog.setTitle("Opretter fastelavnsbolle");
                PDialog.setMessage("Vent venligst");
                PDialog.setCanceledOnTouchOutside(false);
                PDialog.show();

                ValidateFastelavnsboller(BolleNavn, BolleType, BolleSmag, BolleBageri);
            }
        }

    private void ValidateFastelavnsboller(String bolleNavn, String bolleType, String bolleSmag, String bolleBageri) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!(snapshot.child("Fastelavnsboller").child(bolleNavn).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("bolleNavn", bolleNavn);
                    userdataMap.put("bolleType", bolleType);
                    userdataMap.put("bolleSmag", bolleSmag);
                    userdataMap.put("bolleBageri", bolleBageri);

                    RootRef.child("Fastelavnsboller").child(bolleNavn).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(AddFastelavnsbolleActivity.this, "Fastelavnsbolle oprettet succesfuldt", Toast.LENGTH_SHORT).show();
                                PDialog.dismiss();

                                Intent intent = new Intent(AddFastelavnsbolleActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                PDialog.dismiss();
                                Toast.makeText(AddFastelavnsbolleActivity.this, "Fejl, proev venligst igen", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else
                {
                    Toast.makeText(AddFastelavnsbolleActivity.this, "Denne " + bolleNavn + " existerer allerede", Toast.LENGTH_SHORT).show();
                    PDialog.dismiss();
                    Toast.makeText(AddFastelavnsbolleActivity.this, "Proev venligst igen med et andet navn", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddFastelavnsbolleActivity.this, AdminActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

}