# Especificacion: Conexion BD

## Descripcion

Modulo transversal que provee la conexion JDBC a MySQL. Implementa el patron singleton con una unica instancia de `Connection` compartida. Configuracion fija: `localhost:3306`, database `cine`, user `root`, sin password.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| CON-01 | La conexion DEBE usar `com.mysql.cj.jdbc.Driver` via `Class.forName()` | MUST |
| CON-02 | La URL DEBE ser `jdbc:mysql://localhost:3306/cine` | MUST |
| CON-03 | El sistema DEBE exponer metodos `query()`, `insert()`, `update()`, `delete()` que reciban SQL como String | MUST |
| CON-04 | El sistema DEBE cerrar recursos (Connection, Statement, ResultSet) correctamente | MUST |
| CON-05 | Si la conexion falla, el sistema DEBE mostrar el error en consola sin crashear la app | MUST |
| CON-06 | El sistema DEBE usar una unica instancia de Connection compartida (singleton) | SHOULD |

## Escenarios de Uso

### Escenario CON-E1: Conexion exitosa

- GIVEN MySQL esta corriendo en localhost:3306 con la database `cine` creada
- WHEN se instancia `Conexion` por primera vez
- THEN el metodo `getConnection()` retorna un objeto `Connection` no nulo
- AND se imprime "Conectado a jdbc:mysql://localhost:3306/cine" en consola

### Escenario CON-E2: Conexion fallida

- GIVEN MySQL NO esta corriendo o la database `cine` no existe
- WHEN se instancia `Conexion`
- THEN `getConnection()` retorna `null`
- AND se imprime "Error de conexion: ..." en consola
- AND la aplicacion NO lanza una excepcion no capturada

## Criterios de Aceptacion

- [ ] `Class.forName("com.mysql.cj.jdbc.Driver")` carga el driver sin error
- [ ] Una unica instancia de Connection para toda la app
- [ ] `closeConnection()` cierra la conexion sin leak
- [ ] Metodos query/insert/update/delete funcionales con SQL arbitrario

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| gestion-peliculas | Dependencia — usa Conexion para CRUD |
| gestion-salas-butacas | Dependencia — usa Conexion para CRUD |
| gestion-funciones | Dependencia — usa Conexion para CRUD |
| registro-clientes | Dependencia — usa Conexion para CRUD |
| reserva-boletos | Dependencia — usa Conexion para transaccion |
