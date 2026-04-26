# Vinilos - Aplicaciones Móviles (UniAndes)
Aplicación móvil desarrollada en Android para la materia Ingeniería de software para aplicaciones móviles de la Universidad de los Andes. Permite consultar y gestionar un catálogo de álbumes, artistas y coleccionistas de música.

## Integrantes
| Nombre | Correo |
|---|---|
| Juan Sebastián Carballo | j.carballon@uniandes.edu.co |
| Manuel Alejandro Soler Barrera | m.solerb@uniandes.edu.co |
| Pedro Camargo | p.camargoj@uniandes.edu.co |
| Santiago Aparicio | s.aparicio11@uniandes.edu.co |

## Requisitos previos
- Android Studio Hedgehog o superior
- JDK 11
- Android SDK API 21 (Android 5.0 Lollipop) o superior
- Gradle 9.3.1 (vía Gradle Wrapper)
- Android Gradle Plugin (AGP) 9.1.1

### Versión de Gradle
Este proyecto fija la versión de Gradle con el wrapper en:

- `gradle/wrapper/gradle-wrapper.properties`
- `distributionUrl=https://services.gradle.org/distributions/gradle-9.3.1-bin.zip`

Por eso, para garantizar consistencia entre entornos, usa siempre `./gradlew` (o `gradlew.bat` en Windows) y no una instalación global de Gradle.

Para verificar la versión efectiva:

```bash
./gradlew --version
```

## Stack tecnológico
- **Lenguaje:** Kotlin
- **UI:** XML View System + Material Components
- **Build:** Gradle con Kotlin DSL
- **Min SDK:** API 21 (Android 5.0 Lollipop)
- **Target SDK:** API 36

## Cómo correr el proyecto
1. Clonar el repositorio:

```bash
git clone https://github.com/Masb97/Aplicaciones-Moviles-Vinilos.git
```

2. Abrir el proyecto en Android Studio
3. Sincronizar Gradle: **File → Sync Project with Gradle Files**
4. Ejecutar en un emulador o dispositivo físico con API 21+

> **Nota:** El archivo `local.properties` no está incluido en el repositorio.
> Android Studio lo genera automáticamente al abrir el proyecto por primera vez.
> Si no se genera, créalo manualmente en la raíz del proyecto con el contenido:
> `sdk.dir=/ruta/a/tu/Android/sdk`

## Configuración de variables de entorno

Este proyecto usa variables definidas en `local.properties` para mantener configuraciones
sensibles fuera del repositorio. Además de `sdk.dir`, debes agregar:

| Variable | Descripción | Ejemplo |
|---|---|---|
| `BASE_API_URL` | URL base del backend de Vinilos | `https://vinilos-back-5f7d7e2da8cb.herokuapp.com/` |

> Si `BASE_API_URL` no está definida en `local.properties`, el build usa automáticamente como fallback:
> `https://vinilos-back-5f7d7e2da8cb.herokuapp.com/`

### Configuración para desarrollo local
Si el backend corre en tu máquina, usa la IP especial del emulador de Android en lugar de `localhost`:

```properties
BASE_API_URL=https://vinilos-back-5f7d7e2da8cb.herokuapp.com/
```

## Estructura del proyecto
```
app/src/main/
├── java/com/movilesuniandes/vinilos/
│   ├── MainActivity.kt                # Punto de entrada, Toolbar + BottomNav
│   ├── core/
│   │   └── remote/                    # Cliente HTTP (Retrofit) y definición de endpoints
│   └── features/
│       ├── albums/
│       │   ├── model/                 # Entidades, DTOs y repositorio
│       │   ├── view/                  # Fragments y Adapters
│       │   └── viewmodel/             # ViewModel y estados de UI
│       └── artists/
│           ├── model/                 # Entidades, DTOs y repositorio
│           ├── view/                  # Fragments y Adapters
│           └── viewmodel/             # ViewModel y estados de UI
└── res/
    ├── layout/                        # Layouts XML de pantallas
    ├── navigation/                    # Grafo de navegación
    ├── menu/                          # Menús de navegación
    ├── drawable/                      # Íconos y recursos gráficos
    └── values/                        # Colores, strings, tema y tipografía

app/src/test/                          # Unit tests (JVM)
app/src/androidTest/                   # Instrumentation tests (Espresso/UI/E2E)
postman/                               # Colección y environment para validar API
```

## Pruebas

### Correr unit tests (JVM)
```bash
./gradlew test
```

O desde Android Studio: **click derecho sobre src/test/ → Run Tests**  
Para ver cobertura: **click derecho → Run Tests with Coverage**

### Correr tests de UI con repositorio fake (Espresso)
Estas pruebas validan UI y comportamiento de fragmentos inyectando `FakeRepository`.

```bash
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.movilesuniandes.vinilos.features.albums.AlbumListFragmentTest
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.movilesuniandes.vinilos.features.artists.ArtistListFragmentTest,com.movilesuniandes.vinilos.features.artists.ArtistFiltersFragmentTest
```

### Correr pruebas E2E (UI real + backend real)
Estas pruebas arrancan `MainActivity`, navegan por la UI real y consumen el backend configurado en `BASE_API_URL`.

```bash
./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.movilesuniandes.vinilos.features.e2e.CatalogE2ETest
```

### Correr toda la suite de androidTest
```bash
./gradlew :app:connectedDebugAndroidTest
```

### Notas importantes para androidTest
- Requiere emulador o dispositivo físico conectado.
- El proyecto usa un runner personalizado `VinilosTestRunner` para preparar directorios necesarios de ejecución en dispositivo.
- Si `BASE_API_URL` apunta a un ambiente cambiante, las pruebas E2E pueden volverse inestables.

## Pruebas de API (Postman)

En la carpeta `postman/` encontrarás:

- `Vinilos.postman_collection.json`
- `Vinilos.postman_environment.json`

Documentación completa de uso: `postman/README.md`