package com.thebaileybrew.inventorymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.flowingDrawer.ElasticDrawer;
import com.thebaileybrew.inventorymanager.flowingDrawer.FlowingDrawer;
import com.thebaileybrew.inventorymanager.ui.fragments.DashDetailsFragment;
import com.thebaileybrew.inventorymanager.ui.fragments.DashReceiveFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.DASHBOARD_ADD;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.DASHBOARD_DETAILS;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.DASHBOARD_REQUEST;

public class InventoryActivity extends AppCompatActivity {

    private FlowingDrawer mDrawer;
    private BottomNavigation mNavStrip;
    private MenuListFragment mMenuFrag;

    private Fragment loadedFragment = null;

    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        initViews();
        setupToolbar();
        getCurrentPrefs();
        loadFragment(DASHBOARD_DETAILS);
    }

    private void getCurrentPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
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
                        loadFragment(DASHBOARD_DETAILS);
                        break;
                    case 1:
                        loadFragment(DASHBOARD_ADD);
                        break;
                    case 2:
                        loadFragment(DASHBOARD_REQUEST);
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
        FragmentTransaction transaction = fm.beginTransaction();

        //TODO: Figure out how to clear fragment so new reference can be created...
        switch (fragmentName) {
            case "dashboard_details":
                if (loadedFragment == null) {
                    loadedFragment = new DashDetailsFragment();
                    transaction.add(R.id.display_frag_container, loadedFragment).commit();
                } else {
                    if (fm.findFragmentById(R.id.display_frag_container) instanceof DashReceiveFragment)
                    loadedFragment = new DashDetailsFragment();
                    transaction.replace(R.id.display_frag_container, loadedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case "dashboard_add":
                if (loadedFragment == null) {
                    loadedFragment = new DashReceiveFragment();
                    transaction.add(R.id.display_frag_container, loadedFragment).commit();
                } else {
                    if (fm.findFragmentById(R.id.display_frag_container) instanceof DashDetailsFragment)
                        loadedFragment = new DashReceiveFragment();
                    transaction.replace(R.id.display_frag_container, loadedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }


        }
    }


}
