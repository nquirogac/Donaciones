package com.example.donaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class DonarTiempoActivity extends AppCompatActivity {
    private Button btnAcepto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donar_tiempo);
        Button btnAcepto = (Button) findViewById(R.id.btnAcepto);
        btnAcepto.setOnClickListener(this::aceptar);

    }

    public void aceptar(View v) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(DonarTiempoActivity.this);
        alerta.setMessage("Acepto recibir notificaciones de posibles donaciones")
                .setCancelable(false).setPositiveButton("Acepto", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseMessaging.getInstance().subscribeToTopic("Donadoresdetiempo").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DonarTiempoActivity.this, "Suscrito a donadores de tiempo", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
        ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Gracias por querer donar tu tiempo!");
        titulo.show();
    }

}