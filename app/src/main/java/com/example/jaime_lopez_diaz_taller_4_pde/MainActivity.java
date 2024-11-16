package com.example.jaime_lopez_diaz_taller_4_pde;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jaime_lopez_diaz_taller_4_pde.fragments.BotonesFragment;
import com.example.jaime_lopez_diaz_taller_4_pde.fragments.SaludoFragment;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Aplicar el fondo desde SharedPreferences
        applyBackgroundFromPreferences();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerSaludo, new SaludoFragment())
                    .replace(R.id.fragmentContainerBotones, new BotonesFragment())
                    .commit();
        }
    }

    private void applyBackgroundFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String backgroundType = sharedPreferences.getString("backgroundType", "color");
        int backgroundValue = sharedPreferences.getInt("backgroundValue", Color.WHITE);

        View rootView = getWindow().getDecorView().getRootView();

        if ("color".equals(backgroundType)) {
            rootView.setBackgroundColor(backgroundValue);
        } else if ("image".equals(backgroundType)) {
            rootView.setBackgroundResource(backgroundValue);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reaplicar el fondo cada vez que la actividad se reanude
        applyBackgroundFromPreferences();
    }
}
