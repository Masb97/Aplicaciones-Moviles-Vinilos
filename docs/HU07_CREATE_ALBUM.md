# HU07 — Crear un álbum

Registro breve: Implementar pantalla y flujo para crear un nuevo álbum desde la app. El endpoint backend esperado es `POST /albums` con autenticación Bearer y permisos administrativos. El cliente debe realizar validaciones locales antes de enviar la petición.

## Endpoint

- Ruta y método: `POST /albums`
- Autenticación: `Authorization: Bearer <token>` (rol `admin` requerido)
- Respuestas: `201 Created` (recurso creado), `400` validación, `401/403` auth, `409` duplicado

## Campos (body JSON)
- `name` (string, obligatorio, 3-200)
- `cover` (string URL, opcional)
- `releaseDate` (string ISO YYYY-MM-DD, obligatorio, <= hoy)
- `description` (string, opcional, max 2000)
- `genre` (string, obligatorio)
- `recordLabel` (string, opcional)
- `performerIds` (integer[], opcional)
- `tracks` (array of { name, duration }) opcional

Ver validaciones y ejemplos en el código de `AlbumCreateViewModel`.
