package view;

public class PantallaConfirmacion extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaConfirmacion(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        lblPelicula = new javax.swing.JLabel();
        lblFuncion = new javax.swing.JLabel();
        lblSala = new javax.swing.JLabel();
        lblButacas = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        lblTitulo.setText("Resumen de su compra");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblCliente.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblPelicula.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblFuncion.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblSala.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblButacas.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblPrecio.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));

        btnConfirmar.setText("Confirmar Compra");
        btnConfirmar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnConfirmar.addActionListener(this::btnConfirmarActionPerformed);

        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(150)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCliente)
                    .addComponent(lblPelicula)
                    .addComponent(lblFuncion)
                    .addComponent(lblSala)
                    .addComponent(lblButacas)
                    .addComponent(lblPrecio))
                .addGap(150))
            .addGroup(layout.createSequentialGroup()
                .addGap(200)
                .addComponent(btnConfirmar)
                .addGap(30)
                .addComponent(btnCancelar)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(30)
            .addComponent(lblTitulo)
            .addGap(30)
            .addComponent(lblCliente)
            .addGap(10)
            .addComponent(lblPelicula)
            .addGap(10)
            .addComponent(lblFuncion)
            .addGap(10)
            .addComponent(lblSala)
            .addGap(10)
            .addComponent(lblButacas)
            .addGap(10)
            .addComponent(lblPrecio)
            .addGap(40)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnConfirmar)
                .addComponent(btnCancelar))
            .addGap(30)
        );
    }

    @Override
    public void onShow() {
        model.Cliente cliente = ventanaPrincipal.getClienteActual();
        model.Funcion funcion = ventanaPrincipal.getFuncionActual();
        java.util.List<Integer> idsButacas = ventanaPrincipal.getButacasSeleccionadas();

        if (cliente == null || funcion == null || idsButacas == null || idsButacas.isEmpty()) {
            lblCliente.setText("Error: faltan datos de la reserva");
            lblPelicula.setText("");
            lblFuncion.setText("");
            lblSala.setText("");
            lblButacas.setText("");
            lblPrecio.setText("");
            return;
        }

        // Datos del cliente
        lblCliente.setText("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());

        // Datos de la pelicula
        model.Pelicula pelicula = database.PeliculaDAO.obtenerPorId(funcion.getIdPelicula());
        if (pelicula != null) {
            lblPelicula.setText("Pelicula: " + pelicula.getTitulo());
        } else {
            lblPelicula.setText("Pelicula: (desconocida)");
        }

        // Datos de la funcion
        lblFuncion.setText("Funcion: " + funcion.getFecha() + " a las " + funcion.getHora());

        // Datos de la sala
        model.Sala sala = database.SalaDAO.obtenerPorId(funcion.getIdSala());
        if (sala != null) {
            lblSala.setText("Sala: " + sala.getNombre());
        } else {
            lblSala.setText("Sala: (desconocida)");
        }

        // Butacas seleccionadas
        StringBuilder butacasText = new StringBuilder("Butacas: ");
        for (int i = 0; i < idsButacas.size(); i++) {
            model.Butaca b = database.ButacaDAO.obtenerPorId(idsButacas.get(i));
            if (b != null) {
                butacasText.append(b.getFila()).append(b.getNumero());
            } else {
                butacasText.append("?");
            }
            if (i < idsButacas.size() - 1) {
                butacasText.append(", ");
            }
        }
        lblButacas.setText(butacasText.toString());

        // Precio total
        double total = funcion.getPrecio() * idsButacas.size();
        lblPrecio.setText("Total: $" + String.format("%.2f", total));
    }

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Compra realizada con exito!\nGracias por su compra.",
                "Compra Confirmada",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Clear session state
        ventanaPrincipal.setClienteActual(null);
        ventanaPrincipal.setFuncionActual(null);
        ventanaPrincipal.setButacasSeleccionadas(null);

        // Return to menu
        ventanaPrincipal.mostrarPantalla("menu");
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        // Limpiar sesion y volver al menu
        ventanaPrincipal.setClienteActual(null);
        ventanaPrincipal.setFuncionActual(null);
        ventanaPrincipal.setButacasSeleccionadas(null);
        ventanaPrincipal.mostrarPantalla("menu");
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblPelicula;
    private javax.swing.JLabel lblFuncion;
    private javax.swing.JLabel lblSala;
    private javax.swing.JLabel lblButacas;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnCancelar;
}
