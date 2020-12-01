package com.example.donaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Fcm extends FirebaseMessagingService {
    List list = new ArrayList();

    public PendingIntent clickNoti(){
        Intent intent = new Intent(getApplicationContext(), AceptarDonacionActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return  PendingIntent.getActivity(this,0,intent,0);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();
        if(remoteMessage.getData().size()>0){
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("detalle");

            getNoti(titulo,detalle);
        }
    }

    public void getNoti(String tit, String det){
        obtenerNodo();
        String id = "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id, "nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert  nm != null;
            nm.createNotificationChannel(nc);
        }
        builder.setAutoCancel(true).setWhen(System.currentTimeMillis())
                .setContentTitle(tit).setSmallIcon(R.mipmap.icono_ayuda)
                .setContentText(det).setContentIntent(clickNoti()).setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(80000);

        assert  nm != null;
        nm.notify(idNotify, builder.build());
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
                if (dataSnapshot.exists()) {
                    String latD = dataSnapshot.child("latitudUsuario").getValue(String.class);
                    String lonD = dataSnapshot.child("longitudUsuario").getValue(String.class);
                    String latF = dataSnapshot.child("latitudFundacion").getValue(String.class);
                    String lonF = dataSnapshot.child("longitudFundacion").getValue(String.class);
                    String[] puntos = new String[4];
                    puntos[0] = latD;
                    puntos[1] = lonD;
                    puntos[2] = latF;
                    puntos[3] = lonF;
                    aver(puntos);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void aver(String[] lista) {
        //lista largaaa que coja los primeros 4

        for (String punto:lista) {
            list.add(punto);
        }
    }
}