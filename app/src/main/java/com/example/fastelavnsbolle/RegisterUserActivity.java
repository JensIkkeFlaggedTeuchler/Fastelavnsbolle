package com.example.fastelavnsbolle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterUserActivity extends AppCompatActivity {

    private Button OpretBruger2;
    private EditText OpretBrugerNavn, OpretBrugerEmailAdress, OpretBrugerPassword;
    private ProgressDialog PDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        OpretBruger2 = (Button) findViewById(R.id.button_opretBruger2);
        OpretBrugerNavn = (EditText) findViewById(R.id.editTextOpretBrugerNavn);
        OpretBrugerEmailAdress = (EditText) findViewById(R.id.editTextOpretBrugerEmailAddress);
        OpretBrugerPassword = (EditText) findViewById(R.id.editTextOpretBrugerPassword);

        PDialog = new ProgressDialog(this);

        OpretBruger2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpretBruger();

            }
        });
    }

    private void OpretBruger() {

        String navn = OpretBrugerNavn.getText().toString();
        String email = OpretBrugerEmailAdress.getText().toString();
        String adgangskode = OpretBrugerPassword.getText().toString();

        if (TextUtils.isEmpty(navn)) {
            Toast.makeText(this, "Skriv venligst dit brugernavn..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Skriv venligst din email..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(adgangskode)) {
            Toast.makeText(this, "Skriv venligst din adgangskode..", Toast.LENGTH_SHORT).show();
        } else {
            PDialog.setTitle("Opretter bruger");
            PDialog.setMessage("Vent venligst");
            PDialog.setCanceledOnTouchOutside(false);
            PDialog.show();
            
            ValidateUser(navn, email, adgangskode);
        }
    }

    private void ValidateUser(String navn, String email, String adgangskode) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (!(snapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("navn", navn);
                    userdataMap.put("email", email);
                    userdataMap.put("adgangskode", adgangskode);

                    RootRef.child("Users").child(email).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(RegisterUserActivity.this, "Bruger oprettet succesfuldt", Toast.LENGTH_SHORT).show();
                                PDialog.dismiss();

                                Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                PDialog.dismiss();
                                Toast.makeText(RegisterUserActivity.this, "Fejl, proev venligst igen", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else
                {
                    Toast.makeText(RegisterUserActivity.this, "Denne " + email + " existerer allerede", Toast.LENGTH_SHORT).show();
                    PDialog.dismiss();
                    Toast.makeText(RegisterUserActivity.this, "Proev venligst igen med en anden email", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

}