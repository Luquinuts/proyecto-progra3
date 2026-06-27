# Especificacion: Reserva de Boletos

## Descripcion

Modulo central que coordina el flujo completo de compra: cliente selecciona butacas en el mapa visual, lanza un thread sincronizado que verifica disponibilidad y persiste la reserva, y muestra confirmacion. Usa `ReservaButacaThread extends Thread` con `synchronized(Clase.class)` para evitar condicion de carrera.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| RES-01 | El sistema DEBE permitir seleccionar multiples butacas en `PantallaButacas` | MUST |
| RES-02 | El sistema DEBE lanzar un `ReservaButacaThread` al confirmar la reserva | MUST |
| RES-03 | El thread DEBE ejecutar `synchronized(ReservaButacaThread.class)` para exclusion mutua | MUST |
| RES-04 | El thread DEBE verificar que todas las butacas seleccionadas esten libres antes de insertar | MUST |
| RES-05 | Si todas las butacas estan libres, DEBE insertar en `reservas` y `detalle_reserva` | MUST |
| RES-06 | Si alguna butaca ya fue reservada, DEBE rechazar la operacion sin insertar nada | MUST |
| RES-07 | `PantallaConfirmacion` DEBE mostrar resumen: pelicula, sala, fecha, butacas y total | MUST |
| RES-08 | La pantalla DEBE esperar con `thread.join()` el resultado antes de continuar | MUST |
| RES-09 | El sistema DEBE persistir la reserva con integridad referencial (FKs validas) | MUST |
| RES-10 | Si el thread falla, el sistema DEBE mostrar mensaje de error sin crashear | MUST |

## Escenarios de Uso

### Escenario RES-E1: Reserva exitosa (happy path)

- GIVEN un cliente registrado, una funcion seleccionada y 3 butacas libres (A1, A2, A3)
- WHEN el usuario selecciona A1, A2, A3 en `PantallaButacas`
- AND hace clic en "Reservar"
- THEN se crea `ReservaButacaThread(cliente, funcion, [1,2,3])`
- AND `thread.start()` ejecuta el bloque synchronized
- AND `ReservaDAO.reservarButacas()` inserta 1 fila en `reservas` y 3 filas en `detalle_reserva`
- AND `thread.isExito()` retorna `true`
- AND se muestra JOptionPane "Reserva exitosa!"
- AND la navegacion avanza a `PantallaConfirmacion`

### Escenario RES-E2: Condicion de carrera — butaca ya reservada

- GIVEN un cliente selecciona las butacas A1, A2 en la funcion #1
- AND otro thread (concurrente) ya reservo A2 en la misma funcion
- WHEN `ReservaButacaThread` ejecuta `run()` dentro del synchronized block
- AND `ReservaDAO.reservarButacas()` verifica disponibilidad y encuentra A2 ocupada
- THEN no se inserta ninguna fila (rollback implicito por no ejecutar INSERT)
- AND `thread.isExito()` retorna `false`
- AND se muestra JOptionPane "Alguna butaca ya fue reservada."
- AND `PantallaButacas` recarga el mapa mostrando A2 como ocupada

### Escenario RES-E3: Resumen en PantallaConfirmacion

- GIVEN la reserva fue exitosa (Escenario RES-E1)
- WHEN `PantallaConfirmacion.onShow()` carga los datos de la reserva
- THEN se muestra: titulo pelicula, nombre sala, fecha y hora de funcion
- AND se listan las butacas reservadas (A1, A2, A3)
- AND se muestra el total (cantidad butacas x precio funcion)

### Escenario RES-E4: Thread interrumpido

- GIVEN `ReservaButacaThread` esta ejecutando `run()`
- WHEN otro thread llama a `thread.interrupt()` y `thread.join()` lanza `InterruptedException`
- THEN `PantallaButacas` captura la excepcion
- AND muestra JOptionPane "La operacion fue interrumpida."
- AND el sistema NO deja la BD en estado inconsistente

## Criterios de Aceptacion

- [ ] Reserva exitosa persiste en `reservas` y `detalle_reserva`
- [ ] Dos threads simultaneos no reservan la misma butaca (synchronized block)
- [ ] `PantallaConfirmacion` muestra resumen correcto de la compra
- [ ] `thread.join()` espera resultado antes de continuar
- [ ] Rechazo de reserva no persiste datos parciales

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| conexion-bd | Dependencia — usa Conexion para transaccion |
| gestion-peliculas | Dependencia — selecciona pelicula primero |
| gestion-salas-butacas | Dependencia — consulta butacas disponibles, las marca como ocupadas |
| gestion-funciones | Dependencia — selecciona funcion con horario y precio |
| registro-clientes | Dependencia — asocia cliente a la reserva |
