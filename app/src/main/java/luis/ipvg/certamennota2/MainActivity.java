package luis.ipvg.certamennota2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import luis.ipvg.certamennota2.Clases.FormGeo;
import luis.ipvg.certamennota2.Clases.elementosIncidencia;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button btRegistrar, btMapa, btSensor;
    RecyclerView rvHistorial;



    private void obtenerIncidencias() {

        DatabaseReference dbreferencia = FirebaseDatabase
                .getInstance("https://certamendb-default-rtdb.firebaseio.com/")
                .getReference("Form");

        dbreferencia.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                List<FormGeo> listaForms = new ArrayList<>();

                for (DataSnapshot incidencia : snapshot.getChildren()) {

                    String titulo = incidencia.child("titulo")
                            .getValue(String.class);

                    String descripcion = incidencia.child("descripcion")
                            .getValue(String.class);

                    FormGeo nuevo = new FormGeo();
                    nuevo.setTitulo(titulo);
                    nuevo.setDescripcion(descripcion);
                    listaForms.add(nuevo);


                }
                elementosIncidencia adapter = new elementosIncidencia(listaForms);

                rvHistorial.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                rvHistorial.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                android.util.Log.e("Firebase",
                        "Error al cargar historial de incidencias: " + error.getMessage());
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btRegistrar = findViewById(R.id.btRegistrar);
        btMapa = findViewById(R.id.btMapa);
        btSensor = findViewById(R.id.btSensor);
        rvHistorial = findViewById(R.id.rvHistorial);

        btRegistrar.setOnClickListener(v ->
                abrirActivity(FormActivity.class));

        btMapa.setOnClickListener(v ->
                abrirActivity(IncidenciaMapaActivity.class));

        btSensor.setOnClickListener(v ->
                abrirActivity(SensorActivity.class));

        obtenerIncidencias();




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() { //modificando el onresume para que actualice el recycleview
        super.onResume();
        obtenerIncidencias();
    }
    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }
}