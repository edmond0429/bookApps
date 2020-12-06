package com.example.bookapps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import www.sanju.motiontoast.MotionToast;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.w3c.dom.Text;

import java.sql.Struct;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView register, forgetPassword;
    private EditText etEmail, etPassword;
    private Button signIn;
    private int login = 0;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;
    FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button)view.findViewById(R.id.signIn);
        signIn.setOnClickListener(this);

        etEmail = (EditText)view.findViewById(R.id.email);
        etPassword = (EditText)view.findViewById(R.id.password);

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        forgetPassword = (TextView)view.findViewById(R.id.forgetPassword);
        forgetPassword.setOnClickListener(this);

        chipNavigationBar = view.findViewById(R.id.chipNavigation);
        chipNavigationBar.setItemSelected(R.id.profile,true);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener(){
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.category:
                        fragment = new CatogaryFragment();
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.favourite:
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new BlankFavFragment();
                        }else{
                            fragment = new FavouriteFragment();
                        }
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                        break;
                    case R.id.profile:
                        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                            fragment = new ProfileFragment();
                        }else{
                            fragment = new LogInFragment();
                        }
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment)
                                .commit();
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(getActivity(), RegisterUser.class));
                break;
            case R.id.signIn:
                userLogin();
                break;
            case R.id.forgetPassword:
                startActivity(new Intent(getActivity(), ForgetPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter a valid email!");
            etEmail.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPassword.setError("Min password length is 6 characters!");
            etPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()) {
                        //redirected to user profile
                        MotionToast.Companion.darkColorToast(getActivity(),
                                "Success",
                                "Login successfully!",
                                MotionToast.TOAST_SUCCESS,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(getActivity(),R.font.helvetica_regular));
                        if(mFirebaseAuth.getCurrentUser().getUid().equals("0yH2dbifT9PMtzM7O99M2mQeZHw2") ) {
                            Fragment fragment = new adminLoggedInFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                        }else {
                            Fragment fragment = new LogInFragment();
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.container, fragment)
                                    .commit();
                        }
                    }else{
                        user.sendEmailVerification();
                        MotionToast.Companion.darkToast(getActivity(),"Verify Before Login","Please Check Your Email to Verify Account",
                                MotionToast.TOAST_INFO,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(getActivity(),R.font.helvetica_regular));
                        progressBar.setVisibility(View.GONE);
                    }
                }else{
                    MotionToast.Companion.darkColorToast(getActivity(),"Failed to login!","Please Check Your Credentials To Perform login!",
                            MotionToast.TOAST_ERROR,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(getActivity(),R.font.helvetica_regular));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

}