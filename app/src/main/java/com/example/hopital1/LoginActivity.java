package com.example.hopital1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextView loginPageQuestion;

    EditText inputEmail,inputPassword;
    String emailPatern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
    Button btnLogin;
    ProgressDialog progressDialog;
    FirebaseUser mUser;
    String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPageQuestion = findViewById(R.id.loginPageQuestion);
        inputEmail = findViewById(R.id.loginEmail);
        inputPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.loginButton);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        loginPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SelectRegistrationActivity.class);
                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peforLogin();
            }
        });


    }

    private void peforLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();


        if(email.matches((emailPatern))){
            inputEmail.setError("Enter Email connexion");
        }else if(password.isEmpty()||password.length()<6){
            inputPassword.setError("Enter your password ");
        }else{
            progressDialog.setMessage("please wait Login......");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        userID = mUser.getUid();
                        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String test = snapshot.child("type").getValue().toString();
                                Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                sendUserToNextActivity(test);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        //Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Email or password is incorrect",Toast.LENGTH_LONG).show();

                    }

                }
            });

        }

    }

    private void sendUserToNextActivity(String test) {
        if (Objects.equals(test, "patient")){
            Intent intent=new Intent(LoginActivity.this,Patient.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if (Objects.equals(test, "Doctor")){
            Intent intent=new Intent(LoginActivity.this,Doctor.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}
