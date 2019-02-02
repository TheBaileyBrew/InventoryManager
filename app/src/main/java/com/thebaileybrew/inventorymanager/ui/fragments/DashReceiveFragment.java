package com.thebaileybrew.inventorymanager.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.MOTOROLA_MC3200;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_HT682;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_PA710;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.ZEBRA_TC71;

public class DashReceiveFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private final static String TAG = DashReceiveFragment.class.getSimpleName();

    private MaterialSpinner productTypeSpinner;
    private MaterialSpinner productCategorySpinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_receive_product, container, false);

        productTypeSpinner = view.findViewById(R.id.spinner_product_name);
        setupSpinnerADapter();

        return view;
    }

    private void setupSpinnerADapter() {
        String[] ITEMS = {MOTOROLA_MC3200, UNITECH_HT682, APPLE_IPHONE, UNITECH_PA710, ZEBRA_TC71};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productTypeSpinner.setAdapter(typeAdapter);
        productTypeSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
