# Especificacion: Gestion de Funciones

## Descripcion

CRUD de funciones que vinculan una pelicula con una sala en un horario especifico. Modelo `Funcion` con atributos: id_funcion, id_pelicula, id_sala, fecha, hora, precio. Se usa en `PantallaPeliculas` para mostrar horarios disponibles de la pelicula seleccionada.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| FUN-01 | El sistema DEBE listar todas las funciones | MUST |
| FUN-02 | El sistema DEBE obtener funciones por ID | MUST |
| FUN-03 | El sistema DEBE filtrar funciones por id_pelicula | MUST |
| FUN-04 | El sistema DEBE insertar, actualizar y eliminar funciones | MUST |
| FUN-05 | Al seleccionar una pelicula en `PantallaPeliculas`, DEBE mostrar solo las funciones de esa pelicula | MUST |
| FUN-06 | Cada funcion DEBE mostrar fecha, hora, sala y precio | MUST |

## Escenarios de Uso

### Escenario FUN-E1: Funciones de una pelicula especifica

- GIVEN existen 6 funciones en total, 2 de ellas para la pelicula #1 ("El Padrino")
- WHEN se llama a `FuncionDAO.obtenerPorPelicula(1)`
- THEN retorna un `ArrayList<Funcion>` con 2 elementos
- AND ambas funciones tienen id_pelicula = 1
- AND cada funcion incluye fecha, hora y precio

### Escenario FUN-E2: Seleccion de funcion en PantallaPeliculas

- GIVEN `PantallaPeliculas.onShow()` cargo la cartelera
- WHEN el usuario selecciona "Inception" en el JComboBox de peliculas
- THEN el JComboBox o JTable de funciones se actualiza con solo las funciones de Inception
- AND cada item muestra "Sala 2 - 3D | 2026-07-15 19:00 | $2000.00"

### Escenario FUN-E3: Funcion sin horarios

- GIVEN la pelicula #5 no tiene funciones cargadas
- WHEN se llama a `obtenerPorPelicula(5)`
- THEN retorna un `ArrayList<Funcion>` vacio
- AND no se lanza excepcion

## Criterios de Aceptacion

- [ ] CRUD funcional contra tabla `funciones` con FK a `peliculas` y `salas`
- [ ] Filtro por id_pelicula retorna solo funciones de esa pelicula
- [ ] `PantallaPeliculas` actualiza dinamicamente las funciones al cambiar pelicula

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| conexion-bd | Dependencia — usa Conexion para CRUD |
| gestion-peliculas | Dependencia — funciones referencian peliculas |
| gestion-salas-butacas | Dependencia — funciones referencian salas |
| reserva-boletos | Dependencia — la reserva usa id_funcion para detalle_reserva |
