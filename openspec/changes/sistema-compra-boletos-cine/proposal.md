# Propuesta: Sistema de compra de boletos de cine

## Intención

Implementar desde cero el sistema desktop de compra de boletos para el proyecto final de Programacion 3. El cliente se registra, navega la cartelera, selecciona butacas en un mapa visual y confirma la compra con concurrencia simulada via threads + synchronized.

## Alcance

### Incluye
- 7 modelos POJO (peliculas, salas, butacas, funciones, clientes, reservas, detalle_reserva)
- 7 DAOs con JDBC puro + ArrayList
- Conexion MySQL via DriverManager (localhost:3306, root sin pass)
- 5 pantallas Swing (Menu, Registro, Cartelera, MapaButacas, Confirmacion) con CardLayout + IPantallaBase
- Thread de reserva con synchronized block para condicion de carrera
- 22 archivos Java en paquetes view/, database/, model/, thread/

### Excluye
- Diagramas UML (se entregan aparte, no son codigo)
- Pruebas automatizadas (no hay test runner disponible)
- Autenticacion con contrasena (solo registro con datos basicos)
- Framework ORM (solo JDBC puro por requisito de catedra)

## Capacidades

### Nuevas Capacidades
Cada una se convierte en `openspec/specs/<nombre>/spec.md`:
- `conexion-bd`: Conexion MySQL, cierre de recursos, configuracion de driver
- `gestion-peliculas`: CRUD de peliculas, consulta cartelera activa
- `gestion-salas-butacas`: Salas con mapa de butacas, estado libre/ocupado
- `gestion-funciones`: Funciones por pelicula, horarios, sala asignada
- `registro-clientes`: Alta de cliente con validacion de datos
- `reserva-boletos`: Flujo completo de compra con concurrencia y rollback transaccional

### Modificadas
Ninguna — primera iteracion del sistema.

## Enfoque

Construccion por fases progresivas: (1) modelos POJO, (2) esquema SQL + Conexion, (3) DAOs, (4) UI framework (VentanaPrincipal + IPantallaBase), (5) pantallas una por una, (6) thread de reserva, (7) integracion final.

## Areas Afectadas

| Area | Impacto | Descripcion |
|------|---------|-------------|
| `src/model/` | Nuevo | 7 clases POJO con getters/setters |
| `src/database/` | Nuevo | `Conexion.java` + 7 DAOs |
| `src/view/` | Nuevo | `VentanaPrincipal` + 5 pantallas + `IPantallaBase` |
| `src/thread/` | Nuevo | `ReservaButacaThread extends Thread` |
| `BD` | Nueva | Script DDL con 7 tablas, FK, UNIQUE(id_funcion, id_butaca) |

## Riesgos

| Riesgo | Prob. | Mitigacion |
|--------|-------|------------|
| Schema SQL inconsistente con modelos | Media | DER primero, validar DAOs contra DDL |
| Deadlock en thread sincronizado | Baja | synchronized(Clase.class) sobre bloque chico, timeout implicito por join() |
| CardLayout sin initComponents | Media | Crear paneles manualmente sin Netbeans, luego migrar a initComponents |
| Connection pool agotado | Baja | Una sola conexion compartida via singleton en Conexion.java |

## Plan de Rollback

Eliminar `src/` completo y recrear desde cero. El schema SQL se puede dropear con `DROP DATABASE IF EXISTS cine;`. Git permite revertir cualquier commit.

## Dependencias

- MySQL 8+ corriendo en localhost:3306
- Driver JDBC `mysql-connector-j` en classpath
- Netbeans con soporte Swing GUI Builder (initComponents)

## Criterios de Exito

- [ ] Compila sin errores con `javac -d build src/**/*.java`
- [ ] Las 5 pantallas navegan correctamente con CardLayout
- [ ] Mapa de butacas muestra estado libre/ocupado desde BD
- [ ] Dos clientes simultaneos no reservan la misma butaca (synchronized)
- [ ] Reserva exitosa persiste en `reservas` + `detalle_reserva`
