package com.example.hopital1;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Calendar;

public class Profile extends AppCompatActivity {
    TextView textView,email,fulname;
    EditText Fname,Lname,Password,Phone,Email;
    Button editbtn;
    FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    DatePickerDialog.OnDateSetListener setListener;
    String userID;
    ImageView rImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Profile");
        setContentView(R.layout.activity_profile);
        textView = findViewById(R.id.textView2111);
        rImage=findViewById(R.id.imageView12);
        email=findViewById(R.id.textView17);
        fulname=findViewById(R.id.textView16);
        editbtn=findViewById(R.id.button3);
        Fname=findViewById(R.id.textView2111);
        Lname=findViewById(R.id.textView2DD);
        Password=findViewById(R.id.pass);
        Email=findViewById(R.id.textView2155);
        Phone=findViewById(R.id.textView2331);
        EditText date=findViewById(R.id.textView21);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Profile.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                String tes=day+"/"+month+"/"+year;
                date.setText(tes);
            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference daatabaseReference = firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userID = mUser.getUid();
       // databaseReference.child(userID).child("name").setValue("kdk");

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.child("name").getValue().toString();
                String test1 = snapshot.child("name2").getValue().toString();
                String tes1 = snapshot.child("email").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                fulname.setText(test+" "+test1);
                email.setText(tes1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.child("name").getValue().toString();
                String test1 = snapshot.child("name2").getValue().toString();
                String tes1 = snapshot.child("email").getValue().toString();
                String paass = snapshot.child("Password").getValue().toString();
                String phone = snapshot.child("phoneNumber").getValue().toString();
                String datee = snapshot.child("Date").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                Fname.setText(test);
                Lname.setText(test1);
                Email.setText(tes1);
                Password.setText(paass);
                Phone.setText(phone);
                date.setText(datee);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String currentUserId=mAuth.getCurrentUser().getUid();
        DatabaseReference getImage = daatabaseReference.child("users").child(currentUserId).child("profilepictureurl");
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String link = dataSnapshot.getValue(String.class);
                Toast.makeText(Profile.this,link, Toast.LENGTH_LONG).show();
                Picasso.get().load(link).into(rImage);
            }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError)
            {
                Toast.makeText(Profile.this, "Error Loading Image",Toast.LENGTH_SHORT).show();
            }
        });

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString();
                String Fnaame=Fname.getText().toString();
                String Lnaame=Lname.getText().toString();
                String password=Password.getText().toString();
                String Phonee=Phone.getText().toString();
                databaseReference.child(userID).child("name").setValue(Fnaame);
                databaseReference.child(userID).child("name2").setValue(Lnaame);
                databaseReference.child(userID).child("email").setValue(email);
                databaseReference.child(userID).child("phoneNumber").setValue(Phonee);
                databaseReference.child(userID).child("Password").setValue(password);
            }
        });

    }
}