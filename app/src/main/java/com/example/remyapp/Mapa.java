package com.example.remyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle; // Importa la clase Bundle para guardar el estado de la actividad.
import android.preference.PreferenceManager; // Importa PreferenceManager para gestionar las preferencias de la aplicación.
import android.view.View; // Importa la clase View para manejar vistas en la interfaz.
import android.widget.AdapterView; // Importa AdapterView para manejar eventos en vistas adaptables.
import android.widget.ArrayAdapter; // Importa ArrayAdapter para adaptar arrays a vistas como Spinners.
import android.widget.Spinner; // Importa Spinner, que es una lista desplegable en Android.
import org.osmdroid.config.Configuration; // Importa la clase Configuration para configurar el mapa.
import org.osmdroid.tileprovider.tilesource.TileSourceFactory; // Importa TileSourceFactory para definir los tipos de mapas disponibles.
import org.osmdroid.tileprovider.tilesource.XYTileSource; // Importa XYTileSource para crear un proveedor de azulejos específico para mapas personalizados.
import org.osmdroid.util.GeoPoint; // Importa GeoPoint, que representa coordenadas geográficas (latitud y longitud).
import org.osmdroid.views.MapView; // Importa MapView, que es el componente visual del mapa.
import org.osmdroid.views.overlay.Marker; // Importa Marker para agregar marcadores en el mapa.
import org.osmdroid.views.overlay.Polyline; // Importa Polyline para dibujar líneas en el mapa.


public class Mapa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        //Carga la configuración del mapa usando las preferencias predeterminadas
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        // Obtiene la referencia al componente mapView del layout.
        MapView mapView = findViewById(R.id.mapView);
        // Establece la fuente de azulejos del mapa a MAPNIK (mapa estándar)
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        // Activa los controles de zoom en el mapa.
        mapView.setBuiltInZoomControls(true);
        // Activa el control multitactil para el mapa (zoom con dos dedos)
        mapView.setMultiTouchControls(true);

        //Coordenadas del Metro Los Heroes
        double metroLatitud = -33.44615; //Latitud
        double metroLongitud = -70.6629549; //Longitud

        //Crear objetos GeoPoint para las coordenadas definidas
        GeoPoint metroPoint = new GeoPoint(metroLatitud, metroLongitud);
        //Configura la vista inicial del mapa
        mapView.getController().setZoom(15.0);
        //Centra el mapa en el punto de Santiago
        mapView.getController().setCenter(metroPoint);

        //Crear un marcador para el Metro Los Heroes y creamos un marcdor en el Mapa
        Marker marcadorMetro = new Marker(mapView);
        //Establece la posición del marcador en el punto del metro
        marcadorMetro.setPosition(metroPoint);
        //Establece el ancla del marcador
        marcadorMetro.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        //Establece el título del marcador
        marcadorMetro.setTitle("Metro Los Héroes, Chile");
        //Establece una descripción para el marcador
        marcadorMetro.setSnippet("Un metro como cualquier otro");
        //Agregar los marcadores al mapa
        mapView.getOverlays().add(marcadorMetro);

        //Coordenadas local RemyFood

        double localLatitud = -33.4470683; //Latitud
        double localLongitud = -70.6648468; //Longitud

        //Crear objetos GeoPoint para las coordenadas definidas
        GeoPoint localPoint = new GeoPoint(localLatitud, localLongitud);
        //Crear un marcador para el local de RemyFood y creamos un marcdor en el Mapa
        Marker marcadorLocal = new Marker(mapView);
        // Establece la posición del marcador en el punto del metro
        marcadorLocal.setPosition(localPoint);
        // Establece el ancla del marcador y los valores se pueden ajustar la imagen con el marcador
        marcadorLocal.setAnchor(0.2f, 0.4f);
        // Ajusta la posición del título y la descripción
        marcadorLocal.setInfoWindowAnchor(0.2f, 0.0f);
        // Establece el título del marcador
        marcadorLocal.setTitle("Local de RemyFood");
        // Establece una descripcion para el marcador
        marcadorLocal.setSnippet("El rey de la fastfood");
        // Agregamos un Icono al marcador
        marcadorLocal.setIcon(getResources().getDrawable(R.drawable.remy));
        // Agregar los marcadores al mapa
        mapView.getOverlays().add(marcadorLocal);

        // Crear una linea que conecte los 2 Marcadores
        Polyline linea = new Polyline() ;
        // Añade el punto del metro y el del local RemyFood.
        linea.addPoint(metroPoint);
        linea.addPoint(localPoint);
        // Establece el color de la linea (rojo en formato ARGB).
        linea.setColor(0xFFFF0000);
        // Establece el ancho de la linea a 5 px
        linea.setWidth(5);
        //Añadir la linea al mapa
        mapView.getOverlayManager().add(linea);
        // Configurar el Spinner para cambiar el tipo de mapa y Obtiene la referencia al componente Spinner del id del xml
        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        //Definer un Array con los tipos de mapas
        String[] mapTypes = {"Mapa Normal", "Mapa de Transporte", "Mapa Topográfico"};

        // Crear un ArrayAdapter para poblar el Spinner con los tipos de mapas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        // Establece el layout para los elementos del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Asigna el adaptador al Spinner
        mapTypeSpinner.setAdapter(adapter);

        // Listener para detectar cambios en la seleccion del Spinner
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        break;
                    case 1:
                        mapView.setTileSource(new XYTileSource(
                                "PublicTransport",
                                0, 18, 256, ".png", new String[]{
                                "https://tile.memomaps.de/tilegen/"}));
                        break;
                    case 2:
                        mapView.setTileSource(new XYTileSource(
                                "USGS_Satellite", 0, 18, 256, ".png", new String[]{
                                "https://a.tile.opentopomap.org/",
                                "https://b.tile.opentopomap.org/",
                                "https://c.tile.opentopomap.org/"}));
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    public void onClickvolver(View view){
        Intent intent = new Intent(this, AccesoActivity.class);
        startActivity(intent);
    }

}