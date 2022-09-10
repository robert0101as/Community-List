package com.example.androidconsqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SubirActas extends AppCompatActivity {

private Button mUploadBtn;
private StorageReference mStorage;
private static final int GALLERY_INTENT=1;
private ImageView mImageview;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_actas);

        mStorage= FirebaseStorage.getInstance().getReference();


        mUploadBtn = (Button) findViewById(R.id.btnActas);


        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
                progressDialog = new ProgressDialog(SubirActas.this);
                progressDialog.setMessage("Subiendo Acta...");
                progressDialog.setCancelable(false);
                progressDialog.show();





            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GALLERY_INTENT && resultCode == RESULT_OK){

            Uri uri=data.getData();
            StorageReference filePath=mStorage.child("actas").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(SubirActas.this,"Se Subio exitosamente el acta.",Toast.LENGTH_SHORT).show();
                }
            });
        }




    }
}