package com.example.donaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment UsuariosFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        UsuariosFragment = new UsuariosFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment,UsuariosFragment).commit();
    }
}