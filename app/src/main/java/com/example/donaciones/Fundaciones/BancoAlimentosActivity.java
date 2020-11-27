package com.example.donaciones.Fundaciones;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donaciones.AuthActivity;
import com.example.donaciones.Data.Donacion;
import com.example.donaciones.Data.Fundacion;
import com.example.donaciones.HomeActivity;
import com.example.donaciones.MapsActivity;
import com.example.donaciones.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.PrimitiveIterator;

public class BancoAlimentosActivity extends AppCompatActivity {
private Spinner opTransporte;
private Button btnHacerDon;
private EditText txtDonacionBA;
private EditText txtDetalles;
private Fundacion bancoDeAlimentos;

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
    }

    private void validarDonacion(View view) {
        String donacion = txtDonacionBA.getText().toString().trim();
        String detalles = txtDetalles.getText().toString().trim();
        String transporte = opTransporte.getSelectedItem().toString().trim();
        System.out.println(transporte);
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String fecha = formateador.format(date);
        Donacion nuevaDon = new Donacion(donacion, fecha,detalles,transporte,"Procesando", bancoDeAlimentos);
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
                            nuevaDon.setEstado("Finalizada");
                            intention.putExtra("lat",4.620777);
                            intention.putExtra("lon", -74.089625);
                            startActivity(intention);
                        }
                    }
            ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    nuevaDon.setEstado("En espera");
                }
            });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Donación procesada exitosamente!");
            titulo.show();
        }
    }

}