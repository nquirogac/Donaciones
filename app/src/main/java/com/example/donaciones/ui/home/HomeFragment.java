package com.example.donaciones.ui.home;
import com.example.donaciones.Fundaciones.FundacionesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.donaciones.Fundaciones.FundacionesActivity;
import com.example.donaciones.R;

public class HomeFragment extends Fragment {
    private Button btnDonar;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.txtBienvenida);
        Button btnDonar = root.findViewById(R.id.btndonar);
        btnDonar.setOnClickListener(this::irAFundaciones);
        return root;
    }
    public void irAFundaciones(View v){
        Intent intent = new Intent(getActivity(), FundacionesActivity.class);

        startActivity(intent);

    }
}