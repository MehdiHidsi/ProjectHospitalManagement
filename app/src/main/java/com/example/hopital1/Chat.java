package com.example.hopital1;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hopital1.adapter.MessageAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;


public class Chat extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Bundle extras;
    private CollectionReference MessageRef1;
    private CollectionReference MessageRef2;
    private MessageAdapter adapter;
    private TextInputEditText envoyer;
    private Button btnEnvoyer;
    FirebaseUser mUser;
    String userID;
    private DatabaseReference daatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        daatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        String currentUserId=mAuth.getCurrentUser().getUid();
        userID = mUser.getUid();
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Firstname= snapshot.child("name").getValue().toString();
                String Lastname = snapshot.child("name2").getValue().toString();
                String email = snapshot.child("email").getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}