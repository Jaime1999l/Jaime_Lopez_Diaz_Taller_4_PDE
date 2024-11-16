package com.example.jaime_lopez_diaz_taller_4_pde.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jaime_lopez_diaz_taller_4_pde.R;
import com.example.jaime_lopez_diaz_taller_4_pde.activity.ConfiguracionActivity;
import com.example.jaime_lopez_diaz_taller_4_pde.activity.RedActivity;

public class BotonesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_botones, container, false);

        Button mainActivityButton = view.findViewById(R.id.mainActivityButton);
        Button configActivityButton = view.findViewById(R.id.configActivityButton);

        mainActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RedActivity.class);
            startActivity(intent);
        });

        configActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ConfiguracionActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
