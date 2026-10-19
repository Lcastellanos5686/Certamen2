package luis.ipvg.certamennota2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Spinner;
import android.widget.Toast;
import java.time.LocalDateTime;
import java.util.UUID;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import luis.ipvg.certamennota2.Clases.FormGeo;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;


public class FormActivity extends AppCompatActivity implements OnMapReadyCallback {

    EditText etTitulo;
    EditText etDescripcion;
    EditText etFecha;
    EditText etHora;
    Spinner spCategoria;
    Button btGuardar;

    LocalDate fechaSeleccionada = null;
    LocalTime horaSeleccionada = null;

    GoogleMap mMapa;

    double latitudSeleccionada;
    double longitudSeleccionada;

    boolean ubicacionSeleccionada = false;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;





    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance("https://certamendb-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMapa = googleMap;

        mMapa.setOnMapClickListener(latLng -> {

            latitudSeleccionada = latLng.latitude;
            longitudSeleccionada = latLng.longitude;
            ubicacionSeleccionada = true;

            mMapa.clear();

            mMapa.addMarker(
                    new MarkerOptions()
                            .position(latLng)
                            .title("Incidencia seleccionada")
            );
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mfMapa);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);}

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        spCategoria = findViewById(R.id.spCategoria);
        btGuardar = findViewById(R.id.btGuardar);
        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);

        inicializarFireBase();

        String[] categorias = {
                "Defecto",
                "Infraestructura",
                "Alumbrado público",
                "Vía pública"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categorias
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spCategoria.setAdapter(adapter);

        etFecha.setOnClickListener(v -> {
            LocalDate hoy = LocalDate.now();

            DatePickerDialog dialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        fechaSeleccionada = LocalDate.of(
                                year, month + 1, dayOfMonth
                        );

                        DateTimeFormatter formato =
                                DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        etFecha.setText(fechaSeleccionada.format(formato));
                    },
                    hoy.getYear(),
                    hoy.getMonthValue() - 1,
                    hoy.getDayOfMonth()
            );

            dialog.show();
        });

        etHora.setOnClickListener(v -> {
            LocalTime ahora = LocalTime.now();

            TimePickerDialog dialog = new TimePickerDialog(
                    this,
                    (view, hourOfDay, minute) -> {
                        horaSeleccionada = LocalTime.of(
                                hourOfDay, minute
                        );

                        DateTimeFormatter formato =
                                DateTimeFormatter.ofPattern("HH:mm");

                        etHora.setText(horaSeleccionada.format(formato));
                    },
                    ahora.getHour(),
                    ahora.getMinute(),
                    true
            );

            dialog.show();
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titulo = etTitulo.getText().toString().trim();
                String descricpcion = etDescripcion.getText().toString().trim();
                String hora = etHora.getText().toString().trim();
                String categoria = spCategoria.getSelectedItem().toString().trim();

                if (titulo.isEmpty() || hora.isEmpty()  || categoria.isEmpty() ||!ubicacionSeleccionada || fechaSeleccionada == null || horaSeleccionada == null) {
                    Toast.makeText(
                            FormActivity.this,
                            "Por favor llene todos los datos",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                LocalDateTime fechaHora = LocalDateTime.of(fechaSeleccionada, horaSeleccionada);

                FormGeo nuevoForm = new FormGeo();
                nuevoForm.setIdForm(UUID.randomUUID().toString());
                nuevoForm.setTitulo(titulo);
                if(!descricpcion.isEmpty()){
                    nuevoForm.setDescripcion(descricpcion);
                }
                nuevoForm.setCategoria(categoria);
                nuevoForm.setFormDateTime(fechaHora);
                nuevoForm.setLatitud(latitudSeleccionada);
                nuevoForm.setLongitud(longitudSeleccionada);

                databaseReference.child("Form")
                        .child(nuevoForm.getIdForm())
                        .setValue(nuevoForm)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(
                                    FormActivity.this,
                                    "Incidencia guardada correctamente",
                                    Toast.LENGTH_SHORT
                            ).show();


                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(
                                    FormActivity.this,
                                    "Error al guardar incidencia: " + e.getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        });
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}