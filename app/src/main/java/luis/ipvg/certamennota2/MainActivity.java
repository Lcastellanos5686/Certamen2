package luis.ipvg.certamennota2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btRegistrar, btMapa, btSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btRegistrar = findViewById(R.id.btRegistrar);
        btMapa = findViewById(R.id.btMapa);
        btSensor = findViewById(R.id.btSensor);

        btRegistrar.setOnClickListener(v ->
                abrirActivity(FormActivity.class));

        btMapa.setOnClickListener(v ->
                abrirActivity(IncidenciaMapaActivity.class));

        btSensor.setOnClickListener(v ->
                abrirActivity(SensorActivity.class));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }
}