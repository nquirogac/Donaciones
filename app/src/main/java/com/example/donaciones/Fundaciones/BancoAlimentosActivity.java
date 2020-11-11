package com.example.donaciones.Fundaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.donaciones.R;

import java.util.ArrayList;

public class BancoAlimentosActivity extends AppCompatActivity {
private Spinner opTransporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_alimentos);
        Spinner opTransporte=(Spinner)findViewById(R.id.opTransporte);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.Transportes, R.layout.support_simple_spinner_dropdown_item);
        opTransporte.setAdapter(adp);
    }
}