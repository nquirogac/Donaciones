package com.example.donaciones.Fundaciones;
import com.example.donaciones.Data.Fundacion;
import com.example.donaciones.HomeActivity;
import com.example.donaciones.R;
import com.example.donaciones.WebsitesActivity;

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
    private  Button btnInfo1;
    private  Button btnInfo2;
    private  Button btnInfo3;
    private Fundacion bancoDeAlimentos;
    private Fundacion trilce;
    private Fundacion minuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView infoBA=(TextView)findViewById(R.id.infoBA);
        setContentView(R.layout.activity_fundaciones);
        Button btnDonarBA =(Button)findViewById(R.id.btnDonarBA);
        btnDonarBA.setOnClickListener(this::irBA);
        Button btnDonarL = (Button)findViewById(R.id.btnDonarL);
        btnDonarL.setOnClickListener(this::irLibros);
        Button btnDonarR = (Button)findViewById(R.id.btnDonarR);
        btnDonarR.setOnClickListener(this::irRopa);
        Button btnInfo1 = (Button)findViewById(R.id.btninfo1);
        btnInfo1.setOnClickListener(this::irAMasInfo);

        Fundacion bancoDeAlimentos = new Fundacion("Banco de Alimentos", "Alimentos en buen estado",
                "+(571) 244 0249\n" +"+(571) 404 9010", 4.620777, -74.089625, "https://www.bancodealimentos.org.co/" );
        Fundacion trilce = new Fundacion("Fundación Trilce","Libros, papel, discos, cd´s y juguetes, instrumentos musicales, entre otros.",
                "3162675691 ó 9216236", 4.703989, -74.056569, "https://www.fundaciontrilce.com/");
        Fundacion minuto = new Fundacion("Fundación minuto de Dios", "Ropa en buen estado","01 8000 946 223",
                4.702255, -74.090001,"https://www.minutodedios.org/programa/banco-de-ropa");

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
    public void irAMasInfo(View view){
        String url="";
        switch (view.getId()){
            case R.id.btninfo1:
                url = bancoDeAlimentos.getUrl();
                break;
            case R.id.btninfo2:
                url = trilce.getUrl();
                break;
            case  R.id.btninfo3:
                url = minuto.getUrl();
                break;
        }
        Intent intent = new Intent(this, WebsitesActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}