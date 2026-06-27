# Tasks: Sistema de Compra de Boletos de Cine

## Review Workload Forecast

| Field | Value |
|-------|-------|
| Estimated changed lines | ~1,700 |
| 400-line budget risk | High |
| Chained PRs recommended | Yes |
| Suggested split | 4 PRs |
| Delivery strategy | ask-on-risk |

Decision needed before apply: Yes
Chained PRs recommended: Yes
Chain strategy: pending
400-line budget risk: High

Split: PR1=Foundation ~330, PR2=Data~550, PR3=UI~350, PR4=Screens+Thread~410.

## Dependency Graph

```
Phase 1 ──► Phase 2 ──┐
                       ├──► Phase 4 ──► Phase 5
Phase 3 (paralelo) ───┘
Phase 6 (paralelo a todos)
```

## Phase 1: Modelos (POJOs) — ~200 lines

- [x] 1.1 `src/model/Pelicula.java` — atributos, constructores, getters/setters, toString()
- [x] 1.2 `src/model/Sala.java`
- [x] 1.3 `src/model/Butaca.java`
- [x] 1.4 `src/model/Funcion.java`
- [x] 1.5 `src/model/Cliente.java`
- [x] 1.6 `src/model/Reserva.java`
- *Aceptacion*: 6 POJOs compilan. Sin dependencias.

## Phase 2: Conexion + DAOs (Data Access) — ~550 lines

- [x] 2.1 `cine.sql` — DDL 7 tablas + FK + UNIQUE + datos ejemplo (4 peliculas, 3 salas, 90 butacas, 6 funciones)
- [x] 2.2 `src/database/Conexion.java` — singleton DriverManager con query/insert/update/delete
- [x] 2.3 `src/database/PeliculaDAO.java` — CRUD static
- [x] 2.4 `src/database/SalaDAO.java`
- [x] 2.5 `src/database/ButacaDAO.java` — CRUD + `obtenerDisponiblesPorFuncion(int)`
- [x] 2.6 `src/database/FuncionDAO.java` — CRUD + `obtenerPorPelicula(int)`
- [x] 2.7 `src/database/ClienteDAO.java`
- [x] 2.8 `src/database/ReservaDAO.java` — CRUD + `reservarButacas()` transaccional + `obtenerUltimaReserva()`
- *Depende*: Phase 1. *Ejecutar 2.1 antes de probar DAOs.*

## Phase 3: UI Framework — ~140 lines

- [x] 3.1 `src/view/IPantallaBase.java` — interface con `void onShow()`
- [x] 3.2 `src/view/VentanaPrincipal.java` — JFrame + CardLayout + `mostrarPantalla(String)` con iteracion IPantallaBase
- [x] 3.3 `src/view/CineApp.java` — main() con Nimbus LAF + EventQueue.invokeLater
- *Aceptacion*: VentanaPrincipal abre. Paralelo a Phase 1-2.

## Phase 4: Pantallas (Screens) — ~630 lines

- [x] 4.1 `src/view/PantallaMenu.java` — botones "Cartelera", "Registrarse", "Salir". Navegacion con validacion de cliente.
- [x] 4.2 `src/view/PantallaCliente.java` — formulario (nombre, apellido, email, telefono) + validacion + ClienteDAO
- [x] 4.3 `src/view/PantallaPeliculas.java` — JComboBox pelicula/funcion, boton "Seleccionar"
- [x] 4.4 `src/view/PantallaButacas.java` — mapa con JToggleButton (verde libre / naranja seleccionada / roja ocupada), boton "Reservar" + "Volver"
- [x] 4.5 `src/view/PantallaConfirmacion.java` — resumen compra, boton "Confirmar Compra" + "Cancelar"
- *Depende*: Phase 2 (DAOs) + Phase 3 (framework).

## Phase 5: Concurrencia — ~60 lines

- [x] 5.1 `src/thread/ReservaButacaThread.java` — extends Thread con `synchronized(ReservaButacaThread.class)` llamando a `ReservaDAO.reservarButacas()` + `isExito()`
- [x] 5.2 Integrar en PantallaButacas: boton "Reservar" → thread.start() → thread.join() → navegar o error
- *Aceptacion*: Dos reservas simultaneas no duplican butacas. *Depende*: Phase 2 (ReservaDAO) + Phase 4 (PantallaButacas).

## Phase 6: Deploy — ~0 lines de codigo

- [ ] 6.1 `diagramas/der.png` — diagrama entidad-relacion 7 tablas
- [ ] 6.2 `diagramas/diagrama-clases.png` — clases Java con paquetes y herencia
- *Aceptacion*: Reflejan el diseno. Paralelo a todo.
