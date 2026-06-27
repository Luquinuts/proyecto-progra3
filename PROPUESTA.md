# Propuesta — Sistema de Compra de Boletos de Cine

**Proyecto Final — Programacion 3**  
**Integrantes**: Galende Ramiro, Palavecino Lucas  

---

## 1. Descripcion del proyecto

Sistema desktop para la compra de boletos de cine. El cliente se registra, navega la cartelera, selecciona pelicula y funcion, elige sus butacas en un mapa visual y confirma la compra. La seleccion de butacas se maneja con hilos para simular la condicion de carrera cuando dos clientes eligen la misma butaca al mismo tiempo.

---

## 2. Tecnologias

| Tecnologia | Version / Driver |
|------------|------------------|
| Java (Netbeans) | Swing GUI Builder |
| MySQL | 8+ |
| JDBC | `com.mysql.cj.jdbc.Driver` |
| Conexion | `localhost:3306`, user `root`, sin pass |

---

## 3. Arquitectura (paquetes)

```
src/
├── view/        — Pantallas Swing (JPanel + CardLayout)
├── database/    — Conexion JDBC + DAOs por tabla
├── model/       — Clases de dominio (POJO)
└── thread/      — Hilos para concurrencia
```

---

## 4. Base de datos (7 tablas)

`peliculas` → `funciones` → `detalle_reserva` ← `reservas` ← `clientes`  
`salas` → `butacas` → `detalle_reserva`

La restriccion `UNIQUE(id_funcion, id_butaca)` en `detalle_reserva` garantiza que una butaca no se reserve dos veces en la misma funcion a nivel BD.

---

## 5. Flujo de pantallas

```
Menu Principal
    ├── Registro Cliente (nombre, apellido, email, telefono)
    ├── Cartelera (seleccion pelicula → funcion)
    ├── Mapa de Butacas (butacas libres/ocupadas → reservar con hilo)
    └── Confirmacion (resumen de la compra)
```

Navegacion via `CardLayout` + `IPantallaBase.onShow()` para recargar datos al entrar a cada pantalla.

---

## 6. POO aplicado

| Concepto | Donde |
|----------|-------|
| **Encapsulamiento** | Atributos privados en todos los modelos, getters/setters |
| **Abstraccion** | Interfase `IPantallaBase`, herencia de `Thread` |
| **Herencia** | `ReservaButacaThread extends Thread` |
| **Polimorfismo** | `IPantallaBase.onShow()` implementado en cada pantalla |
| **Interfases** | `IPantallaBase` para ciclo de vida de pantallas |
| **Colecciones** | `ArrayList<>` en todos los DAOs |
| **Errores** | `try/catch` en JDBC, validacion de inputs |

---

## 7. Concurrencia — Condicion de carrera

`ReservaButacaThread` extiende `Thread` y usa `synchronized(Clase.class)` para que solo un hilo por vez ejecute la reserva. El flujo:

1. Cliente selecciona butacas y confirma
2. Se lanza un thread que verifica disponibilidad contra BD
3. Si todas las butacas estan libres → inserta `reservas` + `detalle_reserva`
4. Si alguna esta ocupada → se rechaza la operacion
5. `thread.join()` espera el resultado antes de continuar

---

## 8. UML (a entregar)

- **DER**: Diagrama Entidad-Relacion con las 7 tablas, PKs, FKs, tipos
- **Diagrama de clases**: Modelos, DAOs, VentanaPrincipal, Pantallas, Thread
- **Opcional**: Diagrama de secuencia del flujo de reserva

---

## 9. Requisitos de la materia cubiertos

| Requisito | Estado |
|-----------|--------|
| 4+ clases y subclases | ✅ 7 modelos + DAOs |
| Interfases | ✅ IPantallaBase |
| BD relacional (4+ tablas) | ✅ 7 tablas con FK |
| JDBC puro | ✅ Conexion.java |
| 4+ formularios GUI | ✅ 5 pantallas |
| Concurrencia | ✅ Thread + synchronized |
| Colecciones | ✅ ArrayList |
| UML | ✅ DER + Clases |
