package com.example.instest.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.instest.Model.User;
import com.example.instest.R;
import com.example.instest.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    Button button;
    TextView textView1;
    EditText editText;
    private User user = new User("321890");


    private FragmentHomeBinding binding;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        button = root.findViewById(R.id.button3);
        textView1 = root.findViewById(R.id.text_home);
        editText = root.findViewById(R.id.editTextTextPersonName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("321839021890412");
                textView1.setText(editText.getText());
                button.setText("Clicked");
            }
        });



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}