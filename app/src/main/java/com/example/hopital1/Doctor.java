package com.example.hopital1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Doctor extends AppCompatActivity {
    private ImageView spe;
    TextView textView;
    ImageView rImage;
    FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference daatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Doctor");
        setContentView(R.layout.activity_doctor);
         spe=findViewById(R.id.imageViewkdkdkd8);
        TextView t=findViewById(R.id.textView3);
        rImage = findViewById(R.id.imageView11);
        daatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
         spe.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(Doctor.this, specialisation.class);
                 startActivity(intent);
             }
         });
        String currentUserId=mAuth.getCurrentUser().getUid();
        DatabaseReference getImage = databaseReference.child("users").child(currentUserId).child("profilepictureurl");
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String link = dataSnapshot.getValue(String.class);
                Toast.makeText(Doctor.this,link, Toast.LENGTH_LONG).show();
                Picasso.get().load(link).into(rImage);
            }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError)
            {
                Toast.makeText(Doctor.this, "Error Loading Image",Toast.LENGTH_SHORT).show();
            }
        });
        daatabaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.child("First name").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                t.setText("Hello "+test);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}