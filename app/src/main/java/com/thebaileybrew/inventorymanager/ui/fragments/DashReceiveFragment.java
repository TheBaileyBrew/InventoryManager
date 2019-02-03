package com.thebaileybrew.inventorymanager.ui.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.data.models.Order;
import com.thebaileybrew.inventorymanager.listeners.adapters.OrdersRecyclerAdapter;
import com.thebaileybrew.inventorymanager.objects.ArcProgressStackView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.ganfra.materialspinner.MaterialSpinner;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.APPLE_IPHONE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.CUSTOMER;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.INHOUSE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.MOTOROLA_MC3200;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.SALES;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_HT682;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_PA710;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.ZEBRA_TC71;

public class DashReceiveFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private final static String TAG = DashReceiveFragment.class.getSimpleName();

    private MaterialSpinner productTypeSpinner;
    private String[] ITEMS = {MOTOROLA_MC3200, UNITECH_HT682, APPLE_IPHONE, UNITECH_PA710, ZEBRA_TC71};
    private MaterialSpinner productCategorySpinner;
    private String[] CATEGORIES = {SALES, CUSTOMER, INHOUSE};

    private TextInputEditText orderedDate;
    private TextInputEditText expectedDate;
    private TextInputEditText quantityOrdered;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_receive_product, container, false);

        productTypeSpinner = view.findViewById(R.id.spinner_product_name);
        productCategorySpinner = view.findViewById(R.id.spinner_product_category);
        setupSpinnerAdapter();

        orderedDate = view.findViewById(R.id.ordered_date_picker);
        expectedDate = view.findViewById(R.id.expected_date_picker);
        quantityOrdered = view.findViewById(R.id.quantity_picker);
        setUpInputTextListeners();


        return view;
    }

    private void setUpInputTextListeners() {
        orderedDate.setOnClickListener(this);
        expectedDate.setOnClickListener(this);
    }

    private void setupSpinnerAdapter() {

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productTypeSpinner.setAdapter(typeAdapter);
        productTypeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, CATEGORIES);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategorySpinner.setAdapter(categoryAdapter);
        productCategorySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinnerValue = view.getId();
        switch (spinnerValue) {
            case R.id.spinner_product_name:
                Toast.makeText(getActivity(), "Item Selected: " + ITEMS[position], Toast.LENGTH_SHORT).show();
                break;
            case R.id.spinner_product_category:
                Toast.makeText(getActivity(), "Category Selected: " + CATEGORIES[position], Toast.LENGTH_SHORT).show();
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, 2019,01,01);

        switch(v.getId()) {
            case R.id.ordered_date_picker:

                break;
            case R.id.expected_date_picker:

                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        
    }
}
