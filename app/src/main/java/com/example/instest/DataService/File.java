package com.example.instest.DataService;

import android.content.Context;

import com.example.instest.Model.User;

import java.io.FileOutputStream;

public class File {

    String filename = "myfile";

    User user = new User("dada");

    FileOutputStream fileOutputStream;


    public void write(){
        try {

        }
        catch (Exception e){
            e.fillInStackTrace();
        }


    }


}
