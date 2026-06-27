package view;

public class PantallaCliente extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaCliente(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblApellido = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        lblNombre.setText("Nombre:");
        lblNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtNombre.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        lblApellido.setText("Apellido:");
        lblApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtApellido.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        lblEmail.setText("Email:");
        lblEmail.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtEmail.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        lblTelefono.setText("Telefono:");
        lblTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        txtTelefono.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));

        btnGuardar.setText("Guardar");
        btnGuardar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNombre)
                    .addComponent(lblApellido)
                    .addComponent(lblEmail)
                    .addComponent(lblTelefono))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, 250)
                    .addComponent(txtNombre)
                    .addComponent(txtApellido)
                    .addComponent(txtEmail)
                    .addComponent(txtTelefono))
                .addGap(180, 180, 180))
            .addGroup(layout.createSequentialGroup()
                .addGap(250)
                .addComponent(btnGuardar)
                .addGap(30)
                .addComponent(btnVolver)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(80)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblNombre)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblApellido)
                .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblEmail)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblTelefono)
                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(40)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnGuardar)
                .addComponent(btnVolver))
            .addGap(80)
        );
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el proximo ID disponible
        int nextId = 1;
        database.Conexion cn = new database.Conexion();
        try {
            java.sql.ResultSet rs = cn.query("SELECT COALESCE(MAX(id_cliente), 0) + 1 FROM clientes");
            if (rs.next()) {
                nextId = rs.getInt(1);
            }
        } catch (java.sql.SQLException ex) {
            System.out.println("Error obteniendo proximo ID: " + ex);
        } finally {
            cn.closeConnection();
        }

        model.Cliente cliente = new model.Cliente(nextId, nombre, apellido, email, telefono);
        database.ClienteDAO.insertar(cliente);
        ventanaPrincipal.setClienteActual(cliente);

        javax.swing.JOptionPane.showMessageDialog(this, "Cliente registrado");
        ventanaPrincipal.mostrarPantalla("menu");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("menu");
    }

    @Override
    public void onShow() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }

    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVolver;
}
