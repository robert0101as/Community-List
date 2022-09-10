package com.example.androidconsqlite;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.androidconsqlite.databinding.ActivityInformacionBinding;

public class Informacion extends FragmentActivity implements OnMapReadyCallback {
    private Button btnAnimar;

    private GoogleMap mMap;
    private ActivityInformacionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInformacionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0.04084, -78.14524);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marcador Cayambe"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        btnAnimar = (Button)findViewById(R.id.btnAnimar);
        btnAnimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animarCayambe();
            }
        });
    }
    private void animarCayambe()
    {
        LatLng Cayambe = new LatLng(0.04084, -78.14524);

        CameraPosition camPos = new CameraPosition.Builder()
                .target(Cayambe)   //Centramos el mapa en Cayambe
                .zoom(15)         //Establecemos el zoom en 19
                .bearing(50)      //Establecemos la orientación con el noreste arriba
                .tilt(65)         //Bajamos el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);

        mMap.animateCamera(camUpd3);
    }


}
