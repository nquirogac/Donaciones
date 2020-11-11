package com.example.donaciones.Fundaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.donaciones.R;

public class LibrosActivity extends AppCompatActivity {
private Spinner opTansporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);
        Spinner opTransporte=(Spinner)findViewById(R.id.opTransporte);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.Transportes, R.layout.support_simple_spinner_dropdown_item);
        opTransporte.setAdapter(adp);
    }
}