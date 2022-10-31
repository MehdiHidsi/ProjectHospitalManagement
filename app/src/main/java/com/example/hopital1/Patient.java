package com.example.hopital1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.Buffer;

public class Patient extends AppCompatActivity {

    private Button btnout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        btnout=findViewById(R.id.signOutBtn);
        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Patient.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}