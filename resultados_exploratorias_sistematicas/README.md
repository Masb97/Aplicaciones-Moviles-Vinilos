# Resultados Exploratorias Sistemáticas

Esta carpeta contiene los artefactos recogidos tras ejecutar la suite instrumentada en Firebase Test Lab.

Estructura:

- `instr_results_20260523_122532/device_1/` → Pixel2.arm (API 26)
- `instr_results_20260523_122532/device_2/` → Pixel2.arm (API 30)
- `instr_results_20260523_122532/device_3/` → MediumPhone.arm (API 34)

Cada subcarpeta incluye:

- `video.mp4` — grabación de la ejecución
- `logcat` — log del dispositivo durante la ejecución
- `test_result_1.xml` — reporte JUnit
- `app-debug.apk` y `app-debug-androidTest.apk` — APKs usados

Uso rápido:

1. Inspeccionar un video:

```bash
vlc resultados_exploratorias_sistematicas/instr_results_20260523_122532/device_1/Pixel2.arm-26-es-portrait/video.mp4
```

2. Parsear el XML de junit para extraer fallos:

```bash
xmllint --format resultados_exploratorias_sistematicas/instr_results_20260523_122532/device_1/Pixel2.arm-26-es-portrait/test_result_1.xml
```

3. Reproducir localmente (instala los APKs en un dispositivo conectado):

```bash
adb install -r resultados_exploratorias_sistematicas/instr_results_20260523_122532/device_1/app-debug.apk
adb install -r resultados_exploratorias_sistematicas/instr_results_20260523_122532/device_1/app-debug-androidTest.apk
adb shell am instrument -w com.movilesuniandes.vinilos.test/androidx.test.runner.AndroidJUnitRunner
```

Para comprimir los artefactos en un archivo `.zip` para compartir o adjuntar a una release, puede ejecutar:

```bash
zip -r resultados_exploratorias_sistematicas_instr_20260523.zip instr_results_20260523_122532/
```

Este archivo resultante puede subirse a la plataforma de releases del repositorio o compartirse según el flujo de trabajo del proyecto.
