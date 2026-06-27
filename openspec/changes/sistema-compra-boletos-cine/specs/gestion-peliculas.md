# Especificacion: Gestion de Peliculas

## Descripcion

CRUD completo de peliculas + consulta de cartelera activa. Modelo `Pelicula` con atributos: id, titulo, genero, duracion, clasificacion. DAO con metodos static que operan sobre `Conexion` singleton.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| PEL-01 | El sistema DEBE listar todas las peliculas desde la BD | MUST |
| PEL-02 | El sistema DEBE obtener una pelicula por su ID | MUST |
| PEL-03 | El sistema DEBE insertar una nueva pelicula | MUST |
| PEL-04 | El sistema DEBE actualizar los datos de una pelicula existente | MUST |
| PEL-05 | El sistema DEBE eliminar una pelicula por ID | MUST |
| PEL-06 | El sistema DEBE retornar las peliculas como `ArrayList<Pelicula>` | MUST |
| PEL-07 | La pantalla `PantallaPeliculas` DEBE mostrar las peliculas en un JComboBox o JTable | MUST |

## Escenarios de Uso

### Escenario PEL-E1: Listar todas las peliculas (cartelera)

- GIVEN existen 4 peliculas en la tabla `peliculas`
- WHEN se llama a `PeliculaDAO.obtenerTodos()`
- THEN retorna un `ArrayList<Pelicula>` con 4 elementos
- AND cada elemento tiene id, titulo, genero, duracion y clasificacion no nulos

### Escenario PEL-E2: Insertar y recuperar una pelicula

- GIVEN la tabla `peliculas` existe
- WHEN se inserta una pelicula con `PeliculaDAO.insertar(p)` y luego se llama a `obtenerPorId(id)`
- THEN la pelicula retornada tiene los mismos datos que la insertada

### Escenario PEL-E3: Eliminar una pelicula inexistente

- GIVEN no existe una pelicula con id=999
- WHEN se llama a `PeliculaDAO.eliminar(999)`
- THEN el metodo retorna 0 (ninguna fila afectada)
- AND no se lanza excepcion

## Criterios de Aceptacion

- [ ] CRUD funcional contra la tabla `peliculas`
- [ ] `obtenerTodos()` retorna ArrayList con datos reales de BD
- [ ] `PantallaPeliculas` muestra las peliculas disponibles

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| conexion-bd | Dependencia — usa Conexion para todas las operaciones |
| gestion-funciones | Relacion — las funciones referencian peliculas por FK |
| reserva-boletos | Relacion — el flujo de reserva comienza seleccionando una pelicula |
