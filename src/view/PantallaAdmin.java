package view;

public class PantallaAdmin extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaAdmin(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        btnReporte = new javax.swing.JButton();
        btnMapaButacas = new javax.swing.JButton();
        btnFunciones = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblTitulo.setText("Administracion");
        Theme.styleTitle(lblTitulo);

        btnReporte.setText("Reporte por Sala");
        Theme.styleButtonPrimary(btnReporte);
        btnReporte.addActionListener(this::btnReporteActionPerformed);

        btnMapaButacas.setText("Mapa de Butacas");
        Theme.styleButtonSecondary(btnMapaButacas);
        btnMapaButacas.addActionListener(this::btnMapaButacasActionPerformed);

        btnFunciones.setText("Funciones");
        Theme.styleButtonSecondary(btnFunciones);
        btnFunciones.addActionListener(this::btnFuncionesActionPerformed);

        btnVolver.setText("Volver");
        Theme.styleButtonSecondary(btnVolver);
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMapaButacas, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(250, 250, 250))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(60)
            .addComponent(lblTitulo)
            .addGap(60)
            .addComponent(btnReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(30)
            .addComponent(btnMapaButacas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(15)
            .addComponent(btnFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(40)
            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(60)
        );
    }

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("reporte");
    }

    private void btnMapaButacasActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("admin-butacas");
    }

    private void btnFuncionesActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("funciones");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("menu");
    }

    @Override
    public void onShow() {
        // nothing to reload
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JButton btnReporte;
    private javax.swing.JButton btnMapaButacas;
    private javax.swing.JButton btnFunciones;
    private javax.swing.JButton btnVolver;
}
