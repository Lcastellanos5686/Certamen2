package luis.ipvg.certamennota2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager mgSensor;
    Sensor acelerometro;
    TextView tvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sensor);

        tvLista = findViewById(R.id.sensorsList);
        mgSensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = mgSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (acelerometro != null) {
            mgSensor.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        tvLista.setText(String.format("Acelerómetro X: %f Y: %f Z: %f", x, y, z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { //sin esto muere
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgSensor.unregisterListener(this);
    }
}