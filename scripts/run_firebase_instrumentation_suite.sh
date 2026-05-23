#!/usr/bin/env bash
set -euo pipefail

# run_firebase_instrumentation_suite.sh
# Construye APKs y ejecuta pruebas instrumentadas (Espresso) en Firebase Test Lab

APP_APK=app/build/outputs/apk/debug/app-debug.apk
TEST_APK=app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
TIMEOUT="30m"

if [ -z "${GCLOUD_PROJECT:-}" ]; then
  echo "La variable de entorno GCLOUD_PROJECT (ID del proyecto GCP/Firebase) debe estar definida."
  echo "Ejemplo: export GCLOUD_PROJECT=mi-project-id"
  exit 1
fi

echo "Construyendo app y test APKs..."
./gradlew assembleDebug assembleDebugAndroidTest

if [ ! -f "$APP_APK" ] || [ ! -f "$TEST_APK" ]; then
  echo "No se encontraron APKs después del build. Aborting."
  exit 1
fi

## Use virtual (ARM) devices to reduce likelihood of billed physical devices.
## Selected API levels: 26, 30, 34
DEVICES=(
  "model=Pixel2.arm,version=26,locale=es,orientation=portrait"
  "model=Pixel2.arm,version=30,locale=es,orientation=portrait"
  "model=MediumPhone.arm,version=34,locale=es,orientation=portrait"
)

RESULTS_PREFIX="instr_results_$(date +%Y%m%d_%H%M%S)"

for i in "${!DEVICES[@]}"; do
  device_spec=${DEVICES[$i]}
  results_dir="$RESULTS_PREFIX/device_$((i+1))"
  echo "Ejecutando instrumentation tests en dispositivo: $device_spec -> resultados: $results_dir"

  gcloud firebase test android run \
    --project "$GCLOUD_PROJECT" \
    --type instrumentation \
    --app "$APP_APK" \
    --test "$TEST_APK" \
    --timeout "$TIMEOUT" \
    --device "$device_spec" \
    --results-dir "$results_dir"

  echo "Ejecución finalizada para dispositivo $device_spec. Revisa resultados en Firebase Console."
done

echo "Todas las ejecuciones instrumentadas completadas."
