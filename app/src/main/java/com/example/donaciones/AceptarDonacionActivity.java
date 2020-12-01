package com.example.donaciones;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donaciones.Data.Donacion;
import com.example.donaciones.Fundaciones.BancoAlimentosActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AceptarDonacionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FirebaseAuth firebaseAuth;
    private TextView txtNombre, txtDonacion, txtDetalle, txtFecha, txtFundacion;
    public Double lat1,lon1,lat2,lon2;
    GoogleMap googleMap;
    MapView mapView;
    Button btnAceptar,btnRechazar;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceptar_donacion);

        firebaseAuth = FirebaseAuth.getInstance();
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtDonacion = (TextView) findViewById(R.id.txtDonacion);
        txtDetalle = (TextView) findViewById(R.id.txtDetalle);
        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtFundacion = (TextView) findViewById(R.id.txtFundacion);
        btnAceptar =(Button)findViewById(R.id.btnAceptar);
        btnRechazar=(Button)findViewById(R.id.btnRechazar);
        imagen =(ImageView)findViewById(R.id.imageView10);
        lat1= Double.parseDouble("4.6848");
        lon1= Double.parseDouble("-74.105195");
        lat2= Double.parseDouble("4.620777");
        lon2= Double.parseDouble("-74.089625");
        obtenerNodo();
        mapView = (MapView) findViewById(R.id.miMapa);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnAceptar.setOnClickListener(this::aceptar);
        btnRechazar.setOnClickListener(this::rechazar);


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("MapDebug", "onMapReady: map is showing on the screen");
        if(googleMap != null){

            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat1,lon1))
                            .title("Fundación"));
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat2,lon2))
                    .title("Donador"));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat1,lon1))
                    .title("Fundación")).getPosition());

            builder.include(googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat2,lon2))
                    .title("Donador")).getPosition());
        LatLngBounds bounds = builder.build();

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200, 200, 5);

        googleMap.animateCamera(cu);
        }
    }
    public void obtenerNodo(){//OBTENER DATOS DE FIREBASE
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

        // Agregamos un listener a la referencia
        ref.child("Donaciones_pendientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String nodo = dataSnapshot.getKey();

                    obtenerDatos(nodo);

                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("error");
            }
        });
    }
    public void obtenerDatos(String nodo){
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Donaciones_pendientes").child(nodo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String[] p = new String[4];
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String donacion = dataSnapshot.child("donacion").getValue(String.class);
                    String detalle = dataSnapshot.child("detalle").getValue(String.class);
                    String fecha = dataSnapshot.child("fecha").getValue(String.class);
                    String fundacion = dataSnapshot.child("fundacion").getValue(String.class);
                    String latD = dataSnapshot.child("latitudUsuario").getValue(String.class);
                    String lonD = dataSnapshot.child("longitudUsuario").getValue(String.class);
                    String latF = dataSnapshot.child("latitudFundacion").getValue(String.class);
                    String lonF = dataSnapshot.child("longitudFundacion").getValue(String.class);
                    p[0]=latD;
                    p[1]= lonD;
                    p[2]= latF;
                    p[3]= lonF;
                    setearDatos(nombre,donacion,detalle,fecha,fundacion);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());
            }
        });

    }
    public void setearDatos(String nombre, String donacion, String detalle, String fecha
            , String fundacion){

        txtNombre.setText(nombre);
        txtDonacion.setText(donacion);
        txtDetalle.setText(detalle);
        txtFecha.setText(fecha);
        txtFundacion.setText(fundacion);
        if(fundacion.equals("Banco de alimentos")){
            imagen.setImageAlpha(R.mipmap.banco_de_alimentos);
        }if(fundacion.equals("Fundación Trilce")){
            imagen.setImageAlpha(R.mipmap.danar_libros);
        }if(fundacion.equals("Fundación minuto de Dios")){
            imagen.setImageAlpha(R.mipmap.banco_de_ropa);
        }
    }

    public void aceptar(View view){
        AlertDialog.Builder alerta = new AlertDialog.Builder(AceptarDonacionActivity.this);
        alerta.setMessage("Ahora puedes empezar tu donación de tiempo")
                .setCancelable(false).setPositiveButton("Iniciar viaje", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }
        ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
    });
    }
    public void rechazar(View view){
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

        // Agregamos un listener a la referencia
        ref.child("Donaciones_pendientes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String nodo = dataSnapshot.getKey();

                    DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference().child("peticiones");
                    DatabaseReference currentUserBD = mDatabase.child(nodo);
                    currentUserBD.removeValue();

                    break;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("error");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onSaveInstanceState(Bundle outstate, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outstate, outPersistentState);
        mapView.onSaveInstanceState(outstate);
    }



}