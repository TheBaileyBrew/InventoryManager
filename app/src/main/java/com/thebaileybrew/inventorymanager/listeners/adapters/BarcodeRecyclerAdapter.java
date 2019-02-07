package com.thebaileybrew.inventorymanager.listeners.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thebaileybrew.inventorymanager.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeRecyclerAdapter extends RecyclerView.Adapter<BarcodeRecyclerAdapter.ViewHolder> {
    private static final String TAG = BarcodeRecyclerAdapter.class.getSimpleName();

    private final LayoutInflater layoutInflater;
    private int quantity;
    private InputEditTextClickListener inputEditTextClickListener;

    private HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public interface InputEditTextClickListener {
        void afterTextChanged(int position, String barcodeData);
    }

    public BarcodeRecyclerAdapter(Context context, int quantity, InputEditTextClickListener inputEditTextClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.quantity = quantity;
        this.inputEditTextClickListener = inputEditTextClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_barcode_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull final ViewHolder holder, final int position) {
        holder.barcodeFieldCount.setText(String.valueOf(position + 1));
        holder.barcodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hashMap.put(position, holder.barcodeEditText.getText().toString());

                String returnValue = hashMap.get(position);
                Log.e(TAG, "afterTextChanged: returnValue is: " + returnValue );
                inputEditTextClickListener.afterTextChanged(position,returnValue);

            }
        });
        //Set onHolder stuff here
    }

    @Override
    public int getItemCount() {
        if (quantity == 0) {
            return 0;
        } else {
            return quantity;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextInputEditText barcodeEditText;
        final TextInputLayout barcodeEditLayout;
        final TextView barcodeFieldCount;

        private ViewHolder(View barcodeView) {
            super(barcodeView);
            barcodeEditLayout = barcodeView.findViewById(R.id.barcode_entry_layout);
            barcodeEditText = barcodeView.findViewById(R.id.barcode_entry);
            barcodeFieldCount = barcodeView.findViewById(R.id.barcode_field_count);
        }
    }
}
