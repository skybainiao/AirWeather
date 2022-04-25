package com.example.instest.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instest.DataService.DBService;
import com.example.instest.DataService.DataBaseService;
import com.example.instest.Model.User;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private DBService dbService = new DataBaseService();

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public void UploadUser(User user){
        dbService.UploadUser(user);
    }

    public LiveData<String> getText() {
        return mText;
    }
}