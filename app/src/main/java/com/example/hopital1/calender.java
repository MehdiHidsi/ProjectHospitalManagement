package com.example.hopital1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
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

public class calender extends AppCompatActivity {

  private   CalendarView calendarView;
   // Calendar calendar;
   private  Button button;

    private TextView textView;
    private FirebaseAuth mAuth;
    private DatabaseReference userdatabasaRef;
    private DatabaseReference daatabaseReference;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("calender");
        setContentView(R.layout.activity_calender);

        daatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //userID = mUser.getUid();
        String currentUserId=mAuth.getCurrentUser().getUid();
        button = findViewById(R.id.button);
        calendarView = findViewById(R.id.calendarView);
        textView = findViewById(R.id.textView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                String myDate = "your medical appointment :"+dayOfMonth+"/"+month+1+"/"+year;
                Toast.makeText(getApplicationContext(),myDate,Toast.LENGTH_LONG).show();
                textView.setText(myDate);
                //String currentUserId=mAuth.getCurrentUser().getUid();
                //userdatabasaRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users").child(currentUserId);
               // HashMap userInfo=new HashMap();
               // userInfo.put("date reservation",myDate);
               // userdatabasaRef.updateChildren(userInfo);
            }
        });
        daatabaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String test = snapshot.child("date reservation").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
               // textView.setText("your medical appointment : "+test);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
