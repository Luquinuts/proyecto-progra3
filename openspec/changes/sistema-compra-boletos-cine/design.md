# Design: Sistema de compra de boletos de cine

## Technical Approach

Construcción por fases progresivas: (1) modelos POJO → (2) Conexion + DAOs → (3) UI framework (VentanaPrincipal + IPantallaBase) → (4) pantallas individuales → (5) thread de reserva → (6) integración. Patrón Galende: CardLayout en JFrame único + JPanels que implementan `IPantallaBase.onShow()` para recarga de datos.

## Architecture Decisions

| Opción | Tradeoff | Decisión |
|--------|----------|----------|
| `synchronized(Clase.class)` vs `ReentrantLock` | synchronized es más simple, visto en clase; ReentrantLock permite timeout | `synchronized(ReservaButacaThread.class)` — requisito de cátedra |
| Conexión singleton vs una por DAO | Singleton evita múltiples conexiones; una por DAO es más seguro ante cierres bruscos | Singleton en Conexion.java — el template de clase lo usa así |
| DAOs static vs instancia | Static evita instanciar; no permite mockeo ni herencia | Static — estilo del template Palavecino/Galende |
| JPanel + CardLayout vs JFrame por pantalla | Un JFrame es más simple pero rompe el patrón visto en clase | CardLayout — requisito de la skill |

## Package Architecture

```
src/
├── view/          VentanaPrincipal (JFrame), IPantallaBase (interface),
│                  PantallaMenu, PantallaCliente, PantallaPeliculas,
│                  PantallaButacas, PantallaConfirmacion (JPanels)
├── database/      Conexion.java (singleton), PeliculaDAO, SalaDAO,
│                  ButacaDAO, FuncionDAO, ClienteDAO, ReservaDAO
├── model/         Pelicula, Sala, Butaca, Funcion, Cliente, Reserva (POJOs)
└── thread/        ReservaButacaThread extends Thread
```

## Class Diagram

```
┌─────────────────────────────────────────────────────────┐
│ view                                                     │
│  IPantallaBase (interface)                               │
│    onShow(): void                                        │
│                                                          │
│  VentanaPrincipal extends JFrame                         │
│    - contenedor: JPanel (CardLayout)                     │
│    + mostrarPantalla(String nombre): void                │
│    + initComponents() [Netbeans GEN]                     │
│                                                          │
│  PantallaMenu extends JPanel                             │
│  PantallaCliente extends JPanel implements IPantallaBase │
│  PantallaPeliculas extends JPanel implements IPantallaBase│
│  PantallaButacas extends JPanel implements IPantallaBase │
│  PantallaConfirmacion extends JPanel implements IPantallaBase│
│    — cada una: constructor(VentanaPrincipal)             │
└─────────────────────────────────────────────────────────┘
                            │ usa
                            ▼
┌─────────────────────────────────────────────────────────┐
│ database / model / thread                                │
│  Conexion (singleton) — query/insert/update/delete       │
│  XxxDAO (static methods) ──► model/Xxx (POJO)           │
│  ReservaButacaThread extends Thread                      │
│    - cliente, funcion, idsButacas                        │
│    - run(): synchronized(class) { ReservaDAO... }        │
└─────────────────────────────────────────────────────────┘
```

## Screen Navigation Flow

```
CardLayout names:
  "menu"         → PantallaMenu
  "clientes"     → PantallaCliente
  "peliculas"    → PantallaPeliculas
  "butacas"      → PantallaButacas
  "confirmacion" → PantallaConfirmacion

VentanaPrincipal():
  contenedor.setLayout(new CardLayout());
  contenedor.add(new PantallaMenu(this), "menu");
  contenedor.add(new PantallaCliente(this), "clientes");
  ... → mostrarPantalla("menu");

mostrarPantalla(String nombre):
  CardLayout cl = (CardLayout) contenedor.getLayout();
  cl.show(contenedor, nombre);
  for (Component c : contenedor.getComponents())
    if (c.isVisible() && c instanceof IPantallaBase ib)
      ib.onShow(); break;
```

PantallaMenu inicia la navegación → cada pantalla llama a `ventanaPrincipal.mostrarPantalla("nombre")` en sus ActionListeners. `onShow()` se ejecuta solo en la pantalla visible (recarga datos desde BD cada vez que se navega a ella).

**Flujo completo**: Menu → [Registrar Cliente] → Cliente → [Guardar] → Menu → [Ver Cartelera] → Peliculas → [Seleccionar Funcion] → Butacas → [Reservar → Thread → join()] → Confirmacion → [Volver] → Menu

## Database Layer Design

**Conexion.java** (singleton):

| Método | Firma |
|--------|-------|
| constructor | `Conexion()` — Class.forName + DriverManager |
| getConnection | `Connection getConnection()` |
| closeConnection | `void closeConnection()` |
| query | `ResultSet query(String sql) throws SQLException` |
| insert | `int insert(String sql) throws SQLException` |
| update | `int update(String sql) throws SQLException` |
| delete | `int delete(String sql) throws SQLException` |

**Pattern DAO** (todos static, cada método crea su `Conexion` local):

| Método | Firma |
|--------|-------|
| obtenerTodos | `static ArrayList<X> obtenerTodos()` |
| obtenerPorId | `static X obtenerPorId(int id)` |
| insertar | `static void insertar(X obj)` |
| actualizar | `static void actualizar(X obj)` |
| eliminar | `static void eliminar(int id)` |

**Especiales**:

| DAO | Método | SQL |
|-----|--------|-----|
| ButacaDAO | `obtenerDisponiblesPorFuncion(int idFuncion)` | `SELECT b.* FROM butacas b WHERE b.id_sala = (SELECT id_sala FROM funciones WHERE id_funcion=X) AND b.id_butaca NOT IN (SELECT dr.id_butaca FROM detalle_reserva dr WHERE dr.id_funcion=X)` |
| ReservaDAO | `reservarButacas(Cliente, Funcion, List<Integer>)` | Verifica disponibilidad primero, luego INSERT en `reservas` + INSERT por butaca en `detalle_reserva`. Retorna boolean. |
| ReservaDAO | `obtenerUltimaReserva()` | `SELECT * FROM reservas ORDER BY id_reserva DESC LIMIT 1` |
| FuncionDAO | `obtenerPorPelicula(int idPelicula)` | `SELECT * FROM funciones WHERE id_pelicula = X` |

## Concurrency Design

```
ReservaButacaThread
├── Constructor(Cliente cliente, Funcion funcion, List<Integer> idsButacas)
├── run():
│   synchronized(ReservaButacaThread.class) {
│       exito = ReservaDAO.reservarButacas(cliente, funcion, idsButacas);
│   }
└── isExito(): boolean
```

El lock es a nivel de clase (`ReservaButacaThread.class`), lo que garantiza que solo un thread ejecute `reservarButacas()` a la vez en toda la JVM. Dentro del bloque:

1. Query verifica que todos los `id_butaca` en `idsButacas` estén disponibles para la `id_funcion` (misma subquery que `obtenerDisponiblesPorFuncion`)
2. Si todas libres → prepara próx id_reserva (MAX+1) → INSERT en `reservas` → por cada butaca, INSERT en `detalle_reserva` → retorna true
3. Si alguna ocupada → retorna false sin insertar nada

**Uso desde PantallaButacas**:
```
ReservaButacaThread thread = new ReservaButacaThread(cliente, funcion, seleccionadas);
thread.start();
thread.join();  // espera resultado
if (thread.isExito()) → mostrarPantalla("confirmacion")
else → JOptionPane error + recargar butacas
```

El `InterruptedException` de `join()` se captura con `Thread.currentThread().interrupt()` y muestra "Operación interrumpida".

## Netbeans Integration Notes

| Qué genera Netbeans | Qué escribimos a mano |
|---------------------|----------------------|
| `.form` files (layout XML) | No se tocan |
| `initComponents()` en `//GEN-BEGIN:initComponents` | No se edita manualmente |
| Declaraciones de variables en `//GEN-BEGIN:variables` | No se edita manualmente |
| Stubs de eventos `//GEN-FIRST:event_xxx` | Cuerpo de cada listener |
| — | Constructor: `initComponents() + this.ventanaPrincipal = vp` |
| — | Lógica de negocio, validaciones, llamadas a DAOs |
| — | `onShow()` para recargar datos |
| — | Clase `CineApp` con main() y LookAndFeel Nimbus |

**Action listener pattern** (estilo método de referencia, como en los ejemplos):

```java
// En initComponents() — generado por Netbeans:
buttonGuardar.addActionListener(this::buttonGuardarActionPerformed);

// Stub generado + código manual:
private void buttonGuardarActionPerformed(ActionEvent evt) {
    // validación → DAO → navegación
}
```

Cada pantalla extiende `javax.swing.JPanel` y recibe `VentanaPrincipal` en el constructor. Las pantallas con recarga implementan `IPantallaBase`. `PantallaMenu` no implementa la interfaz (solo navega, no recarga datos al volver).

## Testing Strategy

| Layer | Approach |
|-------|----------|
| Manual | Compilar con `javac -d build src/**/*.java`, ejecutar, probar las 5 pantallas |
| Concurrencia | Click rápido "Reservar" dos veces o lanzar threads manualmente desde código de prueba |
| BD | Verificar INSERTS con `SELECT *` desde MySQL Workbench |

*No hay test runner disponible — es un proyecto de cátedra Swing sin tests automatizados.*

## Migration / Rollout

No migration required. BD vacía se crea con `cine.sql` (schema + datos de ejemplo). El sistema arranca con datos de prueba precargados.

## Open Questions

- [ ] ¿Se usa `IEntidad` (interfaz opcional del skill) o se omite? Por ahora se omite para mantener simplicidad.
- [ ] ¿Auto-increment en PKs o inserción manual? Los templates usan inserción manual con `SELECT MAX(id)+1`; definir si se prefiere `AUTO_INCREMENT` en la BD.
