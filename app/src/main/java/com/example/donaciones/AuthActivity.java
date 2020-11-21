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

import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.ls.LSOutput;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailEt;
    private EditText passEt;
    private EditText nombreEt;
    private EditText telefonoEt;
    private EditText email2Et;
    private EditText pass2Et;
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
        nombreEt = (EditText) findViewById(R.id.nombreEt);
        telefonoEt =(EditText) findViewById(R.id.telefonoEt);
        email2Et = (EditText) findViewById(R.id.email2Et);
        pass2Et =(EditText) findViewById(R.id.pass2Et);
        Button btnSignup = (Button) findViewById(R.id.btnRegistro);
        Button btnlogin = (Button) findViewById(R.id.btnLogin);
        progressDialog = new ProgressDialog(this);
        btnSignup.setOnClickListener(this::onClick);
        btnlogin.setOnClickListener(this::onClick);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    private void registrarUsuario(){
        String nombre = nombreEt.getText().toString().trim();
        String telefono = telefonoEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(telefono)){
            Toast.makeText(this, "Ingrese su número de telefono", Toast.LENGTH_SHORT).show();
            return;
        }
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
                            //DATOS PARA LA BD
                            Map<String, Object> map = new HashMap<>();

                            map.put("email", email);
                            map.put("password", password);
                            map.put("nombre", nombre);
                            map.put("telefono", telefono);

                            String id = firebaseAuth.getCurrentUser().getUid();
                            System.out.println("este es el id"+id);
                            //CREAR EL USUARIO EN FIREBASE
                            mDatabase.child("Users").child("Donantes").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()){
                                        irHomeDatos();
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
        String datos;
        String email2 = email2Et.getText().toString().trim();
        String password2 = pass2Et.getText().toString().trim();

        if(TextUtils.isEmpty(email2)){
            Toast.makeText(this, "Ingrese un Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password2)){
            Toast.makeText(this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        //logear usuario
        firebaseAuth.signInWithEmailAndPassword(email2,password2)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AuthActivity.this,"Bienvenido: "+email2, Toast.LENGTH_SHORT).show();

                            irHomeDatos();
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
    public void irHomeDatos(){//OBTENER DATOS DE FIREBASE DEL USUARIO Y LLEVARLOS A HOME
        final String[] val = new String[3];
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

        String userID = firebaseAuth.getCurrentUser().getUid();

        // Agregamos un listener a la referencia
        ref.child("Users").child("Donantes").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String tel = dataSnapshot.child("telefono").getValue(String.class);
                    System.out.println(nombre+tel+email);
                    val[0] = nombre;
                    val[1] = email;
                    val[2] = tel;
                    Intent intention = new Intent(getApplication(), HomeActivity.class);
                    intention.putExtra("nombreUsuario",val[0].toString());
                    intention.putExtra("emailUsuario",val[1].toString());
                    intention.putExtra("telUsuario",val[2].toString());
                    startActivity(intention);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnRegistro:
                registrarUsuario();
                break;
            case R.id.btnLogin:
                logear();
                break;
        }
    }
}
