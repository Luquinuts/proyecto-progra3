package view;

public class PantallaMenu extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaMenu(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblCliente = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        btnCartelera = new javax.swing.JButton();
        btnRegistrarse = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblCliente.setText("Seleccionar Cliente:");
        Theme.styleBodyLabel(lblCliente);

        cmbClientes.setFont(Theme.FONT_BODY);
        cmbClientes.addActionListener(this::cmbClientesActionPerformed);
        cmbClientes.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof model.Cliente) {
                    model.Cliente c = (model.Cliente) value;
                    setText(c.getNombre() + " " + c.getApellido() + " (" + c.getEmail() + ")");
                } else {
                    setText("-- Seleccione un cliente --");
                }
                if (!isSelected) {
                    setBackground(Theme.BG_INPUT);
                    setForeground(Theme.TEXT_PRIMARY);
                }
                setFont(Theme.FONT_BODY);
                return this;
            }
        });

        btnCartelera.setText("Cartelera");
        Theme.styleButtonPrimary(btnCartelera);
        btnCartelera.addActionListener(this::btnCarteleraActionPerformed);

        btnRegistrarse.setText("Nuevo Cliente");
        Theme.styleButtonSecondary(btnRegistrarse);
        btnRegistrarse.addActionListener(this::btnRegistrarseActionPerformed);

        btnAdmin.setText("Administracion");
        Theme.styleButtonSecondary(btnAdmin);
        btnAdmin.addActionListener(this::btnAdminActionPerformed);

        btnSalir.setText("Salir");
        Theme.styleButtonDelete(btnSalir);
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCliente)
                    .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCartelera, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(250, 250, 250))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(80)
            .addComponent(lblCliente)
            .addGap(5)
            .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30)
            .addComponent(btnCartelera, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(20)
            .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(15)
            .addComponent(btnAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(15)
            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(70)
        );
    }

    private void cmbClientesActionPerformed(java.awt.event.ActionEvent evt) {
        model.Cliente selected = (model.Cliente) cmbClientes.getSelectedItem();
        ventanaPrincipal.setClienteActual(selected);
    }

    private void btnCarteleraActionPerformed(java.awt.event.ActionEvent evt) {
        if (ventanaPrincipal.getClienteActual() == null) {
            Theme.showError(this,
                    "Debe seleccionar o registrar un cliente primero.");
        } else {
            ventanaPrincipal.mostrarPantalla("peliculas");
        }
    }

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("admin");
    }

    private void btnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("cliente");
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    @Override
    public void onShow() {
        // Recargar clientes desde la BD
        model.Cliente selected = ventanaPrincipal.getClienteActual();
        cmbClientes.removeAllItems();
        cmbClientes.addItem(null);

        for (model.Cliente c : database.ClienteDAO.obtenerTodos()) {
            cmbClientes.addItem(c);
            if (selected != null && c.getIdCliente() == selected.getIdCliente()) {
                cmbClientes.setSelectedItem(c);
            }
        }

        // Si no hay cliente seleccionado, mostrar placeholder
        if (selected == null) {
            cmbClientes.setSelectedIndex(0);
        }
    }

    private javax.swing.JLabel lblCliente;
    private javax.swing.JComboBox<model.Cliente> cmbClientes;
    private javax.swing.JButton btnCartelera;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnSalir;
}
