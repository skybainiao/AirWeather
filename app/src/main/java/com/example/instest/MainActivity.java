package com.example.instest;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.instest.Model.User;
import com.example.instest.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.qweather.sdk.bean.IndicesBean;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.IndicesType;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{


    String address;
    private ActivityMainBinding binding;


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

        System.out.println("////////"+sHA1(this));




        // TODO: 2022/4/24 /Database







        //ask for permissions
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },1);

        AMapLocationClient.updatePrivacyShow(this,true,true);
        AMapLocationClient.updatePrivacyAgree(this, true);

        HeConfig.init("HE2204120029571686", "3c1d0f6b411c42379bde9ca2fb83661b");
        HeConfig.switchToDevService();


        QWeather.getGeoCityLookup(this, "Horsens", new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG,"Error: "+throwable);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                System.out.println(geoBean.getLocationBean().get(0).getId());
            }
        });


        QWeather.getWeatherNow(this, "D740", Lang.ZH_HANS, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean.getNow()));
                if (Code.OK == weatherBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                } else {

                    Code code = weatherBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
    }




    // getLocation
    private AMapLocationClient mapLocationClient;

    public void startLocation(AMapLocationListener mapLocationListener) throws Exception {
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


    public static String sHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setString(String string) {
        this.address = string;
    }

    public String getString(){
        return address;
    }

    @Override
    protected void onDestroy() {
        stopLocation();
        super.onDestroy();
    }


}





