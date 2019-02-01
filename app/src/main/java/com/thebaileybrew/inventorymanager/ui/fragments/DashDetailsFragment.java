package com.thebaileybrew.inventorymanager.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
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

public class DashDetailsFragment extends Fragment implements OrdersRecyclerAdapter.OrdersRecyclerAdapterClickHandler {
    private final static String TAG = DashDetailsFragment.class.getSimpleName();
    private final static int MODEL_COUNT = 5;
    private final static int SWEEP_ANGLE = 270;
    private final static String MOTOROLA_MC3200 = "Motorola MC3200";
    private final static String UNITECH_HT682 = "Unitech HT682";
    private final static String ZEBRA_TC71 = "Zebra TC71";
    private final static String APPLE_IPHONE = "Apple iPhone";
    private final static String UNITECH_PA710 = "Unitech PA710";

    ArcProgressStackView arcViewInventory;
    RecyclerView orderStatusRecycler;
    List<Order> orderCollection = new ArrayList<>();


    private int[] arcStartColors = new int[MODEL_COUNT];
    private int[] arcBGColors = new int[MODEL_COUNT];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_details, container, false);

        arcViewInventory = view.findViewById(R.id.arc_view_inventory);
        orderStatusRecycler = view.findViewById(R.id.upcoming_inventory_orders);
        buildArcView();
        buildRecycler();

        return view;
    }

    private void buildRecycler() {

        buildArrayList();
        //Adapter setup
        OrdersRecyclerAdapter orderAdapter = new OrdersRecyclerAdapter(getActivity(), orderCollection, this);
        orderStatusRecycler.setAdapter(orderAdapter);
        orderStatusRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
    }

    private void buildArrayList() {
        String[] orderDates = {"01/01/2018", "07/14/2018", "04/12/2019", "01/16/2018", "06/16/2014", "09/19/2019", "10/01/2018"};
        String[] expectedDates = {"02/18/2018", "07/19/2018", "04/14/2019", "01/31/2018", "06/28/2014", "09/27/2019", "10/09/2018"};
        String[] itemTypes = {"Motorola MC3200", "Unitech PA710", "Apple iPhone", "Apple iPhone", "Zebra TC71", "Zebra TC71", "Unitech HT682"};
        String[] ordQty = {"4","18","5","11","44","28","3"};
        for (int i = 0; i < orderDates.length; i++) {
            Order newOrder = new Order();
            newOrder.setItemType(itemTypes[i]);
            newOrder.setOrderDate(orderDates[i]);
            newOrder.setExpectedDate(expectedDates[i]);
            newOrder.setOrderQuantity(ordQty[i]);
            orderCollection.add(newOrder);
        }
    }

    private void buildArcView() {
        final String[] startColors = getResources().getStringArray(R.array.arc_colors);
        final String[] bgColors = getResources().getStringArray(R.array.arc_bg_colors);
        //Parse available colors
        for(int i = 0; i <MODEL_COUNT; i++) {
            arcStartColors[i] = Color.parseColor(startColors[i]);
            arcBGColors[i] = Color.parseColor(bgColors[i]);
        }



        //Set the arc models
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(MOTOROLA_MC3200), arcBGColors[0], arcStartColors[0]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(UNITECH_HT682), arcBGColors[1], arcStartColors[1]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(ZEBRA_TC71), arcBGColors[2], arcStartColors[2]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(APPLE_IPHONE), arcBGColors[3], arcStartColors[3]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(UNITECH_PA710), arcBGColors[4], arcStartColors[4]));
        arcViewInventory.setModels(models);
        arcViewInventory.setIsShadowed(true);
        arcViewInventory.setIsLeveled(true);
        arcViewInventory.setSweepAngle(SWEEP_ANGLE);
        arcViewInventory.requestLayout();
    }

    private float calculateProgress(String device) {
        float value = 0;
        float totalInventory = 100;
        switch (device) {
            case MOTOROLA_MC3200:
                int invLvl3200 = 76;
                value = (invLvl3200/totalInventory)*100;
                break;
            case UNITECH_HT682:
                int invLvl682 = 24;
                value = (invLvl682/totalInventory)*100;
                break;
            case ZEBRA_TC71:
                int invLvl71 = 97;
                value = (invLvl71/totalInventory)*100;
                break;
            case APPLE_IPHONE:
                int invLvl6SPLUS = 15;
                value = (invLvl6SPLUS/totalInventory)*100;
                break;
            case UNITECH_PA710:
                int invLvl710 = 30;
                value = (invLvl710/totalInventory)*100;
                break;
        }

        return value;
    }

    @Override
    public void onClick(View view, Order order) {
        Toast.makeText(getActivity(), "Item selected is: " + order.getItemType(), Toast.LENGTH_SHORT).show();
    }
}
