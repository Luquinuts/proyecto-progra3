---
name: progra3-swing-jdbc
description: "Trigger: proyecto final, entrega materia, programacion 3, progra3, UAI, Java Swing, MySQL, JDBC. Crear sistema de compra de boletos de cine en Java Swing + JDBC puro + Threads siguiendo los patrones vistos en la cursada de Programacion 3."
license: Apache-2.0
metadata:
  author: gentleman-programming
  version: "2.0"
---

# Skill: progra3-swing-jdbc — Sistema de Cine (Compra de Boletos)

## Activation Contract

Usar esta skill cuando el usuario pida:
- Crear el proyecto final de Programacion 3 (sistema de cine / compra de boletos)
- Trabajar con Java Swing + MySQL + JDBC + Threads
- Sistema con clientes, peliculas, funciones, butacas, reservas
- Condicion de carrera en seleccion de butacas
- Seguir los patrones de los ejemplos vistos en clase (Palavecino, Galende)
- Codigo compatible con Netbeans GUI Builder

## Hard Rules

1. **JDBC PURO exclusivamente**. NO usar JPA, Hibernate, ni ningun ORM. Solo `java.sql.DriverManager`, `Connection`, `Statement`, `ResultSet`.
2. **Driver MySQL**: `com.mysql.cj.jdbc.Driver`. Conexion por defecto localhost:3306.
3. **Netbeans compatible**: todo JFrame/JPanel DEBE usar el formato autogenerado de Netbeans (`initComponents()`, `//GEN-BEGIN:initComponents`). La skill da la estructura logica del codigo; el usuario genera el layout desde el disenador visual de Netbeans.
4. **CardLayout navigation**: una sola `VentanaPrincipal` con `JPanel` switcheables. NO un `JFrame` por pantalla.
5. **Threads para condicion de carrera**: al seleccionar butacas, usar `extends Thread` con `synchronized` block para simular concurrencia y evitar que dos clientes reserven la misma butaca.
6. **Nombres en español consistente** (tablas, columnas, variables, metodos).
7. **Paquetes**: `view`, `database`, `thread`, `model`.
8. **Integrantes**: Galende Ramiro y Palavecino Lucas.

## Database Schema (fijo — no cambiar)

```sql
CREATE TABLE peliculas (
    id_pelicula INT PRIMARY KEY,
    titulo VARCHAR(100),
    genero VARCHAR(50),
    duracion INT,
    clasificacion VARCHAR(10)
);

CREATE TABLE salas (
    id_sala INT PRIMARY KEY,
    nombre VARCHAR(30),
    capacidad INT
);

CREATE TABLE butacas (
    id_butaca INT PRIMARY KEY,
    id_sala INT,
    fila CHAR(1),
    numero INT,
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala)
);

CREATE TABLE funciones (
    id_funcion INT PRIMARY KEY,
    id_pelicula INT,
    id_sala INT,
    fecha DATE,
    hora TIME,
    precio DECIMAL(8,2),
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula),
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala)
);

CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    email VARCHAR(100),
    telefono VARCHAR(20)
);

CREATE TABLE reservas (
    id_reserva INT PRIMARY KEY,
    id_cliente INT,
    fecha_reserva DATE,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE detalle_reserva (
    id_detalle INT PRIMARY KEY,
    id_reserva INT,
    id_funcion INT,
    id_butaca INT,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva),
    FOREIGN KEY (id_funcion) REFERENCES funciones(id_funcion),
    FOREIGN KEY (id_butaca) REFERENCES butacas(id_butaca),
    UNIQUE (id_funcion, id_butaca)
);
```

## Decision Gates

| Situacion | Accion |
|-----------|--------|
| El usuario no especifica estructura de paquetes | Usar `view/`, `database/`, `thread/`, `model/` |
| El usuario no especifica DB | Usar `localhost`, `root`, sin password, database = `cine` |
| Integrantes del grupo | Galende Ramiro y Palavecino Lucas |
| Tema del proyecto | Sistema de compra de boletos de cine |
| Concurrencia | Threads para seleccion de butacas con race condition |
| UML | Incluir DER y diagrama de clases (opcional: secuencia) |

## Execution Steps

### 1. Estructura de proyecto Netbeans

```
CineApp/
├── src/
│   ├── view/                    # Swing: JFrames y JPanels
│   │   ├── VentanaPrincipal.java     # JFrame con CardLayout
│   │   ├── PantallaMenu.java         # JPanel — menu principal
│   │   ├── PantallaCliente.java      # JPanel — registro/login cliente
│   │   ├── PantallaPeliculas.java    # JPanel — cartelera / seleccion pelicula
│   │   ├── PantallaButacas.java      # JPanel — seleccion de butacas (con hilos)
│   │   ├── PantallaConfirmacion.java # JPanel — resumen y confirmacion compra
│   │   └── IPantallaBase.java        # Interfase con onShow()
│   ├── database/
│   │   ├── Conexion.java             # Conexion JDBC
│   │   ├── PeliculaDAO.java          # CRUD peliculas
│   │   ├── FuncionDAO.java           # CRUD funciones
│   │   ├── ButacaDAO.java            # CRUD butacas
│   │   ├── ClienteDAO.java           # CRUD clientes
│   │   ├── ReservaDAO.java           # CRUD reservas + detalle
│   │   └── SalaDAO.java             # CRUD salas
│   ├── model/
│   │   ├── Pelicula.java             # Modelo
│   │   ├── Funcion.java              # Modelo
│   │   ├── Butaca.java               # Modelo
│   │   ├── Cliente.java              # Modelo
│   │   ├── Reserva.java              # Modelo
│   │   ├── Sala.java                 # Modelo
│   │   └── IEntidad.java             # Interfase opcional para metodos comunes
│   └── thread/
│       └── ReservaButacaThread.java  # Thread con synchronized para race condition
├── cine.sql                          # Script completo de la DB
└── diagramas/                        # UML: DER + Clases
    ├── der.png
    └── diagrama-clases.png
```

### 2. Requisitos minimos de la materia cubiertos

| Requisito | Como se cubre |
|-----------|---------------|
| **4+ clases y subclases** | 7 modelos + DAOs |
| **Interfaces** | `IPantallaBase`, `IEntidad` (opcional) |
| **Encapsulamiento** | Atributos privados, getters/setters |
| **Abstraccion** | Interfaces, herencia de Thread |
| **Polimorfismo** | IPantallaBase.onShow() en cada pantalla |
| **Herencia** | Thread (ReservaButacaThread) |
| **4+ formularios GUI** | Menu, Cliente, Peliculas, Butacas, Confirmacion |
| **BD relacional (4+ tablas)** | 7 tablas con FK |
| **JDBC puro** | Conexion.java con DriverManager |
| **Concurrencia** | Threads sincronizados en seleccion de butacas |
| **Colecciones** | ArrayList<> en todos los DAOs |
| **Gestion de errores** | try/catch en JDBC, NumberFormatException en inputs |
| **UML** | DER + Diagrama de clases |

### 3. Conexion JDBC (database/Conexion.java)

```java
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private Connection conn = null;
    private String server = "localhost";
    private String database = "cine";
    private String user = "root";
    private String pass = "";
    private String url;

    public Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            url = "jdbc:mysql://" + server + ":3306/" + database;
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado a " + url);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error de conexion: " + ex);
        }
    }

    public Connection getConnection() { return conn; }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ResultSet query(String sql) throws SQLException {
        Statement stm = conn.createStatement();
        return stm.executeQuery(sql);
    }

    public int insert(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }

    public int update(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }

    public int delete(String sql) throws SQLException {
        try (Statement stm = conn.createStatement()) {
            return stm.executeUpdate(sql);
        }
    }
}
```

### 4. Modelos (model/)

Cada modelo es una clase Java simple con:
- Atributos privados
- Constructor vacio y constructor con parametros
- Getters y setters
- `@Override toString()`

### 5. DAOs con CRUD (database/XxxDAO.java)

Cada DAO trabaja con su modelo correspondiente. Las consultas SQL se escriben directamente concatenadas (estilo visto en clase). Cada metodo static:

- `obtenerTodos()` — SELECT * + ArrayList
- `obtenerPorId(int id)` — SELECT WHERE id
- `insertar(Entidad e)` — INSERT
- `actualizar(Entidad e)` — UPDATE
- `eliminar(int id)` — DELETE

**Importante para butacas**: incluir metodo `obtenerDisponiblesPorFuncion(int idFuncion)` que haga:

```sql
SELECT b.* FROM butacas b
WHERE b.id_sala = (SELECT id_sala FROM funciones WHERE id_funcion = X)
AND b.id_butaca NOT IN (
    SELECT dr.id_butaca FROM detalle_reserva dr
    JOIN reservas r ON dr.id_reserva = r.id_reserva
    WHERE dr.id_funcion = X
)
```

### 6. Interfase de ciclo de vida (view/IPantallaBase.java)

```java
package view;

public interface IPantallaBase {
    void onShow();
}
```

### 7. Ventana principal con CardLayout

`VentanaPrincipal` es un `JFrame` que contiene un `JPanel` con `CardLayout`. Cada pantalla es un `JPanel` que se agrega al contenedor y se muestra via `mostrarPantalla(String nombre)`.

El metodo `mostrarPantalla()` itera los componentes visibles y llama a `onShow()` si implementan `IPantallaBase`, para recargar datos cada vez que se navega a esa pantalla.

### 8. Pantallas (view/)

Cada pantalla se crea como `JPanel Form` en Netbeans e implementa `IPantallaBase`. Recibe la `VentanaPrincipal` en el constructor para poder navegar.

| Pantalla | Funcion | Componentes clave |
|----------|---------|-------------------|
| `PantallaMenu` | Menu principal con botones a cada seccion | JButton: "Peliculas", "Clientes", "Salir" |
| `PantallaCliente` | Registro de nuevo cliente | JTextField (nombre, apellido, email, telefono), JButton "Guardar" |
| `PantallaPeliculas` | Seleccion de pelicula, fecha, horario | JComboBox (peliculas, funciones), JTable (funciones disponibles), JButton "Seleccionar" |
| `PantallaButacas` | Mapa visual de butacas (ocupadas/libres) + confirmacion | JPanel con JToggleButton por butaca, JLabel (info funcion), JButton "Reservar" |
| `PantallaConfirmacion` | Resumen de la compra | JLabel con datos, JButton "Confirmar" / "Cancelar" |

### 9. Concurrencia — Condicion de carrera (thread/ReservaButacaThread.java)

**Escenario**: cuando un cliente selecciona butacas y confirma la reserva, pueden estar ocurriendo multiples reservas en paralelo (simulado con hilos). El thread debe:

1. Verificar que las butacas siguen disponibles (no reservadas por otro thread)
2. Si lo estan, insertar en `reservas` y `detalle_reserva`
3. Si no, rechazar la operacion

```java
package thread;

import database.ReservaDAO;
import model.Cliente;
import model.Funcion;
import java.util.List;

public class ReservaButacaThread extends Thread {

    private Cliente cliente;
    private Funcion funcion;
    private List<Integer> idsButacas;
    private boolean exito = false;

    public ReservaButacaThread(Cliente cliente, Funcion funcion, List<Integer> idsButacas) {
        this.cliente = cliente;
        this.funcion = funcion;
        this.idsButacas = idsButacas;
    }

    public boolean isExito() { return exito; }

    @Override
    public void run() {
        synchronized (ReservaButacaThread.class) {
            // 1. Verificar disponibilidad actual de las butacas
            // 2. Si todas estan libres -> insertar reserva + detalle
            // 3. Si alguna esta ocupada -> exito = false
            exito = ReservaDAO.reservarButacas(cliente, funcion, idsButacas);
        }
    }
}
```

**Uso desde PantallaButacas**:

```java
// Al hacer clic en "Reservar"
ReservaButacaThread thread = new ReservaButacaThread(cliente, funcion, butacasSeleccionadas);
thread.start();
try {
    thread.join();  // Esperar que termine
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
if (thread.isExito()) {
    JOptionPane.showMessageDialog(this, "Reserva exitosa!");
    ventanaPrincipal.mostrarPantalla("confirmacion");
} else {
    JOptionPane.showMessageDialog(this, "Alguna butaca ya fue reservada.", "Error", JOptionPane.ERROR_MESSAGE);
}
```

### 10. Main class

```java
package view;

public class CineApp {
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : 
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | 
                 javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
```

## Output Contract

La skill devuelve:
- La estructura completa de paquetes y archivos del proyecto de cine.
- El codigo de cada archivo listo para Netbeans.
- Las instrucciones claras de lo que el usuario debe completar desde Netbeans (los `initComponents()` generados por el disenador).
- Script SQL completo (`cine.sql`) con el schema + datos de ejemplo + INSERTS.
- El flujo de navegacion entre pantallas.
- El diagrama UML esperado (DER + clases).

## References

- `assets/template-modelo.sql` — template SQL
- `assets/template-dao.java` — template de DAO
- Los ejemplos de `Ejemplos/` del proyecto: PalavecinoExamen1, PalavecinoExamen2, Segundo parcial - Galende Ramiro
