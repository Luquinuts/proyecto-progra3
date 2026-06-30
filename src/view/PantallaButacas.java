package view;

import database.ReservaDAO;
import java.util.List;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JToggleButton;

public class PantallaButacas extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;
    private java.util.Map<Integer, javax.swing.JToggleButton> butacaMap;

    public PantallaButacas(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        this.butacaMap = new java.util.HashMap<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblInfo = new javax.swing.JLabel();
        scrollButacas = new javax.swing.JScrollPane();
        panelButacas = new javax.swing.JPanel();
        btnReservar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        lblInfo.setText("Seleccione sus butacas");
        lblInfo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scrollButacas.setViewportView(panelButacas);

        btnReservar.setText("Reservar");
        btnReservar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnReservar.addActionListener(this::btnReservarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50)
                .addComponent(scrollButacas, 600, 600, 600)
                .addGap(50))
            .addGroup(layout.createSequentialGroup()
                .addGap(250)
                .addComponent(btnReservar)
                .addGap(30)
                .addComponent(btnVolver)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(20)
            .addComponent(lblInfo)
            .addGap(10)
            .addComponent(scrollButacas, 300, 300, 300)
            .addGap(20)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnReservar)
                .addComponent(btnVolver))
            .addGap(20)
        );
    }

    private void cargarButacas() {
        panelButacas.removeAll();
        butacaMap.clear();

        model.Funcion funcion = ventanaPrincipal.getFuncionActual();
        if (funcion == null) return;

        lblInfo.setText("Butacas para la funcion del " + funcion.getFecha()
                + " - Sala: " + funcion.getIdSala());

        // Obtener todas las butacas de la sala y las ya ocupadas
        java.util.List<model.Butaca> todas = database.ButacaDAO.obtenerPorSala(funcion.getIdSala());
        java.util.List<Integer> idsOcupadas = database.ReservaDAO.obtenerDetallePorFuncion(funcion.getIdFuncion());

        if (todas.isEmpty()) {
            lblInfo.setText("No hay butacas disponibles para esta funcion");
            return;
        }

        // Determinar filas distintas y maximo numero
        java.util.Set<String> filasSet = new java.util.LinkedHashSet<>();
        int maxNumero = 0;
        for (model.Butaca b : todas) {
            filasSet.add(b.getFila());
            if (b.getNumero() > maxNumero) {
                maxNumero = b.getNumero();
            }
        }
        java.util.List<String> filas = new java.util.ArrayList<>(filasSet);

        // Crear grid con filas y columnas
        int filasCount = filas.size();
        int colsCount = maxNumero;
        panelButacas.setLayout(new java.awt.GridLayout(filasCount, colsCount, 5, 5));

        for (String fila : filas) {
            for (int num = 1; num <= maxNumero; num++) {
                model.Butaca butacaEncontrada = null;
                for (model.Butaca b : todas) {
                    if (b.getFila().equals(fila) && b.getNumero() == num) {
                        butacaEncontrada = b;
                        break;
                    }
                }

                if (butacaEncontrada != null) {
                    javax.swing.JToggleButton btn = new javax.swing.JToggleButton(
                            butacaEncontrada.getFila() + butacaEncontrada.getNumero());
                    boolean ocupada = idsOcupadas.contains(butacaEncontrada.getIdButaca());

                    if (ocupada) {
                        btn.setEnabled(false);
                        btn.setSelected(true);
                        btn.setBackground(java.awt.Color.RED);
                    } else {
                        btn.setEnabled(true);
                        btn.setSelected(false);
                        btn.setBackground(java.awt.Color.GREEN);
                    }
                    btn.setOpaque(true);
                    btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));

                    final int idButacaFinal = butacaEncontrada.getIdButaca();
                    btn.addItemListener(e -> {
                        javax.swing.JToggleButton source = (javax.swing.JToggleButton) e.getSource();
                        if (source.isSelected()) {
                            source.setBackground(java.awt.Color.ORANGE);
                        } else {
                            source.setBackground(java.awt.Color.GREEN);
                        }
                    });

                    btn.putClientProperty("idButaca", butacaEncontrada.getIdButaca());
                    butacaMap.put(butacaEncontrada.getIdButaca(), btn);
                    panelButacas.add(btn);
                } else {
                    // Celda vacia para mantener la cuadricula
                    panelButacas.add(new javax.swing.JLabel(""));
                }
            }
        }

        panelButacas.revalidate();
        panelButacas.repaint();
    }

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {
        // Get selected butacas from toggle buttons
        List<Integer> seleccionadas = new ArrayList<>();
        for (Component comp : panelButacas.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) comp;
                if (btn.isSelected() && btn.isEnabled()) {
                    seleccionadas.add((Integer) btn.getClientProperty("idButaca"));
                }
            }
        }

        if (seleccionadas.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Seleccione al menos una butaca.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar disponibilidad (sin persistir)
        boolean disponibles = ReservaDAO.validarDisponibilidad(
            ventanaPrincipal.getFuncionActual(), seleccionadas);

        if (!disponibles) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Alguna butaca ya fue reservada por otro usuario.",
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            cargarButacas();
            return;
        }

        // Almacenar seleccion temporal en memoria y pasar a confirmacion
        ventanaPrincipal.setButacasSeleccionadas(seleccionadas);
        ventanaPrincipal.mostrarPantalla("confirmacion");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("peliculas");
    }

    @Override
    public void onShow() {
        cargarButacas();
    }

    private javax.swing.JLabel lblInfo;
    private javax.swing.JScrollPane scrollButacas;
    private javax.swing.JPanel panelButacas;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnVolver;
}
