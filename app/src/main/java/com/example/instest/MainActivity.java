package com.example.instest;
import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.instest.Model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.instest.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity{

    Button button;
    TextView textView1;
    TextView textView;
    EditText editText;
    String string;
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;


    private User user = new User("saddas");
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("start");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);

        AMapLocationClient.updatePrivacyShow(this,true,true);
        AMapLocationClient.updatePrivacyAgree(this, true);


        try {
            startLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // getLocation here

    private AMapLocationClient mapLocationClient;
    private AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d("Ins","Location: "+aMapLocation.getCountry()+" "+aMapLocation.getCity());
            string = aMapLocation.getCity()+" "+aMapLocation.getCountry();
            Toast.makeText(getApplicationContext(),"Location: "+string,Toast.LENGTH_LONG).show();
            System.out.println(string);
        }
    };

    public void startLocation() throws Exception {
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationClient.setLocationListener(mapLocationListener);

        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setNeedAddress(true);
        option.setInterval(20 * 100);
        option.setMockEnable(true);
        option.setOnceLocation(true);
        mapLocationClient.setLocationOption(option);
        mapLocationClient.stopLocation();
        mapLocationClient.startLocation();

        Log.d("Ins","StartLocation");
    }

    private void stopLocation(){

        mapLocationClient.stopLocation();
        mapLocationClient.onDestroy();
        Log.d("Ins","StopLocation");
    }

    // end


    @Override
    protected void onDestroy() {
        stopLocation();
        super.onDestroy();
    }


}





