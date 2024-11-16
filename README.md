LINK: https://github.com/Jaime1999l/Jaime_Lopez_Diaz_Taller_4_PDE.git

# Taller 4 Programación Dirigida Por Eventos

Jaime López Díaz

## Índice

1. [MainActivity](#mainactivity)
2. [CustomWidget](#customwidget)
3. [BackgroundUtil](#backgroundutil)
4. [SimulatedNetworkTask](#simulatednetworktask)
5. [FetchGreetingTask](#fetchgreetingtask)
6. [UserViewModel](#userviewmodel)
7. [SaludoFragment](#saludofragment)
8. [BotonesFragment](#botonesfragment)
9. [DatabaseHelper](#databasehelper)
10. [UserRepository](#userrepository)
11. [RedActivity](#redactivity)
12. [ConfiguracionActivity](#configuracionactivity)

---

## MainActivity

### Descripción
`MainActivity` es la actividad principal que maneja los fragmentos (`SaludoFragment` y `BotonesFragment`) y el widget personalizado (`CustomWidget`). Aplica configuraciones de fondo almacenadas en `SharedPreferences`.

### Cambios implementados
- Añadido soporte para `CustomWidget`.
- Añadido uso de fragmentos en el MainActivity.

---

## CustomWidget

### Descripción
`CustomWidget` es un widget personalizado que muestra los datos del acelerómetro en tiempo real.

### Cambios implementados
- Implementación de sensores con `SensorManager` para capturar valores del acelerómetro.
- Interfaz personalizada con el diseño de `widget_layout`.

---

## BackgroundUtil

### Descripción
`BackgroundUtil` gestiona la configuración del fondo (color o imagen) para las actividades.

---

## SimulatedNetworkTask

### Descripción
`SimulatedNetworkTask` es una tarea de red simulada que obtiene datos JSON ficticios y los muestra en la interfaz de usuario.

---

## FetchGreetingTask

### Descripción
`FetchGreetingTask` simula la obtención de un mensaje de bienvenida desde una red.

---

## UserViewModel

### Descripción
`UserViewModel` utiliza el patrón MVVM para manejar datos del usuario, como el nombre. Interactúa con `SharedPreferences`, SQLite y Firestore.

---

## SaludoFragment

### Descripción
`SaludoFragment` muestra un mensaje dinámico de saludo basado en la hora del día.

### Cambios implementados
- Añadida animación para transiciones de texto.
- Optimización del cálculo de cambios en el saludo.

---

## BotonesFragment

### Descripción
`BotonesFragment` contiene botones que navegan entre diferentes actividades.

### Cambios implementados
- Enlace dinámico de botones a `RedActivity` y `ConfiguracionActivity`.

---

## DatabaseHelper

### Descripción
`DatabaseHelper` administra la creación y actualización de la base de datos SQLite utilizada para almacenar datos de usuario.

---

## UserRepository

### Descripción
`UserRepository` interactúa con SQLite y Firestore para guardar y recuperar información de usuario.

---

## RedActivity

### Descripción
`RedActivity` permite al usuario guardar su nombre y ejecuta tareas de red simuladas.

---

## ConfiguracionActivity

### Descripción
`ConfiguracionActivity` permite configurar el fondo (color o imagen) mediante botones o movimientos del dispositivo (Desplazamiento vertical y horizontal del dispositivo).

### Cambios implementados
- Añadido soporte para alternar entre colores e imágenes con el acelerómetro.
- Optimización del manejo de sensores y diseño dinámico.

### Anotaciones
- Agitar el teléfono móvil vertical u horizontalmente para ir cambiando entre distintos fondos de pantalla.

