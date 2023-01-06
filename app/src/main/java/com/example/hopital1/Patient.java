package com.example.hopital1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Patient extends AppCompatActivity {
    StorageReference storageReference;
    ProgressDialog progressDialog;
    String id;
    TextView textView;
    ImageView rImage;
    FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference daatabaseReference;
    String userID;
    private ImageView spe,para,image,chat,log;
    private Button btnout;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Profile");
        progressDialog=new ProgressDialog(this);
        //String id =getIntent();
        //binding=ActivityMainBinding.inflate(getLayoutInflater().inflate());
        setContentView(R.layout.activity_patient);
        //setContentView(binding.getRoot());
        rImage = findViewById(R.id.imageView11);
        log = findViewById(R.id.imageView8);
        spe=findViewById(R.id.imageViewkdkdkd8);
        image=findViewById(R.id.imageView8mdmd);
        chat=findViewById(R.id.imageViewddddd8);
        TextView t=findViewById(R.id.textView3);
        daatabaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //userID = mUser.getUid();
        String currentUserId=mAuth.getCurrentUser().getUid();
        DatabaseReference getImage = databaseReference.child("users").child(currentUserId).child("profilepictureurl");
       getImage.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                  {
                      String link = dataSnapshot.getValue(String.class);

                      //Toast.makeText(Patient.this,link, Toast.LENGTH_LONG).show();
                        Picasso.get().load(link).into(rImage);
                   }
            @Override
            public void onCancelled(
                    @NonNull DatabaseError databaseError)
           {
               Toast.makeText(Patient.this, "Error Loading Image",Toast.LENGTH_SHORT).show();
           }
        });

        daatabaseReference.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String test = snapshot.child("name").getValue().toString();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                t.setText("Hello "+test);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        spe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Patient.this,specialisation.class);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Patient.this,calender.class);
                startActivity(intent);
            }
        });

        para=findViewById(R.id.imageViewssss8);
        para.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Patient.this,Profile.class);
                startActivity(intent);
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Patient.this,add_mesg.class);
                startActivity(intent);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Patient.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        textView = findViewById(R.id.textView2111);
       // image = findViewById(R.id.imageView11);

        //databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        //mAuth= FirebaseAuth.getInstance();
        //mUser=mAuth.getCurrentUser();
        //userID = mUser.getUid();
        //databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
         //   @Override
           // public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   String test = snapshot.child("name").getValue().toString();
                //String id=snapshot.child("profilepictureurl").getValue().toString();
                //ImageView im= (ImageView) snapshot.child("profilepictureurl").getValue();
                //Toast.makeText(Profile.this,"Login Successful",Toast.LENGTH_SHORT).show();
                //sendUserToNextActivity(test);
                //storageReference=FirebaseStorage.getInstance().getReference("Profile pictures/"+id+".jpg");
               // textView.setText("Hello"+test);
            //}

           // @Override
           // public void onCancelled(@NonNull DatabaseError error) {

          //  }
      //eeee  });
    }
}