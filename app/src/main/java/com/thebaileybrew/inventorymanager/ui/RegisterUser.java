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

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.listeners.EditTextWatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.REGISTERED_USER;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterUser.class.getSimpleName();

    private TextInputEditText mFirstName, mLastName, mUsername, mPassword, mPasswordVerify;
    private TextInputLayout mFirstLayout, mLastLayout, mUsernameLayout, mPasswordLayout, mPasswordVerifyLayout;
    private TextWatcher firstNameWatcher, lastNameWatcher, usernameWatcher, passwordWatcher, passwordVerifyWatcher;

    private ActionProcessButton buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initTextWatchers();
        initListeners();
    }

    private void initListeners() {
        buttonRegister.setOnClickListener(this);
    }

    private void initTextWatchers() {
        firstNameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "beforeTextChanged: before change");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onTextChanged: onChange");
                Log.e(TAG, "onTextChanged: start value: " + start );
                Log.e(TAG, "onTextChanged: before value: " + before );
                Log.e(TAG, "onTextChanged: count value: " + count );
            }

            @Override
            public void afterTextChanged(Editable s) {
                mFirstName.removeTextChangedListener(this);
                //validate that string is not null
                if (s != null && !s.toString().isEmpty()) {
                    Log.e(TAG, "afterTextChanged: not null not empty");
                    mFirstLayout.setErrorEnabled(false);
                } else {
                    Log.e(TAG, "afterTextChanged: is null, is empty");
                    mFirstLayout.setErrorEnabled(true);
                    mFirstLayout.setError("Name cannot be empty");
                }
                mFirstName.addTextChangedListener(this);
            }
        };
        mFirstName.addTextChangedListener(firstNameWatcher);
        lastNameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mLastName.removeTextChangedListener(this);
                //validate that string is not null
                if (s != null && !s.toString().isEmpty()) {
                    mLastLayout.setErrorEnabled(false);
                } else {
                    mLastLayout.setErrorEnabled(true);
                    mLastLayout.setError("Name cannot be empty");
                }
                mLastName.addTextChangedListener(this);
            }
        };
        mLastName.addTextChangedListener(lastNameWatcher);
        usernameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mUsername.removeTextChangedListener(this);
                //validate that string is not null
                if (s != null && !s.toString().isEmpty()) {
                    mUsernameLayout.setErrorEnabled(false);
                } else {
                    if (!isEmailValid(s.toString())) {
                        mUsernameLayout.setErrorEnabled(true);
                        mUsernameLayout.setError("Must be in email address");
                    } else {
                        mUsernameLayout.setErrorEnabled(false);
                    }
                }
                mUsername.addTextChangedListener(this);
            }
        };
        mUsername.addTextChangedListener(usernameWatcher);
        passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPassword.removeTextChangedListener(this);
                //validate that string is not null
                if (s != null && !s.toString().isEmpty()) {
                    mPasswordLayout.setErrorEnabled(false);
                } else {
                    mPasswordLayout.setErrorEnabled(true);
                    mPasswordLayout.setError("Password cannot be empty");
                }
                mPassword.addTextChangedListener(this);
            }
        };
        mPassword.addTextChangedListener(passwordWatcher);
        passwordVerifyWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordVerify.removeTextChangedListener(this);
                //validate that string is not null
                if (s != null && !s.toString().isEmpty()) {
                    mPasswordVerifyLayout.setErrorEnabled(false);
                } else {
                    if (s != mPassword.getEditableText()) {
                        mPasswordVerifyLayout.setErrorEnabled(true);
                        mPasswordLayout.setErrorEnabled(true);
                        mPasswordVerifyLayout.setError("Passwords do not match");
                        mPasswordLayout.setError("Passwords do not match");
                    } else {
                        mPasswordVerifyLayout.setErrorEnabled(true);
                        mPasswordVerifyLayout.setError("Password cannot be null");
                    }
                }
                mPasswordVerify.addTextChangedListener(this);
            }
        };
        mPasswordVerify.addTextChangedListener(passwordVerifyWatcher);
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
                registerUser();

                break;
        }
    }

    private void registerUser() {
        if(!TextUtils.isEmpty(mFirstName.getText())) {
            mFirstLayout.setErrorEnabled(false);
            mFirstName.setEnabled(false);
            mFirstName.clearFocus();
        } else {
            mFirstLayout.setErrorEnabled(true);
            mFirstLayout.setError("Name cannot be empty");
        }

        if(!TextUtils.isEmpty(mLastName.getText())) {
            mLastLayout.setErrorEnabled(false);
            mLastName.setEnabled(false);
            mLastName.clearFocus();
        } else {
            mLastLayout.setErrorEnabled(true);
            mLastLayout.setError("Name cannot be empty");
        }

        if(isEmailValid(mUsername.getEditableText().toString())) {
            mUsernameLayout.setErrorEnabled(false);
            mUsername.setEnabled(false);
            mUsername.clearFocus();
        } else {
            mUsernameLayout.setErrorEnabled(true);
            mUsernameLayout.setError("Must be an email address");
        }

        if(isPasswordValid(mPassword, mPasswordVerify)
                && !TextUtils.isEmpty(mPassword.getText())
                && !TextUtils.isEmpty(mPasswordVerify.getText())) {
            mPasswordVerifyLayout.setErrorEnabled(true);
            mPasswordLayout.setErrorEnabled(true);
            mPasswordVerifyLayout.setError("Passwords do not match");
            mPasswordLayout.setError("Passwords do not match");
        } else {
            mPasswordLayout.setErrorEnabled(false);
            mPasswordVerifyLayout.setErrorEnabled(false);
            mPassword.setEnabled(false);
            mPassword.clearFocus();
            mPasswordVerify.setEnabled(false);
            mPasswordVerify.clearFocus();
        }

        if (!mFirstName.isEnabled() && !mLastName.isEnabled() && !mUsername.isEnabled()
                && !mPassword.isEnabled() && !mPasswordVerify.isEnabled()) {
            buttonRegister.setEnabled(false);
            buttonRegister.setProgress(3);

            Handler handler = new Handler();
            Runnable loader = new Runnable() {
                @Override
                public void run() {
                    buttonRegister.setProgress(100);
                    Intent returnToLogin = new Intent(RegisterUser.this, LoginActivity.class);
                    returnToLogin.putExtra(REGISTERED_USER,true);
                    startActivity(returnToLogin);
                }
            };
            handler.postDelayed(loader, 1000);
        } else {
            buttonRegister.setEnabled(true);
            buttonRegister.setProgress(0);
        }
    }

    private boolean isEmailValid(String toString) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(toString);
        return matcher.matches();
    }

    private boolean isPasswordValid(TextInputEditText password, TextInputEditText verify) {
        Log.e(TAG, "isPasswordValid: password" + password.getEditableText().toString());
        Log.e(TAG, "isPasswordValid: verify" + verify.getEditableText().toString());
        return password.getEditableText().toString().equals(verify.getEditableText().toString());
    }

}
