package com.example.donaciones;
import com.example.donaciones.Data.Fundacion;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FundacionesActivity extends AppCompatActivity {
    private TextView infoBA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView infoBA=(TextView)findViewById(R.id.infoBA);
        setContentView(R.layout.activity_fundaciones);
        Fundacion bancoDeAlimentos = new Fundacion("Banco de Alimentos", "Alimentos en buen estado",
                "+(571) 244 0249\n" +"+(571) 404 9010", 4.620777, -74.089625 );
    infoBA.setText(bancoDeAlimentos.getTipodonacion()+"\n"+bancoDeAlimentos.getContacto());
    }
    public void irHome(View v){
        Intent intent = new Intent(getApplication(),HomeActivity.class);

        startActivity(intent);

    }
}