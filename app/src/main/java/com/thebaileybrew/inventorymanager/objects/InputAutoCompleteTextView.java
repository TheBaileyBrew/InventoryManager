package com.thebaileybrew.inventorymanager.objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class InputAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    public InputAutoCompleteTextView(Context context) {
        super(context);
    }

    public InputAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        final InputConnection ic = super.onCreateInputConnection(outAttrs);
        if (ic != null && outAttrs.hintText == null) {
            //If there is no hint and the parent is a TextInputLayout, use its hint for EditorInfo
            //This will display a hint in 'extract mode'
            final ViewParent parent = getParent();
            if (parent instanceof TextInputLayout) {
                outAttrs.hintText = ((TextInputLayout) parent).getHint();
            }
        }
        return ic;
    }
}
