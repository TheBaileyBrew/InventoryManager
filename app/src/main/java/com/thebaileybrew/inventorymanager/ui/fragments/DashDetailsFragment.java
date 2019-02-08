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


    }

    private void getCurrentInventoryValues() {
        //Value Change Listeners for total inventory levels by item
        mDevicesDatabaseReference = mFirebaseDatabase.getReference().child("devices");
        mDevicesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                initializeArcView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(MOTOROLA_MC3200).endAt("Motorola MC3200\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                motoMCount = (int) dataSnapshot.getChildrenCount();
                MotoMC3200Text.setText(String.valueOf(motoMCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(ZEBRA_TC71).endAt("Zebra TC71\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                zebTCount = (int) dataSnapshot.getChildrenCount();
                ZebraTC71Text.setText(String.valueOf(zebTCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(UNITECH_HT682).endAt("Unitech HT682\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uniHCount = (int) dataSnapshot.getChildrenCount();
                UnitechHT682Text.setText(String.valueOf(uniHCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(UNITECH_PA710).endAt("Unitech PA710\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                uniPCount = (int) dataSnapshot.getChildrenCount();
                UnitechPA710Text.setText(String.valueOf(uniPCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDevicesDatabaseReference.orderByChild("deviceModel").startAt(APPLE_IPHONE).endAt("Apple iPhone\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appICount = (int) dataSnapshot.getChildrenCount();
                AppleText.setText(String.valueOf(appICount));
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
        buildRecycler();
        Log.e(TAG, "onCreateView: models size: " + models.size() );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

                Device addedDevice = dataSnapshot.getValue(Device.class);
                Log.e(TAG, "onChildAdded: added: " +addedDevice.getDeviceModel());
                int position = 0;
                switch (addedDevice.getDeviceModel()) {
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
                models.add(new ArcProgressStackView.Model(" ", calculateProgress(addedDevice.getDeviceModel()), arcBGColors[position], arcStartColors[position]));
                Log.e(TAG, "onChildAdded: model added: " + models.size() + addedDevice.getDeviceModel() );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Device removedDevice = dataSnapshot.getValue(Device.class);
                int position = 0;
                switch (removedDevice.getDeviceModel()) {
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
                models.add(new ArcProgressStackView.Model(" ", calculateProgress(removedDevice.getDeviceModel()), arcBGColors[position], arcStartColors[position]));

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDevicesDatabaseReference.addChildEventListener(mDevicesDatabaseListener);
        mOrdersDatabaseReference.addChildEventListener(mOrdersChildEventListener);




    }

    private void buildRecycler() {
        //Adapter setup
        ordersRecyclerAdapter = new OrdersRecyclerAdapter(getActivity(), orderCollection, this);
        orderStatusRecycler.setAdapter(ordersRecyclerAdapter);
        orderStatusRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
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


    private void initializeArcView() {
        arcViewInventory.animateProgress();
        Log.e(TAG, "initializeArcView: creating arcView");
        Log.e(TAG, "initializeArcView: models size: " + models.size() );
        arcViewInventory.setModels(models);
        arcViewInventory.setIsShadowed(true);
        arcViewInventory.setIsLeveled(true);
        arcViewInventory.setSweepAngle(SWEEP_ANGLE);
        arcViewInventory.requestLayout();


    }

    private float calculateProgress(String device) {
        getCurrentInventoryValues();
        float value = 0;
        float totalInventory = motoMCount + uniHCount + uniPCount + zebTCount + appICount;
        switch (device) {
            case MOTOROLA_MC3200:
                value = (motoMCount/totalInventory)*100;
                break;
            case UNITECH_HT682:
                value = (uniHCount/totalInventory)*100;
                break;
            case ZEBRA_TC71:
                value = (zebTCount/totalInventory)*100;
                break;
            case APPLE_IPHONE:
                value = (appICount/totalInventory)*100;
                break;
            case UNITECH_PA710:
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
