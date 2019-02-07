package com.thebaileybrew.inventorymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.data.models.Userdata;
import com.thebaileybrew.inventorymanager.listeners.EditTextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.PASSWORD;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.REGISTERED_USER;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.USERNAME;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterUser.class.getSimpleName();

    private TextInputEditText mFirstName, mLastName, mUsername, mPassword, mPasswordVerify;
    private TextInputLayout mFirstLayout, mLastLayout, mUsernameLayout, mPasswordLayout, mPasswordVerifyLayout;

    private ActionProcessButton buttonRegister;

    private FirebaseAuth mAuth;
    private FirebaseUser mNewUser;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUserDatabaseReference = mFirebaseDatabase.getReference().child("users");

        initViews();
        initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNewUser = mAuth.getCurrentUser();
    }

    private void initListeners() {
        buttonRegister.setOnClickListener(this);
    }

    private void initViews() {
        mFirstName = findViewById(R.id.input_first_name);
        mFirstLayout = findViewById(R.id.register_first_name);
        mLastName = findViewById(R.id.input_last_name);
        mLastLayout = findViewById(R.id.register_last_name);
        mUsername = findViewById(R.id.input_email);
        mUsernameLayout = findViewById(R.id.register_email);
        mPassword = findViewById(R.id.input_password);
        mPasswordLayout = findViewById(R.id.register_password);
        mPasswordVerify = findViewById(R.id.input_verify);
        mPasswordVerifyLayout = findViewById(R.id.register_verify);

        buttonRegister = findViewById(R.id.progress_registering_button);
        buttonRegister.setMode(ActionProcessButton.Mode.ENDLESS);
    }





    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.progress_registering_button:
                registerUser(mNewUser);

                break;
        }
    }

    private void registerUser(FirebaseUser user) {
        if (mNewUser != null) {
            Toast.makeText(this, "Logging out of current user: " + mNewUser.getEmail(), Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }

        sendUserDetailsToFirebase(mUsername.getText().toString(), mPassword.getText().toString(), mFirstName.getText().toString(), mLastName.getText().toString());
        //registerUserViaEmail();
        Userdata thisUser = new Userdata(mFirstName.getText().toString(), mLastName.getText().toString(), mUsername.getText().toString(), mPassword.getText().toString());
        mUserDatabaseReference.push().setValue(thisUser);
        Intent returnToLogin = new Intent(RegisterUser.this, LoginActivity.class);
        returnToLogin.putExtra(USERNAME, mUsername.getText().toString());
        returnToLogin.putExtra(PASSWORD, mPasswordVerify.getText().toString());
        returnToLogin.putExtra(REGISTERED_USER, true);
        startActivity(returnToLogin);
    }

    private void registerUserViaEmail() {
        mNewUser.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterUser.this, "Verification Email Sent To: " + mNewUser.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterUser.this, "Failed To Submit Email Verification", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendUserDetailsToFirebase(String email, String password, final String firstName, final String lastName) {
        if (!validateForm()) {
            return;
        }

        //Start the authentication transmission
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterUser.this, "User Created: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String firstName = mFirstName.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mFirstLayout.setErrorEnabled(true);
            mFirstLayout.setError("Invalid Name");
            valid = false;
        } else {
            mFirstLayout.setErrorEnabled(false);
            mFirstLayout.setError(null);
        }

        String lastName = mLastName.getText().toString();
        if (TextUtils.isEmpty(firstName)) {
            mLastLayout.setErrorEnabled(true);
            mLastLayout.setError("Invalid Name");
            valid = false;
        } else {
            mLastLayout.setErrorEnabled(false);
            mLastLayout.setError(null);
        }

        String emailAddress = mUsername.getText().toString();
        if (TextUtils.isEmpty(emailAddress)) {
            mUsernameLayout.setErrorEnabled(true);
            mUsernameLayout.setError("Email Required.");
            valid = false;
        } else {
            mUsernameLayout.setErrorEnabled(false);
            mUsernameLayout.setError(null);
        }

        String password = mPassword.getText().toString();
        String passwordVerify = mPasswordVerify.getText().toString();
        if (!password.equals(passwordVerify)) {
            mPasswordVerifyLayout.setErrorEnabled(true);
            mPasswordVerifyLayout.setError("Passwords must match.");
            valid = false;
        } else {
            mPasswordVerifyLayout.setErrorEnabled(false);
            mPasswordVerifyLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordLayout.setErrorEnabled(true);
            mPasswordLayout.setError("Password Required.");
            valid = false;
        } else {
            mPasswordLayout.setErrorEnabled(false);
            mPasswordLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordVerifyLayout.setErrorEnabled(true);
            mPasswordVerifyLayout.setError("Password Required.");
            valid = false;
        } else {
            mPasswordVerifyLayout.setErrorEnabled(false);
            mPasswordVerifyLayout.setError(null);
        }

        return valid;
    }


}
