package view;

public class PantallaMenu extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaMenu(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        btnCartelera = new javax.swing.JButton();
        btnRegistrarse = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        btnCartelera.setText("Cartelera");
        btnCartelera.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnCartelera.addActionListener(this::btnCarteleraActionPerformed);

        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnRegistrarse.addActionListener(this::btnRegistrarseActionPerformed);

        btnSalir.setText("Salir");
        btnSalir.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnCartelera, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(250, 250, 250))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(120)
            .addComponent(btnCartelera, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30)
            .addComponent(btnRegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30)
            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(120)
        );
    }

    private void btnCarteleraActionPerformed(java.awt.event.ActionEvent evt) {
        if (ventanaPrincipal.getClienteActual() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Debe registrarse primero");
            ventanaPrincipal.mostrarPantalla("cliente");
        } else {
            ventanaPrincipal.mostrarPantalla("peliculas");
        }
    }

    private void btnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("cliente");
    }

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    @Override
    public void onShow() {
        // nothing special to reload on the menu
    }

    private javax.swing.JButton btnCartelera;
    private javax.swing.JButton btnRegistrarse;
    private javax.swing.JButton btnSalir;
}
