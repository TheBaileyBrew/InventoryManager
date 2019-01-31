package com.thebaileybrew.inventorymanager.listeners;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditTextWatcher implements TextWatcher {
    private TextInputEditText inputEditText;
    private TextInputLayout inputLayout;

    public EditTextWatcher(TextInputEditText inputEditText, TextInputLayout inputLayout) {
        this.inputEditText = inputEditText;
        this.inputLayout = inputLayout;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        inputEditText.removeTextChangedListener(this);
        //validate that string is not null
        if (s != null && !s.toString().isEmpty()) {
            inputLayout.setErrorEnabled(false);
        } else {
            inputLayout.setErrorEnabled(true);
            if (inputLayout.isPasswordVisibilityToggleEnabled()) {
                inputLayout.setError("Invalid Password");
            } else {
                inputLayout.setError("Invalid User Credentials");
            }
        }
        inputEditText.addTextChangedListener(this);

    }
}
