package view;

import model.Butaca;
import model.Funcion;
import java.util.List;

public class PantallaCantidad extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaCantidad(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        spinnerCantidad = new javax.swing.JSpinner();
        lblTotal = new javax.swing.JLabel();
        btnContinuar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        lblTitulo.setText("Cantidad de Entradas");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        spinnerCantidad.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        spinnerCantidad.setPreferredSize(new java.awt.Dimension(80, 40));
        spinnerCantidad.addChangeListener(this::spinnerCantidadStateChanged);

        lblTotal.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotal.setForeground(new java.awt.Color(0, 100, 0));

        btnContinuar.setText("Continuar");
        btnContinuar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnContinuar.addActionListener(this::btnContinuarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(310)
                .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(310))
            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(250)
                .addComponent(btnContinuar)
                .addGap(30)
                .addComponent(btnVolver)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(80)
            .addComponent(lblTitulo)
            .addGap(20)
            .addComponent(lblInfo)
            .addGap(20)
            .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(lblTotal)
            .addGap(50)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnContinuar)
                .addComponent(btnVolver))
            .addGap(80)
        );
    }

    private void actualizarTotal() {
        Funcion funcion = ventanaPrincipal.getFuncionActual();
        if (funcion == null) return;
        int cantidad = (Integer) spinnerCantidad.getValue();
        double total = funcion.getPrecio() * cantidad;
        lblTotal.setText(String.format("Total: $%,.2f", total));
    }

    private void spinnerCantidadStateChanged(javax.swing.event.ChangeEvent evt) {
        actualizarTotal();
    }

    @Override
    public void onShow() {
        Funcion funcion = ventanaPrincipal.getFuncionActual();
        if (funcion == null) return;

        // Calcular butacas disponibles
        List<Butaca> todas = database.ButacaDAO.obtenerPorSala(funcion.getIdSala());
        List<Integer> idsOcupadas = database.ReservaDAO.obtenerDetallePorFuncion(funcion.getIdFuncion());

        int disponibles = 0;
        for (Butaca b : todas) {
            if (!idsOcupadas.contains(b.getIdButaca())) {
                disponibles++;
            }
        }

        lblInfo.setText("Butacas disponibles para esta funcion: " + disponibles);

        // Si ya hay una cantidad seteada (viniendo de butacas), mantenerla
        int valorActual = ventanaPrincipal.getCantidadEntradas();
        if (valorActual < 1 || valorActual > disponibles) {
            valorActual = 1;
        }

        // Desconectar listener temporalmente para no disparar actualizarTotal al setear el modelo
        spinnerCantidad.removeChangeListener(this::spinnerCantidadStateChanged);
        spinnerCantidad.setModel(new javax.swing.SpinnerNumberModel(valorActual, 1, Math.max(disponibles, 1), 1));
        spinnerCantidad.addChangeListener(this::spinnerCantidadStateChanged);

        actualizarTotal();
    }

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {
        int cantidad = (Integer) spinnerCantidad.getValue();
        ventanaPrincipal.setCantidadEntradas(cantidad);
        ventanaPrincipal.setButacasSeleccionadas(null);
        ventanaPrincipal.mostrarPantalla("butacas");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.setCantidadEntradas(0);
        ventanaPrincipal.mostrarPantalla("peliculas");
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JSpinner spinnerCantidad;
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnVolver;
}
