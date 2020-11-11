package com.example.donaciones.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.donaciones.AuthActivity;
import com.example.donaciones.R;
import com.google.firebase.auth.AuthResult;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class LogoutFragment extends Fragment {

private FirebaseAuth firebaseAuth;

private TextView txtuser;
private Button btnlogout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnlogout = this.btnlogout.findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(this::logout);
    }

    public void logout(View v){
        Intent salir = new Intent(getActivity(), AuthActivity.class);

        startActivity(salir);
        firebaseAuth.signOut();
    }


}