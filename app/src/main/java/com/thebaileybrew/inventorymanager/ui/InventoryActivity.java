package com.thebaileybrew.inventorymanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.flowingDrawer.ElasticDrawer;
import com.thebaileybrew.inventorymanager.flowingDrawer.FlowingDrawer;
import com.thebaileybrew.inventorymanager.ui.fragments.DashDetailsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class InventoryActivity extends AppCompatActivity {

    private FlowingDrawer mDrawer;
    private BottomNavigation mNavStrip;
    private MenuListFragment mMenuFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initViews();
        setupToolbar();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isMenuVisible()) {
            mDrawer.closeMenu(true);
        } else {
            super.onBackPressed();
            //TODO: Setup close app rather than navigate back to login screen
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });

    }

    private void setupNavPanel() {
        mNavStrip.setDefaultSelectedIndex(0);
        mNavStrip.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {

            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                //TODO: Set up Fragment exchange based on tab selection
                switch(i1) {
                    case 0:
                        Toast.makeText(InventoryActivity.this, "Tab 1 selected", Toast.LENGTH_SHORT).show();
                        loadFragment("dashboard_details");
                        break;
                    case 1:
                        Toast.makeText(InventoryActivity.this, "Tab 2 selected", Toast.LENGTH_SHORT).show();
                        loadFragment("dashboard_add");
                        break;
                    case 2:
                        Toast.makeText(InventoryActivity.this, "Tab 3 selected", Toast.LENGTH_SHORT).show();
                        loadFragment("dashboard_request");
                        break;
                }

            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {

            }
        });



    }

    private void initViews() {
        mDrawer = findViewById(R.id.drawer_layout);
        setupFlowingDrawer();
        mNavStrip = findViewById(R.id.navigation_tab_strip);
        setupNavPanel();
    }

    private void setupFlowingDrawer() {
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        FragmentManager fm = getSupportFragmentManager();
        mMenuFrag = (MenuListFragment) fm.findFragmentById(R.id.menu_container);
        if (mMenuFrag == null) {
            mMenuFrag = new MenuListFragment();
            fm.beginTransaction().add(R.id.menu_container, mMenuFrag).commit();
        }

    }

    private void loadFragment(String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        switch (fragmentName) {
            case "dashboard_details":
                DashDetailsFragment dashDetailsFragment = (DashDetailsFragment) fm.findFragmentById(R.id.display_frag_container);
                if (dashDetailsFragment == null) {
                    dashDetailsFragment = new DashDetailsFragment();
                    fm.beginTransaction().add(R.id.display_frag_container, dashDetailsFragment).commit();
                }


        }
    }


}
