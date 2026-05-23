# Exploratory Test Suite — Sistema Vinilos

Objetivo
-------
Una suite de exploración sistemática enfocada en las Historias de Usuario (HU) principales del proyecto, diseñada para ejecutarse automáticamente con Firebase Test Lab usando el test tipo `robo` (explorador automático) en 3 dispositivos, incluyendo un dispositivo con Android Lollipop (API 21).

Resumen de la ejecución
----------------------
- Tipo de ejecución: Firebase Test Lab — Robo (exploratorio automático). Opcional: pruebas instrumentadas (`Espresso`) si se han automatizado casos específicos.
- Dispositivos objetivo (ejemplo):
  - `Nexus5`, `version=21` (Lollipop, más antigua)
  - `Pixel2`, `version=29`
  - `Pixel4`, `version=31`
- Timebox recomendado por ejecución por dispositivo: 15–30 minutos (configurable vía `--timeout`).

Charters (por HU)
------------------
Formato: `ID — Nombre (HU)` | Objetivo breve | Alcance | Duración

- EX-HU07 — Crear Álbum | Validar validaciones, subida de imagen y manejo de errores de red | Pantalla Crear Álbum, picker de imagen, campos fecha/género | 60 min
- EX-HU03 — Catálogo de Artistas | Navegación, carga de imágenes, filtros y scroll prolongado | Lista de artistas, búsqueda/filtrado | 45 min
- EX-HU06 — Consultar Detalle Coleccionista | Información personal, favoritos, rendimiento en listas largas | Detalle coleccionista, lista de favoritos | 45 min
- EX-HU01 — Catálogo de Álbumes | Paginación/orden, respuesta a rotación y cambios de configuración | Listado álbumes, transición a detalle | 45 min
- EX-HU010 — Agregar Artistas Favoritos | Accesibilidad de botones, estados y persistencia | Interacción de favoritar / desfavoritar | 30 min

Evidencia y captura
-------------------
- Firebase Test Lab genera un paquete de resultados con logs, screenshots y video por ejecución.
- Para cada sesión manual/automática guarda: `logcat`, `bugreport` y `screenrecord` (si corres localmente). En Firebase la consola ya entrega estos artefactos.

Comandos básicos (local)
------------------------
Construir artefactos necesarios (app + test APK opcional):
```bash
./gradlew assembleDebug assembleDebugAndroidTest
```

Ejecutar Robo test manual para un dispositivo (ejemplo):
```bash
gcloud firebase test android run \
  --type robo \
  --app app/build/outputs/apk/debug/app-debug.apk \
  --timeout 15m \
  --device model=Nexus5,version=21,locale=es,orientation=portrait
```

Notas y recomendaciones
----------------------
- Si quieres ejecuciones más dirigidas, añade `--robo-directives` para poblar campos esenciales (email, contraseña, etc.).
- Para aislamiento por test considera usar Android Test Orchestrator para instrumented tests.
- Reserva al menos 15m por dispositivo para que Robo explore bien las UI. Aumenta el timeout si la app tiene muchas pantallas.

Resultados y seguimiento
------------------------
- Cada ejecución produce un `results` bundle; descarga los artefactos y adjúntalos al ticket del hallazgo.
- Registra hallazgos con la plantilla de la sesión (ID, severity, pasos, evidencia) y convierte lo crítico en pruebas automáticas (`Espresso`) para regresión.

Plantilla rápida para un hallazgo
--------------------------------
- ID: EX-001
- Título: Campo fecha acepta formato inválido
- Severidad: Major
- Dispositivo: Nexus5 API21
- Pasos: 1) Abrir Crear Álbum 2) Ingresar `99/99/9999` 3) Guardar
- Resultado esperado: validación de formato
- Resultado actual: app acepta el dato y crashea
- Evidencia: video/stacktrace

---
Guía completa y script de ejecución: ver `scripts/run_firebase_robo_suite.sh`
