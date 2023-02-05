package com.example.fastelavnsbolle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fastelavnsbolle.Model.Admins;
import com.example.fastelavnsbolle.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText LogindMail, LogindPassword;
    private Button LogindKnap;
    private ProgressDialog PDialog;
    private String ParentDB = "Users";
    private String parentsDB = "Admins";
    private Button AdminLogind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogindKnap = (Button) findViewById(R.id.button_logInd2);
        LogindMail = (EditText) findViewById(R.id.editTextLogindEmailAddress);
        LogindPassword = (EditText) findViewById(R.id.editTextLogindPassword);
        AdminLogind = (Button) findViewById(R.id.adminButton);

        PDialog = new ProgressDialog(this);

        LogindKnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LogindBruger();

            }
        });

        AdminLogind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AdminBrugerLogind();


            }
        });
    }

    private void AdminBrugerLogind() {
        String email = LogindMail.getText().toString();
        String adgangskode = LogindPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Skriv venligst din email..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(adgangskode)) {
            Toast.makeText(this, "Skriv venligst din adgangskode..", Toast.LENGTH_SHORT).show();
        } else {
            PDialog.setTitle("Logind succesfuldt");
            PDialog.setMessage("Vent venligst");
            PDialog.setCanceledOnTouchOutside(false);
            PDialog.show();

            TilladAdgangForAdmin(email, adgangskode);
        }
    }

    private void TilladAdgangForAdmin(String email, String adgangskode) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(parentsDB).child(email).exists())
                {
                    Admins adminsData = snapshot.child(parentsDB).child(email).getValue(Admins.class);

                    if (adminsData.getEmail().equals(email))
                    {
                        if (adminsData.getAdgangskode().equals(adgangskode)) {
                            Toast.makeText(LoginActivity.this, "Logind er Godkendt", Toast.LENGTH_SHORT).show();
                            PDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            PDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Adgangskode er ikke korrekt", Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "En admin med denne " + email + " existerer ikke", Toast.LENGTH_SHORT).show();
                    PDialog.dismiss();

                }

            }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }



    private void LogindBruger() {
        String email = LogindMail.getText().toString();
        String adgangskode = LogindPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Skriv venligst din email..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(adgangskode)) {
            Toast.makeText(this, "Skriv venligst din adgangskode..", Toast.LENGTH_SHORT).show();
        } else {
            PDialog.setTitle("Logind succesfuldt");
            PDialog.setMessage("Vent venligst");
            PDialog.setCanceledOnTouchOutside(false);
            PDialog.show();

            TilladAdgangForBruger(email, adgangskode);
        }
    }

    private void TilladAdgangForBruger(String email, String adgangskode) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child(ParentDB).child(email).exists())
                {
                    Users usersData = snapshot.child(ParentDB).child(email).getValue(Users.class);

                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getAdgangskode().equals(adgangskode)) {
                            Toast.makeText(LoginActivity.this, "Logind er Godkendt", Toast.LENGTH_SHORT).show();
                            PDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            PDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Adgangskode er ikke korrekt", Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "En bruger med denne " + email + " existerer ikke", Toast.LENGTH_SHORT).show();
                    PDialog.dismiss();

                }

            }


            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}