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



public class Comunidad extends AppCompatActivity {

    DatabaseReference mRootReference;

    EditText  mcomunidad, mcedula, mnombres,mapellidos ,masistecia;
    Button btnAgregar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);


        mcomunidad=(EditText)findViewById(R.id.etComunidad);
        mcedula=(EditText)findViewById(R.id.etcedu);
        mnombres=(EditText)findViewById(R.id.etNombres);
        mapellidos=(EditText)findViewById(R.id.etapellidos);
        masistecia =(EditText)findViewById(R.id.etAsistencia);

        btnAgregar=findViewById(R.id.btnGuardar);
        mRootReference = FirebaseDatabase.getInstance().getReference();



        btnAgregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String Comunidad = mcomunidad.getText().toString();
                int Cedula = Integer.parseInt(mcedula.getText().toString());
                String Nombres = mnombres.getText().toString();
                String Apellidos = mapellidos.getText().toString();
                String Asistencia = masistecia.getText().toString();



                Map<String, Object> datosComunidad = new HashMap<>();

                datosComunidad.put("Comunidad", Comunidad);
                datosComunidad.put("Cedula", Cedula);
                datosComunidad.put("Nombres", Nombres);
                datosComunidad.put("Apellidos", Apellidos);
                datosComunidad.put("Asistencia", Asistencia);

                mRootReference.child("asistencia").push().setValue(datosComunidad);

                Toast.makeText(Comunidad.this, "SE INGRESO DATOS CORRECTAMENTE", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void salir(View v) {
        Toast notificacion = Toast.makeText(this, "SELECCIONE UNA OPCION", Toast.LENGTH_LONG);
        notificacion.show();

        finish();

    }

    }













