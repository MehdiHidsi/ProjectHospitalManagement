package com.example.hopital1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
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

public class specialisation extends AppCompatActivity {

    ImageView rImage;

    ProgressDialog progressDialog;
    String id;
    TextView textView;
    FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference daatabaseReference;
    String userID;
    private ImageView spe,para,image,chat;
    private Button btnout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Specialisation");
        setContentView(R.layout.activity_specialisation);

        rImage=findViewById(R.id.imageView);
        textView=findViewById(R.id.textView);

        daatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //userID = mUser.getUid();
        String currentUserId=mAuth.getCurrentUser().getUid();
        DatabaseReference getImage = databaseReference.child("users").child(currentUserId).child("profilepictureurl");
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String link = dataSnapshot.getValue(String.class);

                //Toast.makeText(specialisation.this,link, Toast.LENGTH_LONG).show();
                //Picasso.get().load(link).into(rImage);
            }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError)
            {
                Toast.makeText(specialisation.this, "Error Loading Image",Toast.LENGTH_SHORT).show();
            }
        });

        daatabaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.child("name").getValue().toString();
                String test1 = snapshot.child("name2").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                textView.setText(test+" "+test1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}