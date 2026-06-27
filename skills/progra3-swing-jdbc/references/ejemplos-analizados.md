# Referencias - Ejemplos Analizados

## 1. palavecinoexamen1 (Consola + POO basico)
- **Temas**: Interface, abstract class, herencia, polimorfismo, composicion
- **Apps**: Logica de dominio sin GUI ni DB

## 2. PalavecinoExamen2 (Swing + JDBC + Threads)
- **Temas**: Conexion JDBC, CRUD con modelos, JFrame por pantalla, Thread con synchronized
- **Apps**: Sistema de subasta con productos, postulantes, ofertas

## 3. Segundo parcial - Galende Ramiro (Estructurado por capas)
- **Temas**: CardLayout, JPanel, IPantallaBase, paquetes view/database/thread, CRUD con metodos static, sincronizacion en DB
- **Apps**: Sistema de subastas con ofertas concurrentes

## Proyecto Final - Sistema de Cine (a implementar)
- **Tablas**: peliculas, salas, butacas, funciones, clientes, reservas, detalle_reserva
- **Flujo**: Menu -> Registro Cliente -> Seleccion Pelicula/Funcion -> Mapa Butacas -> Confirmacion
- **Concurrencia**: Threads en seleccion de butacas con synchronized
- **Race condition**: Dos threads intentan reservar la misma butaca simultaneamente
