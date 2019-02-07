package com.thebaileybrew.inventorymanager.ui.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.thebaileybrew.inventorymanager.InventoryManager;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.data.models.Device;
import com.thebaileybrew.inventorymanager.data.models.Order;
import com.thebaileybrew.inventorymanager.listeners.adapters.BarcodeRecyclerAdapter;
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
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.CURRENT_ORDER_VALUE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.CUSTOMER;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.INHOUSE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.INITIAL_ORDER_CREATED;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.MOTOROLA_MC3200;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.SALES;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_HT682;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_PA710;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.ZEBRA_TC71;

public class DashReceiveFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, BarcodeRecyclerAdapter.InputEditTextClickListener {
    private final static String TAG = DashReceiveFragment.class.getSimpleName();

    private MaterialSpinner productTypeSpinner;
    private String[] ITEMS = {MOTOROLA_MC3200, UNITECH_HT682, APPLE_IPHONE, UNITECH_PA710, ZEBRA_TC71};
    private MaterialSpinner productCategorySpinner;
    private String[] CATEGORIES = {SALES, CUSTOMER, INHOUSE};
    private String selectedCategory = null;
    private String selectedItem = null;

    private TextInputLayout orderedDateLayout;
    private TextInputEditText orderedDate;
    private TextInputLayout expectedDateLayout;
    private TextInputEditText expectedDate;
    private TextInputLayout quantityOrderedLayout;
    private TextInputEditText quantityOrdered;

    private DatePickerDialog.OnDateSetListener OrderedDateListener;
    private DatePickerDialog.OnDateSetListener ExpectedDateListener;

    private RecyclerView barcodeRecycler;
    private BarcodeRecyclerAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDeviceDatabaseReference;
    private DatabaseReference mOrderDatabaseReference;
    private FirebaseAuth mAuthUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private ChildEventListener mDeviceChildListener;
    private ChildEventListener mOrderChildListener;

    private ActionProcessButton mProcessOrderButton;

    private List<String> listOfBarcodes = new ArrayList<>();
    private int currentID = 0;
    private String userEmail = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuthUser = FirebaseAuth.getInstance();
        mDeviceDatabaseReference = mFirebaseDatabase.getReference().child("devices");
        mOrderDatabaseReference = mFirebaseDatabase.getReference().child("orders");
        if (InventoryManager.checkForDefaultPrefs(INITIAL_ORDER_CREATED, getActivity())) {
            currentID = InventoryManager.getDefaultPrefs(CURRENT_ORDER_VALUE, getActivity());
        } else {
            InventoryManager.setDefaultPrefs(CURRENT_ORDER_VALUE, 0, getActivity());
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    userEmail = currentUser.getEmail();
                    Toast.makeText(getActivity(), "Logged in as: " + userEmail, Toast.LENGTH_SHORT).show();

                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_receive_product, container, false);
        mProcessOrderButton = view.findViewById(R.id.process_receipt_button);
        mProcessOrderButton.setOnClickListener(this);
        productTypeSpinner = view.findViewById(R.id.spinner_product_name);
        productCategorySpinner = view.findViewById(R.id.spinner_product_category);
        setupSpinnerAdapter();

        barcodeRecycler = view.findViewById(R.id.barcode_entry_recycler);
        orderedDate = view.findViewById(R.id.ordered_date_picker);
        orderedDateLayout = view.findViewById(R.id.ordered_date_picker_layout);
        expectedDate = view.findViewById(R.id.expected_date_picker);
        expectedDateLayout = view.findViewById(R.id.expected_date_picker_layout);
        quantityOrdered = view.findViewById(R.id.quantity_picker);
        quantityOrderedLayout = view.findViewById(R.id.quantity_picker_layout);
        quantityOrdered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    barcodeRecycler.setVisibility(View.VISIBLE);
                    int textValue = Integer.parseInt(s.toString());
                    if (textValue >= 1) {
                        setupRecyclerView(Integer.parseInt(quantityOrdered.getText().toString()));
                        quantityOrdered.clearFocus();
                    }
                } else {
                    barcodeRecycler.setVisibility(View.INVISIBLE);
                }
            }
        });
        setUpInputTextListeners();

        return view;
    }

    private void setupRecyclerView(int editTextValue) {
        adapter = new BarcodeRecyclerAdapter(getActivity(), editTextValue, this);
        barcodeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL, false));
        barcodeRecycler.setAdapter(adapter);

    }



    private void setUpInputTextListeners() {
        orderedDate.setOnClickListener(this);
        expectedDate.setOnClickListener(this);
        setupDialogDateListeners();
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
        int spinnerValue = parent.getAdapter().getCount();
        switch (spinnerValue) {
            case 5:
                if ((int) id == 0) {
                    selectedItem = ITEMS[((int) id)];
                } else {
                    selectedItem = ITEMS[((int) id - 1)];
                }
                break;
            case 3:
                if ((int) id == 0) {
                    selectedCategory = CATEGORIES[((int) id)];
                } else {
                    selectedCategory = CATEGORIES[((int) id - 1)];
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        DatePickerDialog orderedPicker = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, OrderedDateListener, 2019,01,01);
        orderedPicker.setTitle("Order Date");
        DatePickerDialog expectedPicker = new DatePickerDialog(getActivity(), R.style.MyDialogTheme, ExpectedDateListener, 2019,01,01);
        expectedPicker.setTitle("Expected Date");
        switch(v.getId()) {
            case R.id.ordered_date_picker:
                orderedPicker.show();
                break;
            case R.id.expected_date_picker:
                expectedPicker.show();
                break;
            case R.id.process_receipt_button:
                if(validateForm()) {
                    submitOrderToFirebase();
                }
                break;
        }
    }

    //This method calculates the validity of data in the form for submission
    private boolean validateForm() {
        boolean valid = true;
        if (selectedCategory == null) {
            productCategorySpinner.setEnableErrorLabel(true);
            productCategorySpinner.setError("Must select a category");
            valid = false;
        } else {
            productCategorySpinner.setEnableErrorLabel(false);
            productCategorySpinner.setError(null);
        }

        if (selectedItem == null) {
            productTypeSpinner.setEnableErrorLabel(true);
            productTypeSpinner.setError("Must select a device type");
            valid = false;
        } else {
            productTypeSpinner.setEnableErrorLabel(false);
            productTypeSpinner.setError(null);
        }

        String quantity = quantityOrdered.getText().toString();
        if (TextUtils.isEmpty(quantity)) {
            quantityOrderedLayout.setErrorEnabled(true);
            quantityOrderedLayout.setError("Must receive quantity > 0");
            valid = false;
        } else {
            quantityOrderedLayout.setErrorEnabled(false);
            quantityOrderedLayout.setError(null);
        }
        String dateOfOrder = orderedDate.getText().toString();
        if (TextUtils.isEmpty(dateOfOrder)) {
            orderedDateLayout.setErrorEnabled(true);
            orderedDateLayout.setError("Must have an initial order date");
            valid = false;
        } else {
            orderedDateLayout.setErrorEnabled(false);
            orderedDateLayout.setError(null);
        }

        String dateOfExpectation = expectedDate.getText().toString();
        if (TextUtils.isEmpty(dateOfExpectation)) {
            expectedDateLayout.setErrorEnabled(true);
            expectedDateLayout.setError("Must have an expected date");
            valid = false;
        } else {
            expectedDateLayout.setErrorEnabled(false);
            expectedDateLayout.setError(null);
        }

        return valid;
    }

    private void submitOrderToFirebase() {
        //Parse the details from the RecyclerAdapter and push values to Firebase Database
        for (int o = 0; o < Integer.parseInt(quantityOrdered.getText().toString()); o++) {
            Device addDevice = new Device(listOfBarcodes.get(o), productTypeSpinner.getSelectedItem().toString(), productCategorySpinner.getSelectedItem().toString());
            mDeviceDatabaseReference.push().setValue(addDevice);
        }
        //Setup and pass new order to Firebase Database
        Order newOrder = new Order(orderedDate.getText().toString(),
                expectedDate.getText().toString(), productTypeSpinner.getSelectedItem().toString(),
                quantityOrdered.getText().toString(), listOfBarcodes, generateOrderID());
        mOrderDatabaseReference.push().setValue(newOrder);
    }

    private String generateOrderID() {
        //Gets the current value for order number
        currentID = InventoryManager.getDefaultPrefs(CURRENT_ORDER_VALUE, getActivity());
        StringBuilder orderIDBuilder = new StringBuilder();
        orderIDBuilder.append(userEmail);
        orderIDBuilder.append("_order_");
        orderIDBuilder.append(currentID);
        orderIDBuilder.append("_");
        orderIDBuilder.append(productCategorySpinner.getSelectedItem().toString());
        currentID++;
        //Reloads SharedPrefs with the next incremental value
        InventoryManager.setDefaultPrefs(CURRENT_ORDER_VALUE, currentID, getActivity());
        return orderIDBuilder.toString();
    }

    //This method sets the listeners for the DateDialogs
    private void setupDialogDateListeners() {
        OrderedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String orderDate = month + "/" + dayOfMonth + "/" + year;
                orderedDate.setText(orderDate);
            }
        };

        ExpectedDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String expectDate = month + "/" + dayOfMonth + "/" + year;
                expectedDate.setText(expectDate);
            }
        };

    }

    @Override
    public void afterTextChanged(int position, String barcodeData) {

        listOfBarcodes.add(position,barcodeData);

    }
}
