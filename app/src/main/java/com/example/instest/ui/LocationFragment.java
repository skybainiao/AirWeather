package com.example.instest.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.instest.MainActivity;
import com.example.instest.R;
import com.example.instest.ViewModel.DashboardViewModel;
import com.example.instest.databinding.FragmentDashboardBinding;

public class LocationFragment extends Fragment {

    private FragmentDashboardBinding binding;
    public String address;
    private double latitude;
    private double longitude;
    Button button;
    TextView textViewLocation;
    TextView textView1;
    TextView textView2;
    ProgressBar progressBar;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            sleep(2000);
            address = "Location: "+aMapLocation.getCity()+" "+aMapLocation.getProvince()+" "+aMapLocation.getCountry();
            latitude = aMapLocation.getLatitude();
            longitude = aMapLocation.getLongitude();
            textView1.setText("W: "+latitude);
            textView2.setText("J: "+longitude);
            textViewLocation.setText(address);
            progressBar.setVisibility(View.GONE);
            if (latitude!=0 && longitude!=0 && aMapLocation.getCountry()!=null){
                Toast.makeText(getContext(),"Location Successfully", Toast.LENGTH_LONG).show();
            }
            else {
                textViewLocation.setText(aMapLocation.getErrorInfo()+"ErrorCode: "+aMapLocation.getErrorCode());
                Toast.makeText(getContext(),"Location Failed", Toast.LENGTH_LONG).show();
            }

        }
    };



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Intent intent = new Intent();


        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        button = root.findViewById(R.id.button3);
        textViewLocation = root.findViewById(R.id.text_dashboard);
        textView1 = root.findViewById(R.id.textView);
        textView2 = root.findViewById(R.id.textView2);
        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    ((MainActivity)getActivity()).startLocation(aMapLocationListener);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }


    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}