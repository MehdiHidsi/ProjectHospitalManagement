package com.example.hopital1;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientRegistrationActivity extends AppCompatActivity {

    private TextView regPageQuestion;

    private EditText RegistrationFullName,RegistrationIdNumber,RegistrationPhoneNumber,
            loginEmail,loginPassword,lastname,date;
    private Button regButton;
    private CircleImageView profileImage;
    DatePickerDialog.OnDateSetListener setListener;

    private Uri resultUri;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference userdatabasaRef;
    private ProgressDialog loader;
    private String userID;
    String test,test1;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("patient Registration");
        setContentView(R.layout.activity_patient_registration);
        date=findViewById(R.id.Registrationdate);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        PatientRegistrationActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth
                ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();


            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month=month+1;
                String tes=day+"/"+month+"/"+year;
                date.setText(tes);


            }
        };

        regPageQuestion=findViewById(R.id.regPageQuestion);
        //databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/");
        regPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PatientRegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        RegistrationFullName=findViewById(R.id.RegistrationFullName);
        lastname=findViewById(R.id.Registrationlastname);
        RegistrationIdNumber=findViewById(R.id.RegistrationIdNumber);
        RegistrationPhoneNumber=findViewById(R.id.RegistrationPhoneNumber);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        regButton=findViewById(R.id.regButton);
        profileImage=findViewById(R.id.profileImage);

        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=loginEmail.getText().toString().trim();
                final String password=loginPassword.getText().toString().trim();
                final String fullName=RegistrationFullName.getText().toString().trim();
                final String LastName=lastname.getText().toString().trim();
                final String idNumber=RegistrationIdNumber.getText().toString().trim();
                final String phoneNumber=RegistrationPhoneNumber.getText().toString().trim();
                final String datee=date.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                  loginPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(fullName)){
                    RegistrationFullName.setError("First Name is required");
                    return;
                }
                if(TextUtils.isEmpty(LastName)){
                    RegistrationFullName.setError("Last Name is required");
                    return;
                }
                if(TextUtils.isEmpty(idNumber)){
                    RegistrationIdNumber.setError("Id Number is required");
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber)){
                    RegistrationPhoneNumber.setError("Phone Number is required");
                    return;
                }
                if(TextUtils.isEmpty(datee)){
                    RegistrationPhoneNumber.setError("Date is required");
                    return;
                }
                if(resultUri==null){
                    Toast.makeText(PatientRegistrationActivity.this,"Profile is required",Toast.LENGTH_LONG).show();

                }
                else{
                    loader.setMessage("Registration in progress....");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@Nullable Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                String error=task.getException().toString();
                                Toast.makeText(PatientRegistrationActivity.this,"Error occurred"+error,Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: "+error);
                                Toast.makeText(PatientRegistrationActivity.this,"This Email Or IdNumber is Already registered",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userdatabasaRef= FirebaseDatabase.getInstance().getReferenceFromUrl("https://hospital-system-e9e0f-default-rtdb.firebaseio.com/").child("users").child(currentUserId);
                                    HashMap userInfo=new HashMap();
                                userInfo.put("name",fullName);
                                userInfo.put("name2",LastName);
                                userInfo.put("email",email);
                                userInfo.put("idNumber",idNumber);
                                userInfo.put("phoneNumber",phoneNumber);
                                userInfo.put("Password",password);
                                userInfo.put("Date",datee);
                                userInfo.put("type","patient");
                                userInfo.put("groupName","Medical");
                                userdatabasaRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(PatientRegistrationActivity.this,"Details Set Successfully",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(PatientRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });

                                if(resultUri !=null){
                                    final StorageReference filepath=
                                            FirebaseStorage.getInstance().getReferenceFromUrl("gs://hospital-system-e9e0f.appspot.com").child("Profile pictures").child(idNumber);
                                    Bitmap bitmap=null;
                                    //filepath.putFile(resultUri);
                                    try {
                                        bitmap= MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }

                                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);

                                    byte[] data=byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask=filepath.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            finish();
                                            return;

                                        }
                                    });

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if(taskSnapshot.getMetadata()!=null){
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl=uri.toString();
                                                        Map newImageMap=new HashMap<>();
                                                        newImageMap.put("profilepictureurl",imageUrl);
                                                        userdatabasaRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if(task.isSuccessful()){
                                                                    Toast.makeText(PatientRegistrationActivity.this,"Registrant success",Toast.LENGTH_SHORT).show();
                                                                }
                                                                else{
                                                                    Toast.makeText(PatientRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                    });

                                    Intent intent=new Intent(PatientRegistrationActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    //Toast.makeText(PatientRegistrationActivity.this,"Already registered",Toast.LENGTH_SHORT).show();
                                    finish();
                                    loader.dismiss();
                                }


                            }

                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==1 && requestCode== Activity.RESULT_OK && data!=null);
        resultUri=data.getData();
        profileImage.setImageURI(resultUri);
    }

}