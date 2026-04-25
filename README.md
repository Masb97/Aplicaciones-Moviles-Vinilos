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
- Gradle 9.3.1
- Android Gradle Plugin (AGP) 9.1.1

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
| `BASE_API_URL` | URL base del backend de Vinilos | `https://back-vinilos.herokuapp.com/` |

### Configuración para desarrollo local
Si el backend corre en tu máquina, usa la IP especial del emulador de Android en lugar de `localhost`:

```properties
BASE_API_URL=http://10.0.2.2:3000/
```

## Estructura del proyecto
```
app/src/main/
├── java/com/movilesuniandes/vinilos/
│   ├── MainActivity.kt                # Punto de entrada, Toolbar + BottomNav
│   ├── core/
│   │   └── remote/                    # Cliente HTTP (Retrofit) y definición de endpoints
│   └── features/
│       └── albums/
│           ├── model/                 # Entidades, DTOs y repositorio
│           ├── view/                  # Fragments y Adapters
│           └── viewmodel/             # ViewModel y estados de UI
└── res/
    ├── layout/                        # Layouts XML de pantallas
    ├── navigation/                    # Grafo de navegación
    ├── menu/                          # Menús de navegación
    ├── drawable/                      # Íconos y recursos gráficos
    └── values/                        # Colores, strings, tema y tipografía
```

## Pruebas

### Correr unit tests
```bash
./gradlew test
```

O desde Android Studio: **click derecho sobre src/test/ → Run Tests**  
Para ver cobertura: **click derecho → Run Tests with Coverage**

###  Correr tests de UI (Espresso)
Requiere emulador o dispositivo físico con **API 34**:
```bash
./gradlew connectedAndroidTest
```