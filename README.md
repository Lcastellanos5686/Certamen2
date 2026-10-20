# Certamen

Entrega del certamen para la nota. La aplicacion se llama GeoRegistro, la cual permite registrar ubicaciones para inspecciones en terreno simuladas. Esta aplicacion utiliza Firebase con base de datos RealTime, y el SDK de Google Maps


## Caracteristicas

- Menu Principal: Desde aqui se puede acceder a registrar una incidencia, buscar incidencias existentes en mapas y la comprobación de sensores

- Registros: Aqui el usuario puede registrar la incidencia por medio de un formulario y el uso de una instancia de mapa.

- Buscar Incidencias con mapa: Aqui el usuario puede buscar las incidencias registradas con un mapa interactivo

- Sensores: Aqui el usuario pude comprobar los sensores, como el acelerometro, de su dispositivo.

- Historial: Al ingresar a la app, al usuario se le presenta con un historial de incidencias.

- Base de datos: Guardado de información mediante Firebase Realtime Database.

- Uso del SDK Google Maps: El uso de mapas facilita al usuario el ingreso de las coordenadas
## Instrucciones para instalar

Clonar este repositorio con git bash:

```bash
  git https://github.com/Lcastellanos5686/Certamen2
```

En Android Studio seleccionar Open y eligir la carpeta del proyecto.


## Configuración

Se necista configurar un api key desde [Google Cloud](console.cloud.google.com), creando un proyecto y añadiendo Google Maps SDK for android, desde la seccion de APIS AND SERVICES. Una vez realizado, se debe añadir la api de Google Maps al archivo llamado secrets.properties y añadir la propiedad MAPS_API_KEY=DEFAULT_API_KEY a local.default.properties, ubicado en la raiz.

Para configurar [Firebase](https://console.firebase.google.com/), se necesita crear un nuevo proyecto, registrar la aplicacion en el proyecto, la cual genera un archivo llamado google-services.json, el cual se debe añadir a la raiz del proyecto; y crear la instancia de realtime database.

Una vez realizados los anteriores dos pasos, se deben añadir las credenciales del api de maps al archivo llamado secrets.properties, el cual se debe crear en la raiz del proyecto.

Ejecuta la aplicación en un emulador o dispositivo Android compatible.

## Pruebas

Comprobar con LogCat la correcta conexion con Firebase

Se incluye una activity llamada "MapsActivity" para probar la conexion con el uso de la api. De la misma forma que con firebase, se puede probar y diagnosticar su funcionamiento con LogCat.