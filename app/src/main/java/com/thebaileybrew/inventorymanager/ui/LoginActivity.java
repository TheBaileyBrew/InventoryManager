package com.thebaileybrew.inventorymanager.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.listeners.EditTextWatcher;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.REGISTERED_USER;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mLoginEditText, mPasswordEditText;
    private TextInputLayout mLoginLayout, mPasswordLayout;
    private EditTextWatcher editTextWatcherLogin, editTextWatcherPassword;

    private ActionProcessButton buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent getIntent = getIntent();
        Boolean registered = getIntent.getBooleanExtra(REGISTERED_USER, false);
        if (registered) {
            Toast.makeText(this, "New User Registered", Toast.LENGTH_SHORT).show();
        }
        initViews();
        initListeners();


    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        editTextWatcherLogin = new EditTextWatcher(mLoginEditText, mLoginLayout);
        editTextWatcherPassword = new EditTextWatcher(mPasswordEditText, mPasswordLayout);
    }

    private void initViews() {
        buttonLogin = findViewById(R.id.progress_loading_button);
        buttonLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        buttonRegister = findViewById(R.id.register_user);

        //Set associations for EditTexts
        mLoginEditText = findViewById(R.id.input_text_user);
        mPasswordEditText = findViewById(R.id.input_text_password);

        //Set associations for EditText Error Layouts
        mLoginLayout = findViewById(R.id.input_layout_user);
        mPasswordLayout = findViewById(R.id.input_layout_password);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.progress_loading_button:
                if (TextUtils.isEmpty(mLoginEditText.getText()) || TextUtils.isEmpty(mPasswordEditText.getText())) {
                    if (TextUtils.isEmpty(mLoginEditText.getText())) {
                        mLoginLayout.setErrorEnabled(true);
                        mLoginLayout.setError("Please enter a username");
                    } else {
                        mLoginLayout.setErrorEnabled(false);
                    }
                    if (TextUtils.isEmpty(mPasswordEditText.getText())) {
                        mPasswordLayout.setErrorEnabled(true);
                        mPasswordLayout.setError("Please enter a password");
                    } else {
                        mPasswordLayout.setErrorEnabled(false);
                    }
                } else if (!TextUtils.isEmpty(mLoginEditText.getText()) && !TextUtils.isEmpty(mPasswordEditText.getText())) {
                    buttonLogin.setEnabled(false);
                    mLoginEditText.setEnabled(false);
                    mPasswordEditText.setEnabled(false);
                    mLoginEditText.clearFocus();
                    mPasswordEditText.clearFocus();
                    mPasswordLayout.setErrorEnabled(false);
                    mLoginLayout.setErrorEnabled(false);
                    buttonLogin.setProgress(3);
                    //Progress is set to 100 on validation of edit text entries via login logic to be determined
                    loadProgress();
                }
                break;
            case R.id.register_user:
                Intent registerUser = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(registerUser);
                break;
        }
    }

    private void loadProgress() {
        Handler handler = new Handler();
        Runnable loader = new Runnable() {
            @Override
            public void run() {
                buttonLogin.setProgress(100);
                Intent startActivity = new Intent(LoginActivity.this, InventoryActivity.class);
                startActivity(startActivity);
            }
        };
        handler.postDelayed(loader, 1000);
    }

}
