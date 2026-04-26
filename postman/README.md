# Postman

Colección para validar la API de Vinilos en los endpoints de álbumes, músicos y bandas.

## Archivos

- `Vinilos.postman_collection.json`: colección con tres carpetas y tests automáticos.
- `Vinilos.postman_environment.json`: environment requerido para resolver `baseUrl` e IDs de detalle.

## Endpoints cubiertos

- `GET /albums`
- `GET /musicians`
- `GET /bands`
- `GET /albums/:id`
- `GET /musicians/:id`
- `GET /bands/:id`

## Datos del seed

- Álbumes: `100` Buscando América, `101` Poeta del pueblo, `102` A Night at the Opera, `103` A Day at the Races
- Músico: `100` Rubén Blades Bellido de Luna
- Banda: `101` Queen

## Uso

1. Importa la colección y el environment en Postman.
2. Selecciona el environment `Vinilos API Environment`.
3. Ejecuta cada carpeta o la colección completa.
4. Si cambias el seed de la base, actualiza `albumId`, `artistId` y `bandId` en el environment.

> La colección usa `{{baseUrl}}` del environment; si el environment no está seleccionado, Postman deja la URL vacía.

## Validaciones incluidas

- Código HTTP `200`.
- Respuesta tipo arreglo.
- Campos mínimos esperados por recurso.