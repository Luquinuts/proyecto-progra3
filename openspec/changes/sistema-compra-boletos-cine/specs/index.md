# Especificacion del Sistema — Compra de Boletos de Cine

## Arquitectura General

Sistema desktop Swing con 4 paquetes, 7 tablas MySQL, y 6 modulos funcionales que se integran via CardLayout + IPantallaBase.

```
src/
├── view/        — VentanaPrincipal (JFrame + CardLayout) + 5 JPanels
├── database/    — Conexion singleton + 7 DAOs con metodos static
├── model/       — 7 POJOs con atributos privados, getters/setters
└── thread/      — ReservaButacaThread extends Thread + synchronized
```

## Mapa de Dependencias entre Modulos

```
conexion-bd ─► gestion-peliculas ──┐
            ├► gestion-salas-butacas┤
            ├► gestion-funciones ───┼──► reserva-boletos
            └► registro-clientes ───┘
```

Toda operacion de base de datos depende de `conexion-bd`. El modulo `reserva-boletos` consume los 4 modulos de gestion (peliculas, salas/butacas, funciones, clientes) para completar el flujo de compra.

## Flujo de Pantallas

```
Menu (PantallaMenu)
  ├── [Registrar Cliente] → PantallaCliente
  ├── [Ver Cartelera]     → PantallaPeliculas → PantallaButacas → PantallaConfirmacion
  └── [Salir]
```

Cada JPanel implementa `IPantallaBase.onShow()` para recargar datos al navegar. `VentanaPrincipal.mostrarPantalla(String)` itera los paneles y llama a `onShow()` en los que implementan la interfase.

## Esquema BD (7 tablas)

`peliculas` → `funciones` ← `detalle_reserva` → `butacas` → `salas`
`clientes` → `reservas` → `detalle_reserva`

Restriccion `UNIQUE(id_funcion, id_butaca)` en `detalle_reserva` garantiza integridad a nivel BD.

## Modulos Especificados

| Modulo | Archivo | Depende de | Pantalla asociada |
|--------|---------|------------|-------------------|
| conexion-bd | `specs/conexion-bd.md` | — | Ninguna (transversal) |
| gestion-peliculas | `specs/gestion-peliculas.md` | conexion-bd | PantallaPeliculas |
| gestion-salas-butacas | `specs/gestion-salas-butacas.md` | conexion-bd | PantallaButacas |
| gestion-funciones | `specs/gestion-funciones.md` | conexion-bd, gestion-peliculas, gestion-salas-butacas | PantallaPeliculas |
| registro-clientes | `specs/registro-clientes.md` | conexion-bd | PantallaCliente |
| reserva-boletos | `specs/reserva-boletos.md` | Todos los anteriores | PantallaButacas, PantallaConfirmacion |
