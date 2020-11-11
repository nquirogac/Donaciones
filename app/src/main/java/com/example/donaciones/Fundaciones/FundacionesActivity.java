package com.example.donaciones.Fundaciones;
import com.example.donaciones.Data.Fundacion;
import com.example.donaciones.Fundaciones.BancoAlimentosActivity;
import com.example.donaciones.HomeActivity;
import com.example.donaciones.R;
import com.example.donaciones.RopaActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FundacionesActivity extends AppCompatActivity {
    private TextView infoBA;
    private Button btnDonarBA;
    private Button btnDonarL;
    private Button btnDonarR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView infoBA=(TextView)findViewById(R.id.infoBA);
        setContentView(R.layout.activity_fundaciones);
        Button btnDonarBA =(Button)findViewById(R.id.btnDonarBA);
        btnDonarBA.setOnClickListener(this::irBA);
        Button btnDonaRL = (Button)findViewById(R.id.btnDonarL);
        btnDonaRL.setOnClickListener(this::irLibros);
        Button btnDonarR = (Button)findViewById(R.id.btnDonarR);
        btnDonaRL.setOnClickListener(this::irRopa);

        Fundacion bancoDeAlimentos = new Fundacion("Banco de Alimentos", "Alimentos en buen estado",
                "+(571) 244 0249\n" +"+(571) 404 9010", 4.620777, -74.089625 );
    infoBA.setText(bancoDeAlimentos.getTipodonacion()+"\n"+bancoDeAlimentos.getContacto());
    }
    public void irHome(View v){
        Intent intent = new Intent(getApplication(), HomeActivity.class);

        startActivity(intent);

    }
    public void irBA(View view){
        Intent intent = new Intent(this, BancoAlimentosActivity.class);

        startActivity(intent);

    }
    public void irLibros(View view){
        Intent intent = new Intent(this, LibrosActivity.class);

        startActivity(intent);
    }
    public void irRopa(View view){
        Intent intent = new Intent(this, RopaActivity.class);

        startActivity(intent);
    }

}