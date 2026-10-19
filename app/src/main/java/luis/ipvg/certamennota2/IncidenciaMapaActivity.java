package luis.ipvg.certamennota2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.android.gms.maps.model.LatLng;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.SupportMapFragment;

public class IncidenciaMapaActivity extends AppCompatActivity  implements OnMapReadyCallback {

    GoogleMap mMapa;
    TextView tvTitulo, tvCategoria, tvDescripcion;

    private void obtenerIncidencias(GoogleMap googleMap) {

        DatabaseReference dbreferencia = FirebaseDatabase
                .getInstance("https://certamendb-default-rtdb.firebaseio.com/")
                .getReference("Form");

        dbreferencia.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot incidencia : snapshot.getChildren()) {

                    Double latitud = incidencia.child("latitud")
                            .getValue(Double.class);

                    Double longitud = incidencia.child("longitud")
                            .getValue(Double.class);

                    String titulo = incidencia.child("titulo")
                            .getValue(String.class);

                    String descripcion = incidencia.child("descripcion")
                            .getValue(String.class);

                    String categoria = incidencia.child("categoria")
                            .getValue(String.class);

                    ///////

                    LatLng ubicacion = new LatLng(latitud, longitud);

                    Marker marcador = googleMap.addMarker(
                            new MarkerOptions()
                                    .position(ubicacion)
                                    .title(titulo)
                    );

                    if (marcador != null) {
                        java.util.Map<String, String> datos = new java.util.HashMap<>();
                        datos.put("descripcion", descripcion);
                        datos.put("categoria", categoria);
                        marcador.setTag(datos);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                android.util.Log.e("Firebase",
                        "Error al cargar incidencias: " + error.getMessage());
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.mMapa = googleMap;

        obtenerIncidencias(googleMap);

        googleMap.setOnMarkerClickListener(marcador -> {

            String titulo = marcador.getTitle();
            String descripcion = "";
            String categoria = "";

            Object tag = marcador.getTag();

            if (tag instanceof java.util.Map) {
                java.util.Map<?, ?> datos = (java.util.Map<?, ?>) tag;

                descripcion = String.valueOf(datos.get("descripcion"));
                categoria = String.valueOf(datos.get("categoria"));
            }

            tvDescripcion.setText(descripcion);
            tvTitulo.setText(titulo);
            tvCategoria.setText(categoria);
            return true;
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_incidencia_mapa);

        tvCategoria = findViewById(R.id.tvCategoria);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mfMapa);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}