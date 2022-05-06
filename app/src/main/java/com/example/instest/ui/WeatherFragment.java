package com.example.instest.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.instest.MainActivity;
import com.example.instest.Model.Weather;
import com.example.instest.R;
import com.example.instest.databinding.FragmentHomeBinding;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

public class WeatherFragment extends Fragment {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://airweather-51cb6-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference mDatabase = firebaseDatabase.getReference();

    EditText cityText;
    ImageView imageButton;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView temp;
    TextView max;
    TextView min;
    TextView cloud;
    ProgressDialog pd;
    private FragmentHomeBinding binding;
    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            cityText.setText(aMapLocation.getCity());

            QWeather.getGeoCityLookup(getContext(), aMapLocation.getCity(), new QWeather.OnResultGeoListener() {
                @Override
                public void onError(Throwable throwable) {
                    pd.dismiss();
                    Log.i(TAG, "getWeather onError: " + throwable.getMessage());
                }

                @Override
                public void onSuccess(GeoBean geoBean) {

                    QWeather.getWeatherNow(getContext(), geoBean.getLocationBean().get(0).getId(), Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "getWeather onError: " + e);
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(WeatherNowBean weatherBean) {
                            Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean.getNow()));
                            if (Code.OK == weatherBean.getCode()) {
                                WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                                temp.setText(now.getTemp()+"\u2103");
                                cloud.setText(now.getText());

                            } else {

                                Code code = weatherBean.getCode();
                                Log.i(TAG, "failed code: " + code);
                            }
                        }
                    });

                    QWeather.getWeather3D(getContext(), geoBean.getLocationBean().get(0).getId(), Lang.EN,Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {
                        @Override
                        public void onError(Throwable throwable) {
                            pd.dismiss();
                            Log.i(TAG, "getWeather onError: " + throwable.getMessage());
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(WeatherDailyBean weatherDailyBean) {
                            if (Code.OK == weatherDailyBean.getCode()) {
                                pd.dismiss();
                                max.setText("H:"+weatherDailyBean.getDaily().get(0).getTempMax()+"\u2103");
                                min.setText("L:"+weatherDailyBean.getDaily().get(0).getTempMin()+"\u2103");
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


                    QWeather.getWeather24Hourly(getContext(), geoBean.getLocationBean().get(0).getId(), Lang.EN,Unit.METRIC, new QWeather.OnResultWeatherHourlyListener() {
                        @Override
                        public void onError(Throwable throwable) {
                            Log.i(TAG, "getWeather onError=========: " + throwable.getMessage());
                        }

                        @Override
                        public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                            System.out.println("bbabbabababababababababba"+weatherHourlyBean.getHourly().get(0).getFxTime());
                        }
                    });


                }
            });
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pd = new ProgressDialog(getContext());
        pd.setMessage("LoadingWeatherData");
        pd.show();
        cityText = root.findViewById(R.id.cityText);
        imageButton = root.findViewById(R.id.imageButton);
        textView1 = root.findViewById(R.id.item5);
        textView2 = root.findViewById(R.id.item10);
        textView3 = root.findViewById(R.id.item);
        textView4 = root.findViewById(R.id.item6);
        textView5 = root.findViewById(R.id.item11);
        textView6 = root.findViewById(R.id.item8);
        textView7 = root.findViewById(R.id.item7);
        textView8 = root.findViewById(R.id.item12);
        textView9 = root.findViewById(R.id.item9);
        temp = root.findViewById(R.id.temp);
        max = root.findViewById(R.id.max);
        min = root.findViewById(R.id.min);
        cloud = root.findViewById(R.id.cloud);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                QWeather.getGeoCityLookup(getContext(), cityText.getText().toString(), new QWeather.OnResultGeoListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        pd.dismiss();
                        Log.i(TAG,"ErrorCity=====: "+throwable);
                    }

                    @Override
                    public void onSuccess(GeoBean geoBean) {
                        System.out.println("iddddddddddd"+geoBean.getLocationBean().get(0).getId());
                        String id = geoBean.getLocationBean().get(0).getId();
                        mDatabase.child("Cities").child(String.valueOf(cityText.getText())).setValue(id);


                        QWeather.getWeatherNow(getContext(), id, Lang.EN, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
                            @Override
                            public void onError(Throwable e) {
                                pd.dismiss();
                                Log.i(TAG, "getWeather onError: " + e);
                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(WeatherNowBean weatherBean) {
                                pd.dismiss();
                                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean.getNow()));
                                if (Code.OK == weatherBean.getCode()) {
                                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                                    temp.setText(now.getTemp()+"\u2103");
                                    cloud.setText(now.getText());

                                } else {

                                    Code code = weatherBean.getCode();
                                    Log.i(TAG, "failed code: " + code);
                                }
                            }
                        });

                        QWeather.getWeather3D(getContext(), id, Lang.EN,Unit.METRIC, new QWeather.OnResultWeatherDailyListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                Log.i(TAG, "getWeather onError: " + throwable);
                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                if (Code.OK == weatherDailyBean.getCode()) {
                                    max.setText("H:"+weatherDailyBean.getDaily().get(0).getTempMax()+"\u2103");
                                    min.setText("L:"+weatherDailyBean.getDaily().get(0).getTempMin()+"\u2103");
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
                                    Code code = weatherDailyBean.getCode();
                                    Log.i(TAG, "failed code: " + code);
                                }
                            }
                        });




                    }
                });
            }
        });

        try {
            ((MainActivity)getActivity()).startLocation(aMapLocationListener);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return root;
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