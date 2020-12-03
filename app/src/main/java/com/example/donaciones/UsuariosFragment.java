package com.example.donaciones;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donaciones.Data.AdapterUsuarios;
import com.example.donaciones.Data.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UsuariosFragment extends Fragment {


    public UsuariosFragment() {
        // Required empty public constructor
    }



    String idUsuario, emailUsuario, telUsuario;
    FirebaseAuth firebaseAuth;
    DatabaseReference ref;
    TextView tv_user;
    ImageView img_user;
    RecyclerView rv;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container,false);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        idUsuario = user.getUid();
        tv_user = view.findViewById(R.id.tv_user);
        img_user = view.findViewById(R.id.imagenUser);
        obtenerDatos(idUsuario);

        ArrayList<Usuario> userArray;
        AdapterUsuarios adapter;
        LinearLayoutManager mLayout;
        mLayout = new LinearLayoutManager(getContext());
        mLayout.setStackFromEnd(true);
        rv = view.findViewById(R.id.rv);
        rv.setLayoutManager(mLayout);
        userArray = new ArrayList<>();
        adapter = new AdapterUsuarios(userArray,getContext());
        rv.setAdapter(adapter);
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();



        if(user.getEmail().equals("bancodealimentos@gmail.com")
                ||user.getEmail().equals("trilce@gmail.com")||
                user.getEmail().equals("minuto@gmail.com")){
            ref.child("Users").child("Donantes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        userArray.removeAll(userArray);
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Usuario usuario = snapshot.getValue(Usuario.class);
                            userArray.add(usuario);
                        }
                        adapter.notifyDataSetChanged();
                        }
                    }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Fallo la lectura: " + databaseError.getCode());
                }
            });

        }else {
            ref.child("Users").child("Fundaciones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            userArray.removeAll(userArray);
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Usuario usuario = snapshot.getValue(Usuario.class);
                                userArray.add(usuario);
                                System.out.println(userArray.size());
                            }
                            System.out.println(userArray.size());
                            adapter.notifyDataSetChanged();

                        }
                    }

                @Override
                public void onCancelled(DatabaseError error) {

                }
        });
        }
        return view;
    }

    private void obtenerDatos(String id) {
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();



        if(user.getEmail().equals("bancodealimentos@gmail.com")
                ||user.getEmail().equals("trilce@gmail.com")||
                user.getEmail().equals("minuto@gmail.com")){
            ref.child("Users").child("Fundaciones").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String nombre = dataSnapshot.child("nombre").getValue(String.class);
                        setDatos(nombre);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Fallo la lectura: " + databaseError.getCode());
                }
            });

        }else{
        ref.child("Users").child("Donantes").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    setDatos(nombre);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());
            }
        });
    }

}
    private void setDatos(String nombre) {
        tv_user.setText(nombre.toString());
        if(user.getEmail().equals("bancodealimentos@gmail.com")){
            img_user.setImageResource(R.mipmap.banco_de_alimentos);
        }if(user.getEmail().equals("trilce@gmail.com")){
            img_user.setImageResource(R.mipmap.danar_libros);
        }if(user.getEmail().equals("minuto@gmail.com")){
            img_user.setImageResource(R.mipmap.banco_de_ropa);
        }else{
            img_user.setImageResource(R.drawable.user);

        }
    }
    }