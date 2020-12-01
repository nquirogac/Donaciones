package com.example.donaciones.Fundaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.donaciones.Fcm;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.donaciones.AuthActivity;
import com.example.donaciones.Data.Donacion;
import com.example.donaciones.Data.Fundacion;
import com.example.donaciones.HomeActivity;
import com.example.donaciones.MapsActivity;
import com.example.donaciones.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class BancoAlimentosActivity extends AppCompatActivity {
private Spinner opTransporte;
private Button btnHacerDon;
private EditText txtDonacionBA;
private EditText txtDetalles;
private Fundacion bancoDeAlimentos;
private DatabaseReference databaseReference;
private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_banco_alimentos);
        opTransporte= (Spinner)findViewById(R.id.opTransporte);
        btnHacerDon= (Button) findViewById(R.id.btnHacerDon);
        txtDonacionBA = (EditText)findViewById(R.id.txtdonacionBA);
        txtDetalles = (EditText)findViewById(R.id.txtDetalles);
        Fundacion bancoDeAlimentos = (Fundacion) getIntent().getSerializableExtra("fundacion") ;
        btnHacerDon.setOnClickListener(this::validarDonacion);
        bancoDeAlimentos.setLatitud(4.620777);
        bancoDeAlimentos.setLongitud(-74.089625);
        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.Transportes, R.layout.support_simple_spinner_dropdown_item);
        opTransporte.setAdapter(adp);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void validarDonacion(View view) {

        String donacion = txtDonacionBA.getText().toString().trim();
        String detalles = txtDetalles.getText().toString().trim();
        String transporte = opTransporte.getSelectedItem().toString().trim();
        System.out.println(transporte);
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String fecha = formateador.format(date);

        if(TextUtils.isEmpty(donacion)||TextUtils.isEmpty(detalles)){
            Toast.makeText(this, "Complete todos los espacios", Toast.LENGTH_SHORT).show();
            return;

        }if(transporte.equals("Opciones de transporte")){
            Toast.makeText(this, "Escoja una opción de transporte para su donación", Toast.LENGTH_SHORT).show();
            return;
        }if(transporte.equals("Puedo llevar la donación")){
            AlertDialog.Builder alerta = new AlertDialog.Builder(BancoAlimentosActivity.this);
            alerta.setMessage("Ahora puedes llevar tu donación a la fundación.")
                    .setCancelable(false).setPositiveButton("Iniciar viaje", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intention = new Intent(getApplication(), MapsActivity.class);

                            intention.putExtra("lat",4.620777);
                            intention.putExtra("lon", -74.089625);
                            startActivity(intention);
                        }
                    }
            ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Donación procesada exitosamente!");
            titulo.show();
        }if(transporte.equals("No puedo llevar la donación")){
            AlertDialog.Builder alerta1 = new AlertDialog.Builder(BancoAlimentosActivity.this);
            alerta1.setMessage("Desea contactar con un donante de tiempo para que transporte su donación?")
                    .setCancelable(false).setPositiveButton("Si, acepto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            lanzarNot();
                            subirDonacion();
                            AlertDialog.Builder alerta2 = new AlertDialog.Builder(BancoAlimentosActivity.this);
                            alerta2.setMessage("Espere a que un voluntario acepte transportar su donacion.")
                                    .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }}
                            );
                            AlertDialog titulo2 = alerta2.create();
                            titulo2.setTitle("Hemos notificado a los donantes de tiempo sobre su solicitud");
                            titulo2.show();
                        }
                    }

            ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();

                }
            });
            AlertDialog titulo = alerta1.create();
            titulo.setTitle("Donación en proceso");
            titulo.show();


            }
        }

    private void lanzarNot(){
        RequestQueue mRequestQue = Volley.newRequestQueue(this);

        JSONObject json = new JSONObject();
        try {
            json.put("to", "/topics/" + "Donadoresdetiempo");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", "new Order");
            notificationObj.put("body", "New order from : " );
            //replace notification with data when went send data
            json.put("notification", notificationObj);

            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    response -> Log.d("MUR", "onResponse: "),
                    error -> Log.d("MUR", "onError: " + error.networkResponse)
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=key=AAAAJ7jui4I:APA91bGeJlWXP5Am-itZilGlMEvc1HvYhWQoW1Oex06YXwRSd903rRgD7AGlZXLzsCMNe84_xa5TYryh4emXXtsgO-iOE6nr7_yyuzKcfcgAwiKcqRZ5PWGX6WJ2_o618n4Dp_JXkzMI");
                    return header;
                }
            };
            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

}
    public void subirDonacion(){
        String donacion = txtDonacionBA.getText().toString().trim();
        String detalles = txtDetalles.getText().toString().trim();
        String transporte = opTransporte.getSelectedItem().toString().trim();
        String id = firebaseAuth.getCurrentUser().getUid();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String fecha = formateador.format(date);
        databaseReference.child("Users").child("Donantes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String latD = String.valueOf(dataSnapshot.child("ubicacion").child("latitud").getValue(Double.class));
                    String lonD = String.valueOf(dataSnapshot.child("ubicacion").child("longitud").getValue(Double.class));
                    Map<String, Object> map = new HashMap<>();

                    map.put("nombre", nombre);
                    map.put("donacion", donacion);
                    map.put("detalle", detalles);
                    map.put("fecha", fecha);
                    map.put("opTransporte", transporte);
                    map.put("fundacion", "Banco de alimentos");
                    map.put("latitudUsuario", latD);
                    map.put("longitudUsuario", lonD);
                    map.put("latitudFundacion", "4.620777");
                    map.put("longitudFundacion", "-74.089625");

                    databaseReference.child("Donaciones_pendientes").child(id).setValue(map);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Fallo la lectura: " );
            }
        });
    }
}