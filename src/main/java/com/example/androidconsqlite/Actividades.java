package com.example.androidconsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Actividades extends AppCompatActivity {

    DatabaseReference mRootReference;
    EditText mactividad, mlugar, mfecha, mencargado,mhorario ;
    Button btnguardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);
        mactividad=(EditText)findViewById(R.id.etactividad);
        mlugar=(EditText)findViewById(R.id.etlugar);
        mfecha=(EditText)findViewById(R.id.etfecha);
        mencargado=(EditText)findViewById(R.id.etencargado);
        mhorario=(EditText)findViewById(R.id.etHorario);

        btnguardar=findViewById(R.id.btnsubir);
        mRootReference = FirebaseDatabase.getInstance().getReference();

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Actividad = mactividad.getText().toString();
                String Lugar = mlugar.getText().toString();
                String Fecha = mfecha.getText().toString();
                String Encargado = mencargado.getText().toString();
                String Horario = mhorario.getText().toString();



                Map<String, Object> datosActividad = new HashMap<>();
                datosActividad.put("Actividad", Actividad);
                datosActividad.put("Lugar", Lugar);
                datosActividad.put("Fecha", Fecha);
                datosActividad.put("Encargado", Encargado);
                datosActividad.put("Horario", Horario);

                mRootReference.child("actividad").push().setValue(datosActividad);

                Toast.makeText(Actividades.this, "SE INGRESO DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();



            }
        });



    }

    public void salir(View v) {
        Toast notificacion = Toast.makeText(this, "SELECCIONE UNA OPCION", Toast.LENGTH_LONG);
        notificacion.show();

        finish();

    }
}