package com.example.instest.DataService;

import com.example.instest.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DataService implements FireBaseData {

    FirebaseDatabase database;
    DatabaseReference mDatabase;


    public DataService(){
        database = FirebaseDatabase.getInstance("https://airweather-51cb6-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabase = database.getReference();
    }

    @Override
    public void UploadUser(User user) {
        if (!user.getUsername().equals("") && !user.getPassword().equals("")){
            mDatabase.child("Users").child(user.getUsername()).setValue(user);
        }
    }

    @Override
    public ArrayList<User> getUsers() {
        return null;
    }

    public DatabaseReference getmDatabase() {
        return mDatabase;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
    //@Override
    //public ArrayList<User> getUsers() {
//
    //    mDatabase.child("Users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
    //        @Override
    //        public void onComplete(@NonNull Task<DataSnapshot> task) {
    //            for (int i = 0; i < task.getResult().getValue(User.class).toString().length(); i++) {
    //                arrayList.add(task.getResult().getValue(User).toString().)
    //            }
//
//
    //            arrayList.add(task.getResult().getValue(User.class));
    //        }
    //    });
//
    //    return arrayList;
    //}


}
