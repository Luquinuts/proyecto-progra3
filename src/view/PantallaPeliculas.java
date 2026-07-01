package view;

public class PantallaPeliculas extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaPeliculas(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblPelicula = new javax.swing.JLabel();
        comboPelicula = new javax.swing.JComboBox<>();
        lblFuncion = new javax.swing.JLabel();
        comboFuncion = new javax.swing.JComboBox<>();
        lblDetalle = new javax.swing.JLabel();
        lblDetalleValor = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblPelicula.setText("Pelicula:");
        Theme.styleBodyLabel(lblPelicula);
        lblFuncion.setText("Funcion:");
        Theme.styleBodyLabel(lblFuncion);
        lblDetalle.setText("Detalle:");
        Theme.styleBodyLabel(lblDetalle);
        lblDetalleValor.setText(" ");
        Theme.styleBodyLabel(lblDetalleValor);
        lblDetalleValor.setForeground(Theme.TEXT_PRIMARY);

        comboPelicula.setFont(Theme.FONT_BODY);
        comboFuncion.setFont(Theme.FONT_BODY);

        comboPelicula.addActionListener(this::comboPeliculaActionPerformed);
        comboFuncion.addActionListener(this::comboFuncionActionPerformed);

        btnSeleccionar.setText("Seleccionar");
        Theme.styleButtonPrimary(btnSeleccionar);
        btnSeleccionar.addActionListener(this::btnSeleccionarActionPerformed);

        btnVolver.setText("Volver");
        Theme.styleButtonSecondary(btnVolver);
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        // Renderer para mostrar solo el titulo de la pelicula
        comboPelicula.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof model.Pelicula) {
                    setText(((model.Pelicula) value).getTitulo());
                }
                if (!isSelected) {
                    setBackground(Theme.BG_INPUT);
                    setForeground(Theme.TEXT_PRIMARY);
                }
                setFont(Theme.FONT_BODY);
                return this;
            }
        });

        // Renderer para mostrar detalle de la funcion
        comboFuncion.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof model.Funcion) {
                    model.Funcion f = (model.Funcion) value;
                    setText(f.getFecha() + " " + f.getHora() + " - $" + String.format("%.2f", f.getPrecio()));
                }
                if (!isSelected) {
                    setBackground(Theme.BG_INPUT);
                    setForeground(Theme.TEXT_PRIMARY);
                }
                setFont(Theme.FONT_BODY);
                return this;
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(120)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPelicula)
                    .addComponent(lblFuncion)
                    .addComponent(lblDetalle))
                .addGap(15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboPelicula)
                    .addComponent(comboFuncion)
                    .addComponent(lblDetalleValor))
                .addGap(120))
            .addGroup(layout.createSequentialGroup()
                .addGap(250)
                .addComponent(btnSeleccionar)
                .addGap(30)
                .addComponent(btnVolver)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(60)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblPelicula)
                .addComponent(comboPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblFuncion)
                .addComponent(comboFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblDetalle)
                .addComponent(lblDetalleValor))
            .addGap(60)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnSeleccionar)
                .addComponent(btnVolver))
            .addGap(60)
        );
    }

    private void comboPeliculaActionPerformed(java.awt.event.ActionEvent evt) {
        cargarFunciones();
    }

    private void comboFuncionActionPerformed(java.awt.event.ActionEvent evt) {
        actualizarDetalle();
    }

    private void cargarFunciones() {
        comboFuncion.removeAllItems();
        lblDetalleValor.setText(" ");

        model.Pelicula pelicula = (model.Pelicula) comboPelicula.getSelectedItem();
        if (pelicula != null) {
            java.util.ArrayList<model.Funcion> funciones = database.FuncionDAO.obtenerPorPelicula(pelicula.getIdPelicula());
            for (model.Funcion f : funciones) {
                comboFuncion.addItem(f);
            }
        }
    }

    private void actualizarDetalle() {
        model.Funcion f = (model.Funcion) comboFuncion.getSelectedItem();
        if (f != null) {
            model.Sala sala = database.SalaDAO.obtenerPorId(f.getIdSala());
            String salaNombre = (sala != null) ? sala.getNombre() : "?";
            lblDetalleValor.setText("<html>Fecha: " + f.getFecha()
                    + " | Hora: " + f.getHora()
                    + " | Sala: " + salaNombre
                    + " | Precio: $" + String.format("%.2f", f.getPrecio()) + "</html>");
        } else {
            lblDetalleValor.setText(" ");
        }
    }

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {
        if (ventanaPrincipal.getClienteActual() == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Debe registrarse primero",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            ventanaPrincipal.mostrarPantalla("cliente");
            return;
        }

        model.Funcion funcion = (model.Funcion) comboFuncion.getSelectedItem();
        if (funcion == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Seleccione una funcion",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        ventanaPrincipal.setFuncionActual(funcion);
        ventanaPrincipal.mostrarPantalla("cantidad");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("menu");
    }

    @Override
    public void onShow() {
        comboPelicula.removeAllItems();
        comboFuncion.removeAllItems();
        lblDetalleValor.setText(" ");

        java.util.ArrayList<model.Pelicula> peliculas = database.PeliculaDAO.obtenerTodas();
        for (model.Pelicula p : peliculas) {
            comboPelicula.addItem(p);
        }
    }

    private javax.swing.JLabel lblPelicula;
    private javax.swing.JLabel lblFuncion;
    private javax.swing.JLabel lblDetalle;
    private javax.swing.JLabel lblDetalleValor;
    private javax.swing.JComboBox<model.Pelicula> comboPelicula;
    private javax.swing.JComboBox<model.Funcion> comboFuncion;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JButton btnVolver;
}
