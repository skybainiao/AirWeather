package com.example.instest.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.instest.Model.Weather;
import com.example.instest.R;
import com.example.instest.ViewModel.HomeViewModel;
import com.example.instest.databinding.FragmentHomeBinding;

import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

public class WeatherFragment extends Fragment {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    ProgressDialog pd;
    private String weather;
    private Weather weatherFuture1;
    private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pd = new ProgressDialog(getContext());
        pd.setMessage("LoadingWeatherData");
        pd.show();
        textView1 = root.findViewById(R.id.item5);
        textView2 = root.findViewById(R.id.item10);
        textView3 = root.findViewById(R.id.item);
        textView4 = root.findViewById(R.id.item6);
        textView5 = root.findViewById(R.id.item11);
        textView6 = root.findViewById(R.id.item8);
        textView7 = root.findViewById(R.id.item7);
        textView8 = root.findViewById(R.id.item12);
        textView9 = root.findViewById(R.id.item9);

        QWeather.getWeatherNow(getContext(), "D740", Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean.getNow()));
                if (Code.OK == weatherBean.getCode()) {
                    //textView1.setText(weatherBean.getNow().getTemp());
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                } else {

                    Code code = weatherBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

        QWeather.getWeather3D(getContext(), "D740", Lang.EN,Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {
                pd.dismiss();
                Log.i(TAG, "getWeather onError: " + throwable);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                if (Code.OK == weatherDailyBean.getCode()) {
                    pd.dismiss();
                    textView1.setText(weatherDailyBean.getDaily().get(0).getFxDate());
                    textView2.setText(weatherDailyBean.getDaily().get(0).getTextDay());
                    textView3.setText(weatherDailyBean.getDaily().get(0).getTempMin()+"/"+weatherDailyBean.getDaily().get(1).getTempMax()+"\u2103");
                    textView4.setText(weatherDailyBean.getDaily().get(1).getFxDate());
                    textView5.setText(weatherDailyBean.getDaily().get(1).getTextDay());
                    textView6.setText(weatherDailyBean.getDaily().get(1).getTempMin()+"/"+weatherDailyBean.getDaily().get(1).getTempMax()+"\u2103");
                    textView7.setText(weatherDailyBean.getDaily().get(2).getFxDate());
                    textView8.setText(weatherDailyBean.getDaily().get(2).getTextDay());
                    textView9.setText(weatherDailyBean.getDaily().get(2).getTempMin()+"/"+weatherDailyBean.getDaily().get(1).getTempMax()+"\u2103");
                } else {
                    pd.dismiss();
                    Code code = weatherDailyBean.getCode();
                    Log.i(TAG, "failed code: " + code);
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


    public String getNull(){
        return "                    ";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }






}