package com.example.donaciones.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.TextView;

import android.widget.Button;

import com.example.donaciones.AuthActivity;
import com.example.donaciones.R;

//import com.firebase.ui.auth.AuthUI;

import com.google.firebase.auth.FirebaseAuth;


public class LogoutFragment extends Fragment {

private FirebaseAuth firebaseAuth;

private TextView txtuser;
private Button btnlogout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnlogout = this.btnlogout.findViewById(R.id.btnRegistro);
        btnlogout.setOnClickListener(this::logout);
    }

    public void logout(View v){
        Intent salir = new Intent(getActivity(), AuthActivity.class);

        startActivity(salir);
        firebaseAuth.signOut();
    }


}