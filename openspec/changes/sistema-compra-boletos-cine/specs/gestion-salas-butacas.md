# Especificacion: Gestion de Salas y Butacas

## Descripcion

CRUD de salas y butacas + consulta de butacas disponibles para una funcion. Modelos `Sala` (id, nombre, capacidad) y `Butaca` (id, id_sala, fila, numero). El mapa visual de butacas en `PantallaButacas` usa JToggleButton para mostrar estado libre/ocupado.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| SAL-01 | El sistema DEBE listar todas las salas | MUST |
| SAL-02 | El sistema DEBE listar todas las butacas de una sala | MUST |
| SAL-03 | El sistema DEBE consultar butacas disponibles para una funcion (no reservadas) | MUST |
| SAL-04 | La consulta de disponibles DEBE excluir butacas en `detalle_reserva` para esa funcion | MUST |
| SAL-05 | `PantallaButacas` DEBE mostrar un JToggleButton por butaca, coloreado segun estado | MUST |
| SAL-06 | El sistema DEBE insertar, actualizar y eliminar salas y butacas | MUST |

## Escenarios de Uso

### Escenario SAL-E1: Butacas disponibles para una funcion

- GIVEN la funcion #1 esta en sala #1 con 40 butacas
- AND existen 2 reservas previas para esa funcion (butacas #1 y #2)
- WHEN se llama a `ButacaDAO.obtenerDisponiblesPorFuncion(1)`
- THEN retorna 38 butacas (40 - 2)
- AND ninguna butaca retornada tiene id 1 o 2

### Escenario SAL-E2: Mapa visual en PantallaButacas

- GIVEN una funcion con sala #3 (20 butacas, 4 filas A-D x 5 asientos)
- WHEN `PantallaButacas.onShow()` carga las butacas disponibles
- THEN se muestran 20 JToggleButton organizados en grilla 4x5
- AND los botones de butacas ocupadas aparecen deshabilitados o en color rojo
- AND los botones de butacas libres aparecen habilitados en color verde

### Escenario SAL-E3: Sala sin butacas cargadas

- GIVEN una sala nueva sin butacas en la tabla `butacas`
- WHEN se llama a `obtenerDisponiblesPorFuncion(X)`
- THEN retorna un `ArrayList<Butaca>` vacio
- AND no se lanza excepcion

## Criterios de Aceptacion

- [ ] `obtenerDisponiblesPorFuncion(id)` funciona con la subquery NOT IN contra detalle_reserva
- [ ] Mapa de butacas en PantallaButacas distingue visualmente libre/ocupado
- [ ] Las butacas se organizan por fila y numero en la grilla

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| conexion-bd | Dependencia — usa Conexion para CRUD |
| gestion-funciones | Relacion — las funciones referencian salas por FK |
| reserva-boletos | Dependencia — la reserva consulta butacas disponibles y las marca como ocupadas |
