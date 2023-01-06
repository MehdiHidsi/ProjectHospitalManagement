package com.example.hopital1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private TextView regPageQuestion;
    private EditText RegistrationFullName,RegistrationIdNumber,RegistrationPhoneNumber,
            loginEmail,loginPassword,lastname;
    private Button regButton;
    private CircleImageView profileImage;
    private Uri resultUri;
    private FirebaseAuth mAuth;
    private DatabaseReference userdatabasaRef;
    private ProgressDialog loader;
    private Spinner availabilitySpinner,departmentSpinner,specializationSpinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Doctor Registration");
        setContentView(R.layout.activity_doctor_registration);

        regPageQuestion = findViewById(R.id.regPageQuestion);
        regPageQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        RegistrationFullName = findViewById(R.id.RegistrationFullName);
        lastname = findViewById(R.id.Registrationlastname);
        RegistrationIdNumber = findViewById(R.id.RegistrationIdNumber);
        RegistrationPhoneNumber = findViewById(R.id.RegistrationPhoneNumber);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        regButton = findViewById(R.id.regButton);
        profileImage = findViewById(R.id.profileImage);
        availabilitySpinner = findViewById(R.id.availabilitySpinner);
        departmentSpinner = findViewById(R.id.departmentSpinner);
        specializationSpinner = findViewById(R.id.specializationSpinner);
        loader = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=loginEmail.getText().toString().trim();
                final String password=loginPassword.getText().toString().trim();
                final String fullName=RegistrationFullName.getText().toString().trim();
                final String Lastname=lastname.getText().toString().trim();
                final String idNumber=RegistrationIdNumber.getText().toString().trim();
                final String phoneNumber=RegistrationPhoneNumber.getText().toString().trim();
                final String availability=availabilitySpinner.getSelectedItem().toString().trim();
                final String department=departmentSpinner.getSelectedItem().toString().trim();
                final String specialization=specializationSpinner.getSelectedItem().toString().trim();

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
                if(TextUtils.isEmpty(Lastname)){
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
                if(TextUtils.isEmpty(availability)){
                    ((TextView)availabilitySpinner.getSelectedView()).setError("availability is required");
                    return;
                }
                if(TextUtils.isEmpty(department)){
                    ((TextView)departmentSpinner.getSelectedView()).setError("availability is required");
                    return;
                }
                if(TextUtils.isEmpty(specialization)){
                    ((TextView)specializationSpinner.getSelectedView()).setError("availability is required");
                    return;
                }
                if(resultUri==null){
                    Toast.makeText(DoctorRegistrationActivity.this,"Profile is required",Toast.LENGTH_LONG).show();

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
                                Toast.makeText(DoctorRegistrationActivity.this,"Error occurred"+error,Toast.LENGTH_SHORT).show();
                                Toast.makeText(DoctorRegistrationActivity.this,"This Email Or IdNumber is Already registered",Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userdatabasaRef= FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
                                HashMap userInfo=new HashMap();
                                userInfo.put("First name",fullName);
                                userInfo.put("Last name",Lastname);
                                userInfo.put("email",email);
                                userInfo.put("Password",password);
                                userInfo.put("idNumber",idNumber);
                                userInfo.put("phoneNumber",phoneNumber);
                                userInfo.put("availability",availability);
                                userInfo.put("department",department);
                                userInfo.put("specialization",specialization);
                                userInfo.put("type","Doctor");

                                userdatabasaRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(DoctorRegistrationActivity.this,"Details Set Successfully",Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            Toast.makeText(DoctorRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });

                                if(resultUri !=null){
                                    final StorageReference filepath=
                                            FirebaseStorage.getInstance().getReference().child("Profile pictures").child(currentUserId);
                                    Bitmap bitmap=null;
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
                                                                    Toast.makeText(DoctorRegistrationActivity.this,"Registrant success",Toast.LENGTH_SHORT).show();
                                                                }
                                                                else{
                                                                    Toast.makeText(DoctorRegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                        finish();

                                                    }
                                                });
                                            }
                                        }
                                    });
                                    Intent intent=new Intent(DoctorRegistrationActivity.this,LoginActivity.class);
                                    startActivity(intent);
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