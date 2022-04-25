package com.example.instest.ui;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.instest.MainActivity;
import com.example.instest.Model.User;
import com.example.instest.R;
import com.example.instest.ViewModel.NotificationsViewModel;
import com.example.instest.databinding.FragmentNotificationsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    Button chooseImg, uploadImg,uploadData;
    ImageView imgView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        chooseImg = root.findViewById(R.id.chooseImg);
        uploadImg = root.findViewById(R.id.uploadImg);
        uploadData = root.findViewById(R.id.uploadData);
        imgView = root.findViewById(R.id.imgView);

        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading....");

        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User("chen", "dsajio");
                notificationsViewModel.UploadUser(user);
            }
        });

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });


        //upload File
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child(getEntryName(filePath.getPath()));

                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }





    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //get FileName
    public static String getEntryName(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            int start = picturePath.lastIndexOf("/");
            String format = "";
            if (start < 0) {
                format = picturePath;
            } else {
                format = picturePath.substring(start + 1);
            }
            return format;
        }
        return "";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}