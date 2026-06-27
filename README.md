# Proyecto Progra 3 — Sistema de Compra de Boletos de Cine

**Materia**: Programación 3 — Licenciatura en Informática  
**Integrantes**: Galende Ramiro, Palavecino Lucas  

---

## Descripción

Sistema desktop para la compra de boletos de cine desarrollado en Java Swing con conexión a MySQL vía JDBC. El cliente se registra, navega la cartelera, selecciona película y función, elige butacas en un mapa visual y confirma la compra. La selección de butacas utiliza hilos sincronizados para simular una condición de carrera.

## Tecnologías

- Java (Netbeans GUI Builder)
- MySQL 8+
- JDBC con `com.mysql.cj.jdbc.Driver`

## Requisitos cubiertos

| Requisito | Estado |
|-----------|--------|
| POO (encapsulamiento, herencia, polimorfismo, abstracción) | ✅ |
| 7 clases / subclases | ✅ |
| Interfaces | ✅ |
| BD relacional (7 tablas con FK) | ✅ |
| JDBC puro | ✅ |
| 5 formularios GUI | ✅ |
| Concurrencia con hilos sincronizados | ✅ |
| Colecciones | ✅ |
| DER + Diagrama de clases | ✅ |

## Base de datos

Las 7 tablas del sistema:

- `peliculas` — catálogo de películas
- `salas` — salas de cine
- `butacas` — butacas por sala
- `funciones` — película + sala + horario
- `clientes` — registro de clientes
- `reservas` — cabecera de reserva
- `detalle_reserva` — butacas reservadas por función (con `UNIQUE` para evitar duplicados)

## Estructura del proyecto

```
src/
├── view/        — Pantallas Swing (CardLayout + JPanel)
├── database/    — Conexión JDBC + DAOs
├── model/       — Clases de dominio
└── thread/      — Hilos para concurrencia
```

## Contenido del repositorio

| Carpeta/Archivo | Descripción |
|----------------|-------------|
| `skills/progra3-swing-jdbc/` | Skill de OpenCode con templates y guía del proyecto |
| `Ejemplos/` | Ejemplos de parciales anteriores de la materia |
| `PROPUESTA.md` | Propuesta detallada del sistema |
| `Proyecto Final.md` | Consigna original de la materia |
