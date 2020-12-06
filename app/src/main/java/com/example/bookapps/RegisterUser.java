package com.example.bookapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import www.sanju.motiontoast.MotionToast;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private TextView banner, registerUser;
    private EditText etFullName, etAge, etEmail, etPassword, etPhoneNo;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = (Button)findViewById(R.id.btnRegisterUser);
        registerUser.setOnClickListener(this);

        etFullName = (EditText)findViewById(R.id.fullName);
        etAge = (EditText)findViewById(R.id.age);
        etEmail = (EditText)findViewById(R.id.email);
        etPassword = (EditText)findViewById(R.id.password);
        etPhoneNo = (EditText)findViewById(R.id.phoneNumber);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.banner:
                finish();
                break;
            case R.id.btnRegisterUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        final String email = etEmail.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String fullName = etFullName.getText().toString().trim();
        final String age = etAge.getText().toString().trim();
        final String phoneNo = etPhoneNo.getText().toString().trim();

        if(fullName.isEmpty()){
            etFullName.setError("Full Name is required!");
            etFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            etAge.setError("Age is required!");
            etAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please provide valid email!");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            etPassword.setError("Min password length should be 6 characters!");
            etPassword.requestFocus();
            return;
        }

        if(phoneNo.isEmpty()){
            etPhoneNo.setError("Phone Number is required!");
            etPhoneNo.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName,age,email,password,phoneNo);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        MotionToast.Companion.darkColorToast(RegisterUser.this,"Registered Succesful!",
                                                "Profile has been registered successfully!",
                                                MotionToast.TOAST_SUCCESS,
                                                MotionToast.GRAVITY_BOTTOM,
                                                MotionToast.LONG_DURATION,
                                                ResourcesCompat.getFont(RegisterUser.this,R.font.helvetica_regular));
                                        progressBar.setVisibility(View.GONE);
                                    }else{
                                        Toast.makeText(RegisterUser.this,"Failed to register! Please try again!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(RegisterUser.this,"Failed to register! Please try again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });


    }
}