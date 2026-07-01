package view;

import model.Funcion;
import model.Pelicula;
import model.Sala;
import java.util.ArrayList;
import java.util.List;

public class PantallaFormFuncion extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;
    private List<Pelicula> peliculas;
    private List<Sala> salas;
    private boolean editando = false;

    public PantallaFormFuncion(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        this.peliculas = new ArrayList<>();
        this.salas = new ArrayList<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        lblPelicula = new javax.swing.JLabel();
        cmbPelicula = new javax.swing.JComboBox<>();
        lblSala = new javax.swing.JLabel();
        cmbSala = new javax.swing.JComboBox<>();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblHora = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        lblPrecio = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblTitulo.setText("Nueva Funcion");
        Theme.styleTitle(lblTitulo);

        lblPelicula.setText("Pelicula:");
        Theme.styleBodyLabel(lblPelicula);

        cmbPelicula.setFont(Theme.FONT_BODY);

        lblSala.setText("Sala:");
        Theme.styleBodyLabel(lblSala);

        cmbSala.setFont(Theme.FONT_BODY);

        lblFecha.setText("Fecha (YYYY-MM-DD):");
        Theme.styleBodyLabel(lblFecha);
        txtFecha.setFont(Theme.FONT_BODY);

        lblHora.setText("Hora (HH:MM:SS):");
        Theme.styleBodyLabel(lblHora);
        txtHora.setFont(Theme.FONT_BODY);

        lblPrecio.setText("Precio:");
        Theme.styleBodyLabel(lblPrecio);
        txtPrecio.setFont(Theme.FONT_BODY);

        btnGuardar.setText("Guardar");
        Theme.styleButtonPrimary(btnGuardar);
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnCancelar.setText("Cancelar");
        Theme.styleButtonSecondary(btnCancelar);
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPelicula)
                    .addComponent(lblSala)
                    .addComponent(lblFecha)
                    .addComponent(lblHora)
                    .addComponent(lblPrecio))
                .addGap(15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPelicula, 250, 250, 250)
                    .addComponent(cmbSala, 250, 250, 250)
                    .addComponent(txtFecha, 250, 250, 250)
                    .addComponent(txtHora, 250, 250, 250)
                    .addComponent(txtPrecio, 250, 250, 250))
                .addGap(200, 200, 200))
            .addGroup(layout.createSequentialGroup()
                .addGap(250)
                .addComponent(btnGuardar)
                .addGap(30)
                .addComponent(btnCancelar)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(30)
            .addComponent(lblTitulo)
            .addGap(30)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblPelicula)
                .addComponent(cmbPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblSala)
                .addComponent(cmbSala, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblFecha)
                .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblHora)
                .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblPrecio)
                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(40)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnGuardar)
                .addComponent(btnCancelar))
            .addGap(30)
        );
    }

    @Override
    public void onShow() {
        cargarCombos();

        // Verificar si estamos editando
        Funcion f = ventanaPrincipal.getFuncionEditar();
        editando = (f != null);

        if (editando) {
            lblTitulo.setText("Editar Funcion");
            seleccionarEnCombo(cmbPelicula, f.getIdPelicula(), peliculas);
            seleccionarEnCombo(cmbSala, f.getIdSala(), salas);
            txtFecha.setText(f.getFecha());
            txtHora.setText(f.getHora());
            txtPrecio.setText(String.format("%.2f", f.getPrecio()));
        } else {
            lblTitulo.setText("Nueva Funcion");
            txtFecha.setText("");
            txtHora.setText("");
            txtPrecio.setText("");
        }
    }

    private void cargarCombos() {
        peliculas = database.PeliculaDAO.obtenerTodas();
        salas = database.SalaDAO.obtenerTodas();

        cmbPelicula.removeAllItems();
        for (Pelicula p : peliculas) {
            cmbPelicula.addItem(p.getTitulo());
        }

        cmbSala.removeAllItems();
        for (Sala s : salas) {
            cmbSala.addItem(s.getNombre());
        }
    }

    private <T> void seleccionarEnCombo(javax.swing.JComboBox<String> combo, int id, List<T> items) {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            int itemId = 0;
            if (item instanceof Pelicula) {
                itemId = ((Pelicula) item).getIdPelicula();
            } else if (item instanceof Sala) {
                itemId = ((Sala) item).getIdSala();
            }
            if (itemId == id) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private int getSiguienteIdFuncion() {
        int maxId = 0;
        for (Funcion f : database.FuncionDAO.obtenerTodas()) {
            if (f.getIdFuncion() > maxId) {
                maxId = f.getIdFuncion();
            }
        }
        return maxId + 1;
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        // Validar campos
        if (cmbPelicula.getSelectedIndex() < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una pelicula.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (cmbSala.getSelectedIndex() < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione una sala.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        String precioStr = txtPrecio.getText().trim();

        if (fecha.isEmpty() || hora.isEmpty() || precioStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "El precio debe ser un numero valido.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pelicula pelicula = peliculas.get(cmbPelicula.getSelectedIndex());
        Sala sala = salas.get(cmbSala.getSelectedIndex());

        if (editando) {
            Funcion original = ventanaPrincipal.getFuncionEditar();

            // Verificar duplicado (excluyendo la funcion actual)
            if (database.FuncionDAO.existeFuncionActiva(sala.getIdSala(), fecha, hora, original.getIdFuncion())) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ya existe una funcion activa en la misma sala, fecha y horario.",
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            original.setIdPelicula(pelicula.getIdPelicula());
            original.setIdSala(sala.getIdSala());
            original.setFecha(fecha);
            original.setHora(hora);
            original.setPrecio(precio);
            database.FuncionDAO.actualizar(original);

            javax.swing.JOptionPane.showMessageDialog(this, "Funcion actualizada correctamente.");
        } else {
            // Verificar duplicado
            if (database.FuncionDAO.existeFuncionActiva(sala.getIdSala(), fecha, hora, 0)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ya existe una funcion activa en la misma sala, fecha y horario.",
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nuevoId = getSiguienteIdFuncion();
            Funcion nueva = new Funcion(nuevoId, pelicula.getIdPelicula(), sala.getIdSala(), fecha, hora, precio);
            database.FuncionDAO.insertar(nueva);

            javax.swing.JOptionPane.showMessageDialog(this, "Funcion creada correctamente.");
        }

        ventanaPrincipal.setFuncionEditar(null);
        ventanaPrincipal.mostrarPantalla("funciones");
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.setFuncionEditar(null);
        ventanaPrincipal.mostrarPantalla("funciones");
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblPelicula;
    private javax.swing.JComboBox<String> cmbPelicula;
    private javax.swing.JLabel lblSala;
    private javax.swing.JComboBox<String> cmbSala;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JTextField txtHora;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnCancelar;
}
