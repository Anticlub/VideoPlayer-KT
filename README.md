# VideoPlayerKT

VideoPlayerKT es una app para Android en Kotlin que permite reproducir listas M3U, M3U8 y RPD.

## Setup local

### 1. Clonar el repositorio

```bash
git clone https://github.com/Anticlub/VideoPlayer-KT.git
```

### 2. Configurar Firebase

El proyecto usa Firebase para login de usuarios, listas remotas y análisis de la app. El archivo `google-services.json` no está en el repositorio por motivos de seguridad, por lo que hay que descargarlo manualmente.

Pasos para obtenerlo:

1. Solicitar acceso al proyecto Firebase al mantenedor del repositorio.
2. Entrar en [console.firebase.google.com](https://console.firebase.google.com).
3. Seleccionar el proyecto → Configuración del proyecto → Tus apps → Android.
4. Descargar el archivo `google-services.json`.
5. Colocarlo en la ruta `app/google-services.json`.

Si no tienes acceso al proyecto Firebase, contacta con el mantenedor del repositorio.