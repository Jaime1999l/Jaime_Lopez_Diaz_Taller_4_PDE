package com.example.jaime_lopez_diaz_taller_4_pde.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jaime_lopez_diaz_taller_4_pde.R;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaludoFragment extends Fragment {

    private TextView greetingTextView;
    private ExecutorService executorService;
    private String lastGreeting = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saludo, container, false);
        greetingTextView = view.findViewById(R.id.greetingTextView);

        // Asegúrate de que greetingTextView no sea null
        if (greetingTextView == null) {
            System.err.println("Error: greetingTextView no encontrado.");
        }

        startGreetingChecker();
        return view;
    }

    private void startGreetingChecker() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            while (!executorService.isShutdown()) {
                String newGreeting = getGreetingMessage();
                if (!newGreeting.equals(lastGreeting)) {
                    lastGreeting = newGreeting;
                    requireActivity().runOnUiThread(() -> {
                        if (greetingTextView != null) {
                            updateGreeting(newGreeting); // Añadimos la animación
                            System.out.println("Saludo actualizado a: " + newGreeting);
                        } else {
                            System.err.println("greetingTextView es nulo.");
                        }
                    });
                }
                try {
                    Thread.sleep(calculateNextCheckDelay());
                } catch (InterruptedException e) {
                    executorService.shutdownNow();
                }
            }
        });
    }

    private long calculateNextCheckDelay() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 12) {
            return getMillisUntilNextHour(12);
        } else if (hour >= 12 && hour < 18) {
            return getMillisUntilNextHour(18);
        } else {
            return getMillisUntilNextHour(6);
        }
    }

    private long getMillisUntilNextHour(int targetHour) {
        Calendar now = Calendar.getInstance();
        Calendar next = (Calendar) now.clone();
        next.set(Calendar.HOUR_OF_DAY, targetHour);
        next.set(Calendar.MINUTE, 0);
        next.set(Calendar.SECOND, 0);
        if (next.before(now)) {
            next.add(Calendar.DAY_OF_MONTH, 1);
        }
        return next.getTimeInMillis() - now.getTimeInMillis();
    }

    private String getGreetingMessage() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour < 12) {
            return "¡Buenos días!";
        } else if (hour >= 12 && hour < 18) {
            return "¡Buenas tardes!";
        } else {
            return "¡Buenas noches!";
        }
    }

    private void updateGreeting(String greeting) {
        if (greetingTextView != null) {
            // Inicia transparente
            greetingTextView.setAlpha(0f);
            // Actualiza el texto
            greetingTextView.setText(greeting);
            // Aplica animación para hacerlo visible
            greetingTextView.animate()
                    .alpha(1f) // Cambia la opacidad a completamente visible
                    .setDuration(2000) // Duración de la animación (en milisegundos)
                    .start(); // Inicia la animación
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }
}
