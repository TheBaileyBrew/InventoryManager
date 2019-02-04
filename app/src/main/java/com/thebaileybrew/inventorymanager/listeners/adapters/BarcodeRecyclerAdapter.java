package com.thebaileybrew.inventorymanager.listeners.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.listeners.BarcodeTextWatcher;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BarcodeRecyclerAdapter extends RecyclerView.Adapter<BarcodeRecyclerAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private int quantity;
    private BarcodeTextWatcher barcodeTextWatcher;

    public interface InputEditTextClickListener {
        void onClick(View view);
    }

    public BarcodeRecyclerAdapter(Context context, int quantity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.quantity = quantity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_barcode_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.barcodeFieldCount.setText(String.valueOf(position + 1));
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
            barcodeTextWatcher = new BarcodeTextWatcher(barcodeEditText, barcodeEditLayout);
        }
    }
}
