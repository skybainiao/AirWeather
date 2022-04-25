package com.example.instest.DataService;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.instest.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBaseService implements DBService{

    FirebaseDatabase database;
    DatabaseReference mDatabase;


    public DataBaseService(){
        database = FirebaseDatabase.getInstance("https://airweather-51cb6-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference();
    }


    @Override
    public void UploadUser(User user) {
        mDatabase.child("Users").child(user.getUsername()).setValue(user);
    }
}
