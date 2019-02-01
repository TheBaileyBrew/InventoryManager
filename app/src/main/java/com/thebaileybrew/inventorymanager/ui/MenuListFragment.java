package com.thebaileybrew.inventorymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.thebaileybrew.inventorymanager.R;
import com.thebaileybrew.inventorymanager.behavior.CircularImageTransformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MenuListFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = MenuListFragment.class.getSimpleName();
    private final static String HEADER_IMAGE = "https://avatars1.githubusercontent.com/u/35344621?s=460&v=4";
    private ImageView menuUserProfileImage;
    private Button menuLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        NavigationView navView = view.findViewById(R.id.navigation_menu_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(getActivity(), "Item selected is: " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        View navHeader = navView.getHeaderView(0);
        menuUserProfileImage = navHeader.findViewById(R.id.user_image_avatar);
        menuLogout = navHeader.findViewById(R.id.user_log_out);
        TextView menuName = navHeader.findViewById(R.id.user_name);
        Toast.makeText(getActivity(), "Name is: " + menuName.getText().toString(), Toast.LENGTH_SHORT).show();
        setupListener();
        setupHeaderDetails();
        return view;
    }

    private void setupListener() {
        menuLogout.setOnClickListener(this);
        Log.e(TAG, "setupListener: " + menuLogout.hasOnClickListeners());
    }

    private void setupHeaderDetails() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.menu_avatar_size);

        /*
        Picasso.get()
            .load(HEADER_IMAGE)
            .resize(avatarSize,avatarSize)
            .centerCrop()
            .transform(new CircularImageTransformation())
            .into(menuUserProfileImage);
        */
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_log_out:
                Log.e(TAG, "onClick: Clicked logout");
                Intent returnToLogin = new Intent(getActivity(), LoginActivity.class);
                startActivity(returnToLogin);
        }

    }
}
