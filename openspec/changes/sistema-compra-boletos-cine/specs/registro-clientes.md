# Especificacion: Registro de Clientes

## Descripcion

Alta de cliente con validacion de datos basicos. Modelo `Cliente` con atributos: id, nombre, apellido, email, telefono. La pantalla `PantallaCliente` presenta un formulario con JTextFields y un boton "Guardar". No hay autenticacion con password — solo registro.

## Requerimientos Funcionales

| ID | Requerimiento | Prioridad |
|----|--------------|-----------|
| CLI-01 | El sistema DEBE insertar un nuevo cliente en la BD | MUST |
| CLI-02 | El sistema DEBE listar todos los clientes | MUST |
| CLI-03 | El sistema DEBE obtener un cliente por ID | MUST |
| CLI-04 | El sistema DEBE validar que nombre y apellido no esten vacios antes de insertar | MUST |
| CLI-05 | El sistema DEBE validar que el email tenga formato basico (contiene @) | SHOULD |
| CLI-06 | `PantallaCliente` DEBE tener JTextField para nombre, apellido, email, telefono y un JButton "Guardar" | MUST |

## Escenarios de Uso

### Escenario CLI-E1: Registro exitoso

- GIVEN la pantalla `PantallaCliente` esta visible con el formulario vacio
- WHEN el usuario ingresa nombre="Ramiro", apellido="Galende", email="rgalende@email.com", telefono="123456789"
- AND hace clic en "Guardar"
- THEN se llama a `ClienteDAO.insertar(cliente)`
- AND se muestra un JOptionPane con "Cliente registrado exitosamente"
- AND los campos del formulario se limpian

### Escenario CLI-E2: Validacion — nombre vacio

- GIVEN la pantalla `PantallaCliente` esta visible
- WHEN el usuario ingresa nombre="" (vacio), apellido="Galende", email="rgalende@email.com"
- AND hace clic en "Guardar"
- THEN se muestra un JOptionPane de error: "El nombre no puede estar vacio"
- AND NO se llama a `ClienteDAO.insertar()`

### Escenario CLI-E3: Email sin @

- GIVEN la pantalla `PantallaCliente` esta visible
- WHEN el usuario ingresa nombre="Lucas", apellido="Palavecino", email="lucaspalavecino"
- AND hace clic en "Guardar"
- THEN se muestra un JOptionPane de advertencia: "El email debe contener @" (opcional, segun validacion)
- AND el registro puede proceder o no segun la implementacion (SHOULD)

## Criterios de Aceptacion

- [ ] Inserccion de cliente funciona contra tabla `clientes`
- [ ] Validacion de campos vacios en nombre y apellido
- [ ] `PantallaCliente` permite navegar de vuelta al menu tras guardar

## Relaciones con otros Specs

| Spec | Relacion |
|------|----------|
| conexion-bd | Dependencia — usa Conexion para INSERT |
| reserva-boletos | Dependencia — la reserva asocia un cliente a la compra |
