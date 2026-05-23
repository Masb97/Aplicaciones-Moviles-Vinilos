#!/usr/bin/env bash
set -euo pipefail

# run_firebase_robo_suite.sh
# Ejecuta la suite exploratoria usando Firebase Test Lab (Robo) en 3 dispositivos.
# Requisitos:
# - Tener instalado y autenticado `gcloud` con acceso a Firebase Test Lab.
# - Haber generado el APK: ./gradlew assembleDebug

APP_APK=app/build/outputs/apk/debug/app-debug.apk
TIMEOUT="15m"

if [ -z "${GCLOUD_PROJECT:-}" ]; then
  echo "La variable de entorno GCLOUD_PROJECT (ID del proyecto GCP/Firebase) debe estar definida."
  echo "Ejemplo: export GCLOUD_PROJECT=mi-project-id"
  exit 1
fi

if [ ! -f "$APP_APK" ]; then
  echo "APK no encontrado. Construyendo..."
  ./gradlew assembleDebug
fi

DEVICES=(
  "model=Nexus5,version=21,locale=es,orientation=portrait"
  "model=Pixel2,version=29,locale=es,orientation=portrait"
  "model=Pixel4,version=31,locale=es,orientation=portrait"
)

RESULTS_PREFIX="robo_results_$(date +%Y%m%d_%H%M%S)"

for i in "${!DEVICES[@]}"; do
  device_spec=${DEVICES[$i]}
  results_dir="$RESULTS_PREFIX/device_$((i+1))"
  echo "Ejecutando Robo en dispositivo: $device_spec -> resultados: $results_dir"

  gcloud firebase test android run \
    --project "$GCLOUD_PROJECT" \
    --type robo \
    --app "$APP_APK" \
    --timeout "$TIMEOUT" \
    --device "$device_spec" \
    --results-bucket "" \
    --results-dir "$results_dir"

  echo "Ejecución finalizada para dispositivo $device_spec. Resultados en: https://console.firebase.google.com/project/$GCLOUD_PROJECT/testlab"
done

echo "Todas las ejecuciones completadas. Descarga artefactos desde la consola de Firebase Test Lab o usa gcloud beta commands para obtener resultados específicos."
