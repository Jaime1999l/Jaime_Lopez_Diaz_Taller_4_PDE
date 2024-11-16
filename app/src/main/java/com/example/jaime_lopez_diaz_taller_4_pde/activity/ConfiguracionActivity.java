package com.example.jaime_lopez_diaz_taller_4_pde.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.jaime_lopez_diaz_taller_4_pde.R;
import com.example.jaime_lopez_diaz_taller_4_pde.util.BackgroundUtil;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "ConfiguracionActivity";
    private static final float SHAKE_THRESHOLD = 8.0f; // Sensibilidad al movimiento
    private static final int SHAKE_RESET_TIME = 300; // Tiempo entre agitaciones (ms)

    private BackgroundUtil backgroundUtil;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastShakeTime = 0;

    private RadioGroup radioGroupBackgroundType;
    private LinearLayout colorSelectionLayout;
    private LinearLayout imageSelectionLayout;

    private List<CardView> colorButtons; // Lista para almacenar botones de color
    private List<CardView> imageButtons; // Lista para almacenar botones de imagen
    private int currentIndex = 0; // Índice actual en la lista activa
    private boolean isColorMode = true; // Determina si está en modo "Color" o "Imagen"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        backgroundUtil = new BackgroundUtil(this);

        // Aplicar el fondo al iniciar la configuración
        backgroundUtil.applyBackground(this);

        // Inicializar elementos UI
        radioGroupBackgroundType = findViewById(R.id.radioGroupBackgroundType);
        colorSelectionLayout = findViewById(R.id.colorSelectionLayout);
        imageSelectionLayout = findViewById(R.id.imageSelectionLayout);

        // Inicializar botones de colores
        colorButtons = new ArrayList<>();
        colorButtons.add(findViewById(R.id.colorRedButton));
        colorButtons.add(findViewById(R.id.colorGreenButton));
        colorButtons.add(findViewById(R.id.colorBlueButton));

        // Inicializar botones de imágenes
        imageButtons = new ArrayList<>();
        imageButtons.add(findViewById(R.id.image1Button));
        imageButtons.add(findViewById(R.id.image2Button));

        // Configuración de clic en botones de colores
        for (CardView button : colorButtons) {
            button.setOnClickListener(v -> {
                int color = getColorFromButton(button);
                setColorAndReturn(color);
            });
        }

        // Configuración de clic en botones de imágenes
        for (CardView button : imageButtons) {
            button.setOnClickListener(v -> {
                int imageResId = getImageFromButton(button);
                setImageAndReturn(imageResId);
            });
        }

        // Manejo del RadioGroup para alternar entre "Color" e "Imagen"
        radioGroupBackgroundType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonColor) {
                isColorMode = true;
                colorSelectionLayout.setVisibility(LinearLayout.VISIBLE);
                imageSelectionLayout.setVisibility(LinearLayout.GONE);
                Log.d(TAG, "Modo cambiado a: Colores");
            } else if (checkedId == R.id.radioButtonImage) {
                isColorMode = false;
                colorSelectionLayout.setVisibility(LinearLayout.GONE);
                imageSelectionLayout.setVisibility(LinearLayout.VISIBLE);
                Log.d(TAG, "Modo cambiado a: Imágenes");
            }
        });

        // Configuración del botón de volver
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Inicialización del sensor de acelerómetro
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private int getColorFromButton(CardView button) {
        if (button.getId() == R.id.colorRedButton) {
            return Color.RED;
        } else if (button.getId() == R.id.colorGreenButton) {
            return Color.GREEN;
        } else if (button.getId() == R.id.colorBlueButton) {
            return Color.BLUE;
        }
        return Color.WHITE; // Valor predeterminado
    }

    private int getImageFromButton(CardView button) {
        if (button.getId() == R.id.image1Button) {
            return R.drawable.background_image1;
        } else if (button.getId() == R.id.image2Button) {
            return R.drawable.background_image2;
        }
        return 0; // Valor predeterminado
    }

    private void setColorAndReturn(int color) {
        SharedPreferences.Editor editor = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit();
        editor.putString("backgroundType", "color");
        editor.putInt("backgroundValue", color);
        editor.apply();
        Log.d(TAG, "Color background saved: " + color);
        backgroundUtil.applyBackground(this);
    }

    private void setImageAndReturn(int imageResId) {
        SharedPreferences.Editor editor = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).edit();
        editor.putString("backgroundType", "image");
        editor.putInt("backgroundValue", imageResId);
        editor.apply();
        Log.d(TAG, "Image background saved: " + imageResId);
        backgroundUtil.applyBackground(this);
    }

    // Métodos del sensor
    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la magnitud del movimiento
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;

            if (acceleration > SHAKE_THRESHOLD) {
                long currentTime = System.currentTimeMillis();
                if ((currentTime - lastShakeTime) > SHAKE_RESET_TIME) {
                    lastShakeTime = currentTime;
                    moveToNextButton();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void moveToNextButton() {
        // Determinar qué lista de botones usar
        List<CardView> activeButtons = isColorMode ? colorButtons : imageButtons;

        // Incrementar el índice y seleccionar el siguiente botón
        currentIndex = (currentIndex + 1) % activeButtons.size();

        // Simular clic en el botón actual
        CardView nextButton = activeButtons.get(currentIndex);
        nextButton.performClick();

        Log.d(TAG, "Botón seleccionado: " + nextButton.getId());
    }
}
