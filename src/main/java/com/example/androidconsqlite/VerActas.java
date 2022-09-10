package com.example.androidconsqlite;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidconsqlite.databinding.ActivityVerActasBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VerActas extends AppCompatActivity {

    ActivityVerActasBinding binding ;
    StorageReference storageReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityVerActasBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.getimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(VerActas.this);
                progressDialog.setMessage("Cargando Acta...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String imageID = binding.etimageid.getText().toString();

                storageReference= FirebaseStorage.getInstance().getReference("actas/"+imageID+"");

                try {
                    File localfile = File.createTempFile("tempfile","");

                    storageReference.getFile(localfile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();

                                    Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                    binding.imageView.setImageBitmap(bitmap);



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    if(progressDialog.isShowing())
                                        progressDialog.dismiss();


                                    Toast.makeText(VerActas.this, "Fallo", Toast.LENGTH_SHORT).show();

                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


}