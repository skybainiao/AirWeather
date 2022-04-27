package com.example.instest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instest.DataService.DataService;
import com.example.instest.DataService.FireBaseData;
import com.example.instest.Model.User;

public class SignUpActivity extends AppCompatActivity {

    FireBaseData data = new DataService();
    TextView username;
    TextView password;
    TextView passwordAgain;
    Button signUp;
    Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        passwordAgain = findViewById(R.id.editTextTextPasswordAgain);
        signUp = findViewById(R.id.signUp2);
        cancel = findViewById(R.id.cancel);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("") && !passwordAgain.getText().toString().equals("")){
                    if (password.getText().toString().equals(passwordAgain.getText().toString()) && !password.getText().toString().equals("")){
                        data.UploadUser(new User(username.getText().toString(),password.getText().toString()));
                        Toast.makeText(getApplicationContext(),"registration success", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"The two entered passwords do not match", Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Text is Empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}