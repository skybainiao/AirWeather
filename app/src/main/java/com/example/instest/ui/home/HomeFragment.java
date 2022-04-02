package com.example.instest.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.instest.MainActivity;
import com.example.instest.Model.User;
import com.example.instest.R;
import com.example.instest.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    public String address;
    private double latitude;
    private double longitude;
    Button button;
    TextView textViewHome;
    TextView textView1;
    TextView textView2;
    private FragmentHomeBinding binding;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            address = "Location: "+aMapLocation.getCity()+" "+aMapLocation.getProvince()+" "+aMapLocation.getCountry();
            latitude = aMapLocation.getLatitude();
            longitude = aMapLocation.getLongitude();
            textView1.setText("W: "+latitude);
            textView2.setText("J: "+longitude);
            textViewHome.setText(address);
            System.out.println(aMapLocation.getLatitude()+" "+aMapLocation.getLongitude());
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        button = root.findViewById(R.id.button3);
        textViewHome = root.findViewById(R.id.text_home);
        textView1 = root.findViewById(R.id.textView);
        textView2 = root.findViewById(R.id.textView2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Getting Location", Toast.LENGTH_LONG).show();
                try {
                    ((MainActivity)getActivity()).startLocation(aMapLocationListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        address = ((MainActivity)context).getString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}