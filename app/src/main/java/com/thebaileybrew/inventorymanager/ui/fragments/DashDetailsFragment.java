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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.data.models.Device;
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

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.MODEL_COUNT;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.APPLE_IPHONE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.MOTOROLA_MC3200;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.SWEEP_ANGLE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_HT682;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.UNITECH_PA710;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.ZEBRA_TC71;

public class DashDetailsFragment extends Fragment implements OrdersRecyclerAdapter.OrdersRecyclerAdapterClickHandler {
    private final static String TAG = DashDetailsFragment.class.getSimpleName();

    private ArcProgressStackView arcViewInventory;
    private RecyclerView orderStatusRecycler;
    private OrdersRecyclerAdapter ordersRecyclerAdapter;
    private List<Order> orderCollection = new ArrayList<>();
    private ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();

    private TextView MotoMC3200Text, UnitechHT682Text, UnitechPA710Text, AppleText, ZebraTC71Text;

    private int[] arcStartColors = new int[MODEL_COUNT];
    private int[] arcBGColors = new int[MODEL_COUNT];
    private int motoMCount, uniHCount, zebTCount, appICount, uniPCount;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mOrdersDatabaseReference;
    private DatabaseReference mDevicesDatabaseReference;

    private ChildEventListener mOrdersChildEventListener;
    private ChildEventListener mDevicesDatabaseListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDevicesDatabaseReference = mFirebaseDatabase.getReference().child("devices");
        mOrdersDatabaseReference = mFirebaseDatabase.getReference().child("orders");

        mOrdersChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order newOrder = dataSnapshot.getValue(Order.class);
                orderCollection.add(newOrder);
                ordersRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDevicesDatabaseListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Device newDevice = dataSnapshot.getValue(Device.class);
                updateArcView(newDevice.getDeviceModel(), 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Device newDevice = dataSnapshot.getValue(Device.class);
                updateArcView(newDevice.getDeviceModel(), -1);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void getCurrentInventoryValues() {
        //Value Change Listeners for total inventory levels by item
        mDevicesDatabaseReference = mFirebaseDatabase.getReference().child("devices");

        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(MOTOROLA_MC3200).endAt("Motorola MC3200\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motoMCount = (int) dataSnapshot.getChildrenCount();
                MotoMC3200Text.setText(motoMCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(ZEBRA_TC71).endAt("Zebra TC71\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zebTCount = (int) dataSnapshot.getChildrenCount();
                ZebraTC71Text.setText(zebTCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(UNITECH_HT682).endAt("Unitech HT682\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uniHCount = (int) dataSnapshot.getChildrenCount();
                UnitechHT682Text.setText(uniHCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(UNITECH_PA710).endAt("Unitech PA710\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uniPCount = (int) dataSnapshot.getChildrenCount();
                UnitechPA710Text.setText(uniPCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(APPLE_IPHONE).endAt("Apple iPhone\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appICount = (int) dataSnapshot.getChildrenCount();
                AppleText.setText(appICount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_details, container, false);

        arcViewInventory = view.findViewById(R.id.arc_view_inventory);
        orderStatusRecycler = view.findViewById(R.id.upcoming_inventory_orders);
        MotoMC3200Text = view.findViewById(R.id.mc3200_quantity);
        UnitechHT682Text = view.findViewById(R.id.ht682_quantity);
        UnitechPA710Text = view.findViewById(R.id.pa710_quantity);
        ZebraTC71Text = view.findViewById(R.id.tc71_quantity);
        AppleText = view.findViewById(R.id.iphone_quantity);
        buildArcViewColors();
        buildArcView(0);
        buildRecycler();
        

        return view;
    }

    private void buildRecycler() {
        //Adapter setup
        ordersRecyclerAdapter = new OrdersRecyclerAdapter(getActivity(), orderCollection, this);
        orderStatusRecycler.setAdapter(ordersRecyclerAdapter);
        orderStatusRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
    }


    private void updateArcView(String model, int change) {
        int position = 0;
        switch (model) {
            case MOTOROLA_MC3200:
                position = 0;
                break;
            case UNITECH_HT682:
                position = 1;
                break;
            case ZEBRA_TC71:
                position = 2;
                break;
            case APPLE_IPHONE:
                position = 3;
                break;
            case UNITECH_PA710:
                position = 4;
                break;
        }
        models.remove(position);
        models.add(position,new ArcProgressStackView.Model(" ", calculateProgress(model, change), arcBGColors[position], arcStartColors[position]));
        initializeArcView();
    }

    private void buildArcViewColors() {
        int change = 0;
        final String[] startColors = getResources().getStringArray(R.array.arc_colors);
        final String[] bgColors = getResources().getStringArray(R.array.arc_bg_colors);
        //Parse available colors
        for (int i = 0; i < MODEL_COUNT; i++) {
            arcStartColors[i] = Color.parseColor(startColors[i]);
            arcBGColors[i] = Color.parseColor(bgColors[i]);
        }
    }

    private void buildArcView(int change) {
        //Set the arc models
        models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(MOTOROLA_MC3200, change), arcBGColors[0], arcStartColors[0]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(UNITECH_HT682, change), arcBGColors[1], arcStartColors[1]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(ZEBRA_TC71, change), arcBGColors[2], arcStartColors[2]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(APPLE_IPHONE, change), arcBGColors[3], arcStartColors[3]));
        models.add(new ArcProgressStackView.Model(" ", calculateProgress(UNITECH_PA710, change), arcBGColors[4], arcStartColors[4]));
        initializeArcView();
    }

    private void initializeArcView() {
        arcViewInventory.setModels(models);
        arcViewInventory.setIsShadowed(true);
        arcViewInventory.setIsLeveled(true);
        arcViewInventory.setSweepAngle(SWEEP_ANGLE);
        arcViewInventory.requestLayout();
    }

    private float calculateProgress(String device, int valueChange) {
        getCurrentInventoryValues();
        float value = 0;
        float totalInventory = motoMCount + uniHCount + uniPCount + zebTCount + appICount;
        switch (device) {
            case MOTOROLA_MC3200:
                motoMCount = motoMCount + valueChange;
                value = (motoMCount/totalInventory)*100;
                break;
            case UNITECH_HT682:
                uniHCount = uniHCount + valueChange;
                value = (uniHCount/totalInventory)*100;
                break;
            case ZEBRA_TC71:
                zebTCount = zebTCount + valueChange;
                value = (zebTCount/totalInventory)*100;
                break;
            case APPLE_IPHONE:
                appICount = appICount + valueChange;
                value = (appICount/totalInventory)*100;
                break;
            case UNITECH_PA710:
                uniPCount = uniPCount + valueChange;
                value = (uniPCount/totalInventory)*100;
                break;
        }

        return value;
    }

    @Override
    public void onClick(View view, Order order) {
        Toast.makeText(getActivity(), "Item selected is: " + order.getItemType(), Toast.LENGTH_SHORT).show();
    }
}
