package com.example.instest.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button bt86;
    Button bt85;
    Button bt84;
    Button bt83;
    Button bt82;
    Button bt81;
    Button bt80;
    Button bt79;
    Button bt78;
    Button bt77;
    Button bt76;
    Button bt75;
    Button bt74;
    Button bt73;
    Button bt72;
    Button bt71;
    Button bt70;
    Button bt69;
    Button bt68;
    Button bt67;
    Button bt66;
    Button bt65;
    Button bt64;
    Button bt63;
    Button bt62;
    Button bt61;
    Button bt60;
    Button bt59;
    Button bt58;
    Button bt57;
    Button bt56;
    Button bt55;
    Button bt54;
    Button bt53;
    Button bt52;
    Button bt51;
    Button bt50;
    Button bt49;
    Button bt48;
    Button bt47;
    Button bt46;
    Button bt45;
    Button bt44;
    Button bt43;
    Button bt42;
    Button bt41;
    Button bt40;
    Button bt39;
    Button bt38;
    Button bt37;
    Button bt36;
    Button bt35;
    Button bt34;
    Button bt33;

    Button bt30;
    Button bt31;
    Button bt32;
    Button bt27;
    Button bt28;
    Button bt29;
    Button bt24;
    Button bt25;
    Button bt26;
    Button bt23;
    Button bt21;
    Button bt22;
    Button bt17;
    Button bt18;
    Button bt19;
    Button bt14;
    Button bt15;
    Button bt20;


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

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                            System.out.println("bbabbabababababababababba"+weatherHourlyBean.getHourly().get(23).getFxTime().substring(11,16));
                            bt86.setText("now");
                            bt85.setText(weatherHourlyBean.getHourly().get(23).getTemp()+"\u2103");
                            bt84.setText(weatherHourlyBean.getHourly().get(23).getText());
                            bt83.setText(weatherHourlyBean.getHourly().get(0).getFxTime().substring(11,16));
                            bt82.setText(weatherHourlyBean.getHourly().get(0).getTemp()+"\u2103");
                            bt81.setText(weatherHourlyBean.getHourly().get(0).getText());
                            bt80.setText(weatherHourlyBean.getHourly().get(1).getFxTime().substring(11,16));
                            bt79.setText(weatherHourlyBean.getHourly().get(1).getTemp()+"\u2103");
                            bt78.setText(weatherHourlyBean.getHourly().get(1).getText());
                            bt77.setText(weatherHourlyBean.getHourly().get(2).getFxTime().substring(11,16));
                            bt76.setText(weatherHourlyBean.getHourly().get(2).getTemp()+"\u2103");
                            bt75.setText(weatherHourlyBean.getHourly().get(2).getText());
                            bt74.setText(weatherHourlyBean.getHourly().get(3).getFxTime().substring(11,16));
                            bt73.setText(weatherHourlyBean.getHourly().get(3).getTemp()+"\u2103");
                            bt72.setText(weatherHourlyBean.getHourly().get(3).getText());
                            bt71.setText(weatherHourlyBean.getHourly().get(4).getFxTime().substring(11,16));
                            bt70.setText(weatherHourlyBean.getHourly().get(4).getTemp()+"\u2103");
                            bt69.setText(weatherHourlyBean.getHourly().get(4).getText());
                            bt68.setText(weatherHourlyBean.getHourly().get(5).getFxTime().substring(11,16));
                            bt67.setText(weatherHourlyBean.getHourly().get(5).getTemp()+"\u2103");
                            bt66.setText(weatherHourlyBean.getHourly().get(5).getText());
                            bt65.setText(weatherHourlyBean.getHourly().get(6).getFxTime().substring(11,16));
                            bt64.setText(weatherHourlyBean.getHourly().get(6).getTemp()+"\u2103");
                            bt63.setText(weatherHourlyBean.getHourly().get(6).getText());
                            bt62.setText(weatherHourlyBean.getHourly().get(7).getFxTime().substring(11,16));
                            bt61.setText(weatherHourlyBean.getHourly().get(7).getTemp()+"\u2103");
                            bt60.setText(weatherHourlyBean.getHourly().get(7).getText());
                            bt59.setText(weatherHourlyBean.getHourly().get(8).getFxTime().substring(11,16));
                            bt58.setText(weatherHourlyBean.getHourly().get(8).getTemp()+"\u2103");
                            bt57.setText(weatherHourlyBean.getHourly().get(8).getText());
                            bt56.setText(weatherHourlyBean.getHourly().get(9).getFxTime().substring(11,16));
                            bt55.setText(weatherHourlyBean.getHourly().get(9).getTemp()+"\u2103");
                            bt54.setText(weatherHourlyBean.getHourly().get(9).getText());
                            bt53.setText(weatherHourlyBean.getHourly().get(10).getFxTime().substring(11,16));
                            bt52.setText(weatherHourlyBean.getHourly().get(10).getTemp()+"\u2103");
                            bt51.setText(weatherHourlyBean.getHourly().get(10).getText());
                            bt50.setText(weatherHourlyBean.getHourly().get(11).getFxTime().substring(11,16));
                            bt49.setText(weatherHourlyBean.getHourly().get(11).getTemp()+"\u2103");
                            bt48.setText(weatherHourlyBean.getHourly().get(11).getText());
                            bt47.setText(weatherHourlyBean.getHourly().get(12).getFxTime().substring(11,16));
                            bt46.setText(weatherHourlyBean.getHourly().get(12).getTemp()+"\u2103");
                            bt45.setText(weatherHourlyBean.getHourly().get(12).getText());
                            bt44.setText(weatherHourlyBean.getHourly().get(13).getFxTime().substring(11,16));
                            bt43.setText(weatherHourlyBean.getHourly().get(13).getTemp()+"\u2103");
                            bt42.setText(weatherHourlyBean.getHourly().get(13).getText());
                            bt41.setText(weatherHourlyBean.getHourly().get(14).getFxTime().substring(11,16));
                            bt40.setText(weatherHourlyBean.getHourly().get(14).getTemp()+"\u2103");
                            bt39.setText(weatherHourlyBean.getHourly().get(14).getText());
                            bt38.setText(weatherHourlyBean.getHourly().get(15).getFxTime().substring(11,16));
                            bt37.setText(weatherHourlyBean.getHourly().get(15).getTemp()+"\u2103");
                            bt36.setText(weatherHourlyBean.getHourly().get(15).getText());
                            bt35.setText(weatherHourlyBean.getHourly().get(16).getFxTime().substring(11,16));
                            bt34.setText(weatherHourlyBean.getHourly().get(16).getTemp()+"\u2103");
                            bt33.setText(weatherHourlyBean.getHourly().get(16).getText());
                            bt30.setText(weatherHourlyBean.getHourly().get(17).getFxTime().substring(11,16));
                            bt31.setText(weatherHourlyBean.getHourly().get(17).getTemp()+"\u2103");
                            bt32.setText(weatherHourlyBean.getHourly().get(17).getText());
                            bt27.setText(weatherHourlyBean.getHourly().get(18).getFxTime().substring(11,16));
                            bt28.setText(weatherHourlyBean.getHourly().get(18).getTemp()+"\u2103");
                            bt29.setText(weatherHourlyBean.getHourly().get(18).getText());
                            bt24.setText(weatherHourlyBean.getHourly().get(19).getFxTime().substring(11,16));
                            bt25.setText(weatherHourlyBean.getHourly().get(19).getTemp()+"\u2103");
                            bt26.setText(weatherHourlyBean.getHourly().get(19).getText());
                            bt23.setText(weatherHourlyBean.getHourly().get(20).getFxTime().substring(11,16));
                            bt21.setText(weatherHourlyBean.getHourly().get(20).getTemp()+"\u2103");
                            bt22.setText(weatherHourlyBean.getHourly().get(20).getText());
                            bt17.setText(weatherHourlyBean.getHourly().get(21).getFxTime().substring(11,16));
                            bt18.setText(weatherHourlyBean.getHourly().get(21).getTemp()+"\u2103");
                            bt19.setText(weatherHourlyBean.getHourly().get(21).getText());
                            bt14.setText(weatherHourlyBean.getHourly().get(22).getFxTime().substring(11,16));
                            bt15.setText(weatherHourlyBean.getHourly().get(22).getTemp()+"\u2103");
                            bt20.setText(weatherHourlyBean.getHourly().get(22).getText());
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
        bt86 = root.findViewById(R.id.button86);
        bt85 = root.findViewById(R.id.button85);
        bt84 = root.findViewById(R.id.button84);
        bt83 = root.findViewById(R.id.button83);
        bt82 = root.findViewById(R.id.button82);
        bt81 = root.findViewById(R.id.button81);
        bt80 = root.findViewById(R.id.button80);
        bt79 = root.findViewById(R.id.button79);
        bt78 = root.findViewById(R.id.button78);
        bt77 = root.findViewById(R.id.button77);
        bt76 = root.findViewById(R.id.button76);
        bt75 = root.findViewById(R.id.button75);
        bt74 = root.findViewById(R.id.button74);
        bt73 = root.findViewById(R.id.button73);
        bt72 = root.findViewById(R.id.button72);
        bt71 = root.findViewById(R.id.button71);
        bt70 = root.findViewById(R.id.button70);
        bt69 = root.findViewById(R.id.button69);
        bt68 = root.findViewById(R.id.button68);
        bt67 = root.findViewById(R.id.button67);
        bt66 = root.findViewById(R.id.button66);
        bt65 = root.findViewById(R.id.button65);
        bt64 = root.findViewById(R.id.button64);
        bt63 = root.findViewById(R.id.button63);
        bt62 = root.findViewById(R.id.button62);
        bt61 = root.findViewById(R.id.button61);
        bt60 = root.findViewById(R.id.button60);
        bt59 = root.findViewById(R.id.button59);
        bt58 = root.findViewById(R.id.button58);
        bt57 = root.findViewById(R.id.button57);
        bt56 = root.findViewById(R.id.button56);
        bt55 = root.findViewById(R.id.button55);
        bt54 = root.findViewById(R.id.button54);
        bt53 = root.findViewById(R.id.button53);
        bt52 = root.findViewById(R.id.button52);
        bt51 = root.findViewById(R.id.button51);
        bt50 = root.findViewById(R.id.button50);
        bt49 = root.findViewById(R.id.button49);
        bt48 = root.findViewById(R.id.button48);
        bt47 = root.findViewById(R.id.button47);
        bt46 = root.findViewById(R.id.button46);
        bt45 = root.findViewById(R.id.button45);
        bt44 = root.findViewById(R.id.button44);
        bt43 = root.findViewById(R.id.button43);
        bt42 = root.findViewById(R.id.button42);
        bt41 = root.findViewById(R.id.button41);
        bt40 = root.findViewById(R.id.button40);
        bt39 = root.findViewById(R.id.button39);
        bt38 = root.findViewById(R.id.button38);
        bt37 = root.findViewById(R.id.button37);
        bt36 = root.findViewById(R.id.button36);
        bt35 = root.findViewById(R.id.button35);
        bt34 = root.findViewById(R.id.button34);
        bt33 = root.findViewById(R.id.button33);
        bt30 = root.findViewById(R.id.button30);
        bt31 = root.findViewById(R.id.button31);
        bt32 = root.findViewById(R.id.button32);
        bt27 = root.findViewById(R.id.button27);
        bt28 = root.findViewById(R.id.button28);
        bt29 = root.findViewById(R.id.button29);
        bt24 = root.findViewById(R.id.button24);
        bt25 = root.findViewById(R.id.button25);
        bt26 = root.findViewById(R.id.button26);
        bt23 = root.findViewById(R.id.button23);
        bt21 = root.findViewById(R.id.button21);
        bt22 = root.findViewById(R.id.button22);
        bt17 = root.findViewById(R.id.button17);
        bt18 = root.findViewById(R.id.button18);
        bt19 = root.findViewById(R.id.button19);
        bt14 = root.findViewById(R.id.button14);
        bt15 = root.findViewById(R.id.button15);
        bt20 = root.findViewById(R.id.button20);


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


                        QWeather.getWeather24Hourly(getContext(), id, Lang.EN,Unit.METRIC, new QWeather.OnResultWeatherHourlyListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                Log.i(TAG, "getWeather onError=========: " + throwable.getMessage());
                            }

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                                System.out.println("bbabbabababababababababba"+weatherHourlyBean.getHourly().get(23).getFxTime().substring(11,16));
                                bt86.setText("now");
                                bt85.setText(weatherHourlyBean.getHourly().get(23).getTemp()+"\u2103");
                                bt84.setText(weatherHourlyBean.getHourly().get(23).getText());
                                bt83.setText(weatherHourlyBean.getHourly().get(0).getFxTime().substring(11,16));
                                bt82.setText(weatherHourlyBean.getHourly().get(0).getTemp()+"\u2103");
                                bt81.setText(weatherHourlyBean.getHourly().get(0).getText());
                                bt80.setText(weatherHourlyBean.getHourly().get(1).getFxTime().substring(11,16));
                                bt79.setText(weatherHourlyBean.getHourly().get(1).getTemp()+"\u2103");
                                bt78.setText(weatherHourlyBean.getHourly().get(1).getText());
                                bt77.setText(weatherHourlyBean.getHourly().get(2).getFxTime().substring(11,16));
                                bt76.setText(weatherHourlyBean.getHourly().get(2).getTemp()+"\u2103");
                                bt75.setText(weatherHourlyBean.getHourly().get(2).getText());
                                bt74.setText(weatherHourlyBean.getHourly().get(3).getFxTime().substring(11,16));
                                bt73.setText(weatherHourlyBean.getHourly().get(3).getTemp()+"\u2103");
                                bt72.setText(weatherHourlyBean.getHourly().get(3).getText());
                                bt71.setText(weatherHourlyBean.getHourly().get(4).getFxTime().substring(11,16));
                                bt70.setText(weatherHourlyBean.getHourly().get(4).getTemp()+"\u2103");
                                bt69.setText(weatherHourlyBean.getHourly().get(4).getText());
                                bt68.setText(weatherHourlyBean.getHourly().get(5).getFxTime().substring(11,16));
                                bt67.setText(weatherHourlyBean.getHourly().get(5).getTemp()+"\u2103");
                                bt66.setText(weatherHourlyBean.getHourly().get(5).getText());
                                bt65.setText(weatherHourlyBean.getHourly().get(6).getFxTime().substring(11,16));
                                bt64.setText(weatherHourlyBean.getHourly().get(6).getTemp()+"\u2103");
                                bt63.setText(weatherHourlyBean.getHourly().get(6).getText());
                                bt62.setText(weatherHourlyBean.getHourly().get(7).getFxTime().substring(11,16));
                                bt61.setText(weatherHourlyBean.getHourly().get(7).getTemp()+"\u2103");
                                bt60.setText(weatherHourlyBean.getHourly().get(7).getText());
                                bt59.setText(weatherHourlyBean.getHourly().get(8).getFxTime().substring(11,16));
                                bt58.setText(weatherHourlyBean.getHourly().get(8).getTemp()+"\u2103");
                                bt57.setText(weatherHourlyBean.getHourly().get(8).getText());
                                bt56.setText(weatherHourlyBean.getHourly().get(9).getFxTime().substring(11,16));
                                bt55.setText(weatherHourlyBean.getHourly().get(9).getTemp()+"\u2103");
                                bt54.setText(weatherHourlyBean.getHourly().get(9).getText());
                                bt53.setText(weatherHourlyBean.getHourly().get(10).getFxTime().substring(11,16));
                                bt52.setText(weatherHourlyBean.getHourly().get(10).getTemp()+"\u2103");
                                bt51.setText(weatherHourlyBean.getHourly().get(10).getText());
                                bt50.setText(weatherHourlyBean.getHourly().get(11).getFxTime().substring(11,16));
                                bt49.setText(weatherHourlyBean.getHourly().get(11).getTemp()+"\u2103");
                                bt48.setText(weatherHourlyBean.getHourly().get(11).getText());
                                bt47.setText(weatherHourlyBean.getHourly().get(12).getFxTime().substring(11,16));
                                bt46.setText(weatherHourlyBean.getHourly().get(12).getTemp()+"\u2103");
                                bt45.setText(weatherHourlyBean.getHourly().get(12).getText());
                                bt44.setText(weatherHourlyBean.getHourly().get(13).getFxTime().substring(11,16));
                                bt43.setText(weatherHourlyBean.getHourly().get(13).getTemp()+"\u2103");
                                bt42.setText(weatherHourlyBean.getHourly().get(13).getText());
                                bt41.setText(weatherHourlyBean.getHourly().get(14).getFxTime().substring(11,16));
                                bt40.setText(weatherHourlyBean.getHourly().get(14).getTemp()+"\u2103");
                                bt39.setText(weatherHourlyBean.getHourly().get(14).getText());
                                bt38.setText(weatherHourlyBean.getHourly().get(15).getFxTime().substring(11,16));
                                bt37.setText(weatherHourlyBean.getHourly().get(15).getTemp()+"\u2103");
                                bt36.setText(weatherHourlyBean.getHourly().get(15).getText());
                                bt35.setText(weatherHourlyBean.getHourly().get(16).getFxTime().substring(11,16));
                                bt34.setText(weatherHourlyBean.getHourly().get(16).getTemp()+"\u2103");
                                bt33.setText(weatherHourlyBean.getHourly().get(16).getText());
                                bt30.setText(weatherHourlyBean.getHourly().get(17).getFxTime().substring(11,16));
                                bt31.setText(weatherHourlyBean.getHourly().get(17).getTemp()+"\u2103");
                                bt32.setText(weatherHourlyBean.getHourly().get(17).getText());
                                bt27.setText(weatherHourlyBean.getHourly().get(18).getFxTime().substring(11,16));
                                bt28.setText(weatherHourlyBean.getHourly().get(18).getTemp()+"\u2103");
                                bt29.setText(weatherHourlyBean.getHourly().get(18).getText());
                                bt24.setText(weatherHourlyBean.getHourly().get(19).getFxTime().substring(11,16));
                                bt25.setText(weatherHourlyBean.getHourly().get(19).getTemp()+"\u2103");
                                bt26.setText(weatherHourlyBean.getHourly().get(19).getText());
                                bt23.setText(weatherHourlyBean.getHourly().get(20).getFxTime().substring(11,16));
                                bt21.setText(weatherHourlyBean.getHourly().get(20).getTemp()+"\u2103");
                                bt22.setText(weatherHourlyBean.getHourly().get(20).getText());
                                bt17.setText(weatherHourlyBean.getHourly().get(21).getFxTime().substring(11,16));
                                bt18.setText(weatherHourlyBean.getHourly().get(21).getTemp()+"\u2103");
                                bt19.setText(weatherHourlyBean.getHourly().get(21).getText());
                                bt14.setText(weatherHourlyBean.getHourly().get(22).getFxTime().substring(11,16));
                                bt15.setText(weatherHourlyBean.getHourly().get(22).getTemp()+"\u2103");
                                bt20.setText(weatherHourlyBean.getHourly().get(22).getText());
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