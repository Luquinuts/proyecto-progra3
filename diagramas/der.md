# Diagrama Entidad-Relacion (DER)

**Sistema de Compra de Boletos de Cine**  
**Integrantes**: Galende Ramiro, Palavecino Lucas

## Esquema relacional (7 tablas)

```
┌─────────────┐     ┌─────────────┐
│  peliculas  │     │   salas     │
├─────────────┤     ├─────────────┤
│ PK id_pelicula│   │ PK id_sala  │
│   titulo    │     │   nombre    │
│   genero    │     │   capacidad │
│   duracion  │     └──────┬──────┘
│ clasificacion│           │
└──────┬──────┘           │
       │ 1                │ 1
       │                  │
       └──┐        ┌──────┘
          │        │
     ┌────▼────────▼──┐
     │   funciones     │
     ├────────────────┤
     │ PK id_funcion   │
     │ FK id_pelicula  │──────┐
     │ FK id_sala      │──────┤
     │   fecha         │      │
     │   hora          │      │
     │   precio        │      │
     └───────┬─────────┘      │
             │ 1              │
             │                │
             │                │
    ┌────────▼────────┐       │
    │ detalle_reserva  │       │
    ├─────────────────┤       │
    │ PK id_detalle    │       │
    │ FK id_reserva    │──┐    │
    │ FK id_funcion    │──┘    │
    │ FK id_butaca     │──┐    │
    │ UNIQUE(func,but) │  │    │
    └──────────────────┘  │    │
                          │    │
                    ┌─────▼┐   │
                    │butacas│   │
                    ├───────┤   │
                    │PK id_ │   │
                    │ butaca│   │
                    │FK id_ │   │
                    │ sala  │───┘
                    │ fila  │
                    │ numero│
                    └───────┘

┌─────────────┐     ┌─────────────┐
│  clientes   │     │  reservas   │
├─────────────┤     ├─────────────┤
│ PK id_cliente│    │ PK id_reserva│
│   nombre    │     │ FK id_cliente│──┘
│   apellido  │     │ fecha_reserva│
│   email     │     └──────┬───────┘
│   telefono  │            │ 1
└─────────────┘            │
                    ┌──────┘
                    │
               ┌────▼────────┐
               │detalle_     │
               │ reserva     │
               └─────────────┘
```

## Detalle de tablas

### peliculas
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_pelicula | INT | PRIMARY KEY |
| titulo | VARCHAR(100) | |
| genero | VARCHAR(50) | |
| duracion | INT | |
| clasificacion | VARCHAR(10) | |

### salas
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_sala | INT | PRIMARY KEY |
| nombre | VARCHAR(30) | |
| capacidad | INT | |

### butacas
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_butaca | INT | PRIMARY KEY |
| id_sala | INT | FOREIGN KEY → salas(id_sala) |
| fila | CHAR(1) | |
| numero | INT | |

### funciones
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_funcion | INT | PRIMARY KEY |
| id_pelicula | INT | FOREIGN KEY → peliculas(id_pelicula) |
| id_sala | INT | FOREIGN KEY → salas(id_sala) |
| fecha | DATE | |
| hora | TIME | |
| precio | DECIMAL(8,2) | |

### clientes
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_cliente | INT | PRIMARY KEY |
| nombre | VARCHAR(50) | |
| apellido | VARCHAR(50) | |
| email | VARCHAR(100) | |
| telefono | VARCHAR(20) | |

### reservas
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_reserva | INT | PRIMARY KEY |
| id_cliente | INT | FOREIGN KEY → clientes(id_cliente) |
| fecha_reserva | DATE | |

### detalle_reserva
| Columna | Tipo | Restriccion |
|---------|------|-------------|
| id_detalle | INT | PRIMARY KEY |
| id_reserva | INT | FOREIGN KEY → reservas(id_reserva) |
| id_funcion | INT | FOREIGN KEY → funciones(id_funcion) |
| id_butaca | INT | FOREIGN KEY → butacas(id_butaca) |
| — | — | UNIQUE(id_funcion, id_butaca) |

## Restricciones de integridad

- `UNIQUE(id_funcion, id_butaca)` en detalle_reserva: garantiza que una butaca no se reserve dos veces en la misma funcion
- Las FK con `ON DELETE RESTRICT` (default) evitan borrar entidades con dependencias
- `AUTO_INCREMENT` en id_pelicula, id_sala, id_funcion, id_reserva, id_detalle
