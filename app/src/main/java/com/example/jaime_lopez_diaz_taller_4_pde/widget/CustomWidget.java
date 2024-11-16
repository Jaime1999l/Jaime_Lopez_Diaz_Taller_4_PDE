package com.example.jaime_lopez_diaz_taller_4_pde.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jaime_lopez_diaz_taller_4_pde.R;

public class CustomWidget extends LinearLayout implements SensorEventListener {

    private TextView sensorDataTextView;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    public CustomWidget(Context context) {
        super(context);
        init(context);
    }

    public CustomWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_layout, this, true);
        sensorDataTextView = findViewById(R.id.sensorDataTextView);

        // Configurar el sensor de acelerómetro
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    @SuppressLint("DefaultLocale")
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String sensorData = String.format("Acelerómetro:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z);
            sensorDataTextView.setText(sensorData);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se necesita implementar
    }

    public void registerSensorListener() {
        if (sensorManager != null && accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void unregisterSensorListener() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}

