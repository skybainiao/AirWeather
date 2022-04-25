package com.example.instest.ViewModel;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instest.DataService.DBService;
import com.example.instest.DataService.DataBaseService;
import com.example.instest.MainActivity;
import com.example.instest.Model.User;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }



    public LiveData<String> getText() {
        return mText;
    }

}