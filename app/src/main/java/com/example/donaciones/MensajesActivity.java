package com.example.donaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.donaciones.Data.AdapterChats;
import com.example.donaciones.Data.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MensajesActivity extends AppCompatActivity {
    CircleImageView img_user;
    TextView username;
    ImageView iconectado,idesconectado;
    SharedPreferences mpref;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    Toolbar toolbar;
    EditText txt_mensaje;
    ImageButton btnenviar;
    String idchat_global;
    RecyclerView rv_chats;
    AdapterChats adapter;
    ArrayList<Chats> chatsArrayList;
    DatabaseReference ref_chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mpref = getApplicationContext().getSharedPreferences("usuario_sp",MODE_PRIVATE);
        img_user = findViewById(R.id.img_user);
        username = findViewById(R.id.tv_users);
        iconectado = findViewById(R.id.iconconectado);
        idesconectado = findViewById(R.id.icondesconectado);
        txt_mensaje = findViewById(R.id.txt_mensaje);
        btnenviar = findViewById(R.id.btnenviar);
        String usuario = getIntent().getExtras().getString("nombre");
        idchat_global = getIntent().getExtras().getString("idchat");
        String iduser = getIntent().getExtras().getString("iduser");

        username.setText(usuario);
        ref_chat = database.getReference();
        if(usuario.equals("Banco de alimentos")) {
            img_user.setImageResource(R.mipmap.banco_de_alimentos);
            System.out.println("ppp");
        }else{
            img_user.setImageResource(R.drawable.user);
        }
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msj = txt_mensaje.getText().toString();
                if(!msj.isEmpty()){
                    final Calendar c = Calendar.getInstance();
                    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    String idpush = ref_chat.push().getKey();
                    Chats chatmsj = new Chats(idpush,user.getUid(),iduser,msj,"no",dateFormat.format(c.getTime()),timeFormat.format(c.getTime()));
                    ref_chat.child("Chat").child(idchat_global).child(idpush).setValue(chatmsj);
                    Toast.makeText(MensajesActivity.this,"Mensaje enviado",Toast.LENGTH_SHORT).show();
                    txt_mensaje.setText("");

                }else{
                    Toast.makeText(MensajesActivity.this,"Mensaje vacio!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        //recicler view
        rv_chats = findViewById(R.id.rv);
        rv_chats.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        rv_chats.setLayoutManager(linearLayoutManager);
        chatsArrayList = new ArrayList<>();
        adapter = new AdapterChats(chatsArrayList, this);
        rv_chats.setAdapter(adapter);
        leerMensajes();


    }

    private void leerMensajes() {
        ref_chat.child("Chat").child(idchat_global).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    chatsArrayList.removeAll(chatsArrayList);
                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        Chats chat = snapshot.getValue(Chats.class);
                        chatsArrayList.add(chat);
                        deslizar();
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void deslizar() {
        rv_chats.scrollToPosition(adapter.getItemCount()-1);
    }
}