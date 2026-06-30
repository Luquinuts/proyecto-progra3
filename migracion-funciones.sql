-- Migracion para ABM de Funciones (Feature #5)
-- Ejecutar contra la base de datos existente.

ALTER TABLE funciones ADD COLUMN activa TINYINT DEFAULT 1;
ALTER TABLE funciones ADD CONSTRAINT unique_funcion_sala_horario UNIQUE (id_sala, fecha, hora, activa);

-- Las funciones existentes quedan activas por defecto (activa = 1)
