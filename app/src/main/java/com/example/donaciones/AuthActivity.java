package com.example.donaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailEt;
    private EditText passEt;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    private Button btnlogin;
    //private FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        firebaseAuth = FirebaseAuth.getInstance();
        emailEt = (EditText) findViewById(R.id.emailEt);
        passEt =(EditText) findViewById(R.id.passEt);
        Button btnSignup = (Button) findViewById(R.id.btnLogout);
        Button btnlogin = (Button) findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        btnSignup.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    private void registrarUsuario(){
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();
        System.out.println(email+password);
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese un Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        //crear usuario
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AuthActivity.this,"Se ha registrado "+email+" correctamente", Toast.LENGTH_SHORT).show();

                            Map<String, Object> map = new HashMap<>();

                            map.put("email", email);
                            map.put("password", password);

                            String id = firebaseAuth.getCurrentUser().getUid();
                            mDatabase.child("Users").child("Donantes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        Intent intention = new Intent(getApplication(), HomeActivity.class);
                                        startActivity(intention);finish();
                                    }else{
                                        Toast.makeText(AuthActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(AuthActivity.this,"El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AuthActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                            }
                        progressDialog.dismiss();
                    }
                });
    }

    private void logear() {
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese un Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        //logear usuario
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AuthActivity.this,"Bienvenido: "+email, Toast.LENGTH_SHORT).show();
                            Intent intention = new Intent(getApplication(), HomeActivity.class);

                            startActivity(intention);


                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(AuthActivity.this,"El usuario ya existe", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AuthActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogout:
                registrarUsuario();
                break;
            case R.id.btnLogin:
                logear();
                break;
        }


    }
}
