package com.example.instest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instest.DataService.FireBaseData;
import com.example.instest.DataService.DataService;
import com.example.instest.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {


    TextView username;
    TextView password;
    Button signIn;
    Button signUp;
    FireBaseData fireBaseData = new DataService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);




        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireBaseData.getmDatabase().child("Users").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        try {
                            for (int i = 0; i < Objects.requireNonNull(task.getResult().getValue()).toString().length(); i++) {
                                if (task.getResult().getValue().toString().contains(username.getText().toString())){
                                    if (Objects.requireNonNull(task.getResult().child(username.getText().toString()).getValue(User.class)).getPassword().contains(password.getText())){
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }
                        catch (Exception e){
                            e.getMessage();
                            Toast.makeText(getApplicationContext(),"Text is Empty", Toast.LENGTH_LONG).show();
                        }

                    }

                });

            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });



    }
}