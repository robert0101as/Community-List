package com.example.androidconsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class inermediate extends AppCompatActivity {
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inermediate);

        tv1=(TextView) findViewById(R.id.tv1);
    }

    public void actividades(View v){
        Intent i = new Intent(this, Actividades.class );
        startActivity(i);
        Toast notificacion = Toast.makeText(this, "BIENVENIDO A LA SECCION DE ACTIVIDAD", Toast.LENGTH_LONG);
        notificacion.show();

    }

    public void comunidad(View v){
        Intent i = new Intent(this, Comunidad.class );
        startActivity(i);
        Toast notificacion = Toast.makeText(this, "BIENVENIDO A LA SECCION DE ASISTENCIA", Toast.LENGTH_LONG);
        notificacion.show();

    }

    public void actas(View v){
        Intent i = new Intent(this, SubirActas.class );
        startActivity(i);
        Toast notificacion = Toast.makeText(this, "BIENVENIDO A LA SECCION DE ACTAS", Toast.LENGTH_LONG);
        notificacion.show();

    }



    public void veractas(View v){
        Intent i = new Intent(this, VerActas.class );
        startActivity(i);
        Toast notificacion = Toast.makeText(this, "BIENVENIDO A LA SECCION DE VISUALIZACION DE ACTAS", Toast.LENGTH_LONG);
        notificacion.show();

    }


    public void salir(View v){
        Toast notificacion = Toast.makeText(this, "INGRESE LOS DATOS DE ACCESO", Toast.LENGTH_LONG);
        notificacion.show();
        finish();
    }
}