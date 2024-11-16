package com.example.jaime_lopez_diaz_taller_4_pde;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jaime_lopez_diaz_taller_4_pde.fragments.BotonesFragment;
import com.example.jaime_lopez_diaz_taller_4_pde.fragments.SaludoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Agregar los fragmentos al inicio
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerSaludo, new SaludoFragment())
                    .replace(R.id.fragmentContainerBotones, new BotonesFragment())
                    .commit();
        }
    }
}
