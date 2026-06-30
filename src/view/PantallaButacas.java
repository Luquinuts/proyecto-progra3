package view;

import database.ReservaDAO;
import java.util.List;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JToggleButton;

public class PantallaButacas extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;
    private java.util.Map<Integer, javax.swing.JToggleButton> butacaMap;
    private int maxSeleccion;

    public PantallaButacas(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        this.butacaMap = new java.util.HashMap<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblInfo = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        scrollButacas = new javax.swing.JScrollPane();
        panelButacas = new javax.swing.JPanel();
        btnReservar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblInfo.setText("Seleccione sus butacas");
        lblInfo.setFont(Theme.FONT_SUBTITLE);
        lblInfo.setForeground(Theme.TEXT_PRIMARY);
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblContador.setFont(Theme.FONT_BODY);
        lblContador.setForeground(Theme.TEXT_SECONDARY);
        lblContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        scrollButacas.setViewportView(panelButacas);

        btnReservar.setText("Reservar");
        Theme.styleButtonConfirm(btnReservar);
        btnReservar.addActionListener(this::btnReservarActionPerformed);

        btnVolver.setText("Volver");
        Theme.styleButtonSecondary(btnVolver);
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGap(15)
            .addComponent(lblInfo)
            .addGap(5)
            .addComponent(lblContador)
            .addGap(5)
            .addComponent(scrollButacas, 300, 300, 300)
            .addGap(15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnReservar)
                .addComponent(btnVolver))
            .addGap(15)
        );
    }

    private int contarSeleccionadas() {
        int count = 0;
        for (Component comp : panelButacas.getComponents()) {
            if (comp instanceof JToggleButton) {
                JToggleButton btn = (JToggleButton) comp;
                if (btn.isSelected() && btn.isEnabled()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void actualizarContador() {
        int seleccionadas = contarSeleccionadas();
        lblContador.setText("Seleccionadas: " + seleccionadas + " de " + maxSeleccion);
    }

    private void cargarButacas() {
        panelButacas.removeAll();
        butacaMap.clear();

        model.Funcion funcion = ventanaPrincipal.getFuncionActual();
        if (funcion == null) return;

        maxSeleccion = ventanaPrincipal.getCantidadEntradas();

        lblInfo.setText("Butacas para la funcion del " + funcion.getFecha()
                + " - Sala: " + funcion.getIdSala());

        List<model.Butaca> todas = database.ButacaDAO.obtenerPorSala(funcion.getIdSala());
        List<Integer> idsOcupadas = database.ReservaDAO.obtenerDetallePorFuncion(funcion.getIdFuncion());

        if (todas.isEmpty()) {
            lblInfo.setText("No hay butacas disponibles para esta funcion");
            return;
        }

        java.util.Set<String> filasSet = new java.util.LinkedHashSet<>();
        int maxNumero = 0;
        for (model.Butaca b : todas) {
            filasSet.add(b.getFila());
            if (b.getNumero() > maxNumero) {
                maxNumero = b.getNumero();
            }
        }
        List<String> filas = new java.util.ArrayList<>(filasSet);

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
                        btn.setBackground(Theme.BUTACA_TAKEN);
                    } else {
                        btn.setEnabled(true);
                        btn.setSelected(false);
                        btn.setBackground(Theme.BUTACA_FREE);
                    }
                    btn.setOpaque(true);
                    btn.setFont(Theme.FONT_SMALL);

                    // ActionListener: solo se dispara por clicks del usuario (no por setSelected)
                    btn.addActionListener(e -> {
                        javax.swing.JToggleButton source = (javax.swing.JToggleButton) e.getSource();
                        if (source.isSelected()) {
                            if (contarSeleccionadas() > maxSeleccion) {
                                source.setSelected(false);
                                javax.swing.JOptionPane.showMessageDialog(PantallaButacas.this,
                                        "Solo puede seleccionar hasta " + maxSeleccion + " butacas.\n"
                                        + "Deseleccione una butaca primero.",
                                        "Limite alcanzado", javax.swing.JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                            source.setBackground(Theme.BUTACA_SELECTED);
                        } else {
                            source.setBackground(Theme.BUTACA_FREE);
                        }
                        actualizarContador();
                    });

                    btn.putClientProperty("idButaca", butacaEncontrada.getIdButaca());
                    butacaMap.put(butacaEncontrada.getIdButaca(), btn);
                    panelButacas.add(btn);
                } else {
                    panelButacas.add(new javax.swing.JLabel(""));
                }
            }
        }

        actualizarContador();
        panelButacas.revalidate();
        panelButacas.repaint();
    }

    private void btnReservarActionPerformed(java.awt.event.ActionEvent evt) {
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

        // Validar que la cantidad coincida
        if (seleccionadas.size() != maxSeleccion) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Debe seleccionar exactamente " + maxSeleccion + " butacas.\n"
                    + "Ha seleccionado " + seleccionadas.size() + ".",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean disponibles = ReservaDAO.validarDisponibilidad(
            ventanaPrincipal.getFuncionActual(), seleccionadas);

        if (!disponibles) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Alguna butaca ya fue reservada por otro usuario.",
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            cargarButacas();
            return;
        }

        ventanaPrincipal.setButacasSeleccionadas(seleccionadas);
        ventanaPrincipal.mostrarPantalla("confirmacion");
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("cantidad");
    }

    @Override
    public void onShow() {
        cargarButacas();
    }

    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblContador;
    private javax.swing.JScrollPane scrollButacas;
    private javax.swing.JPanel panelButacas;
    private javax.swing.JButton btnReservar;
    private javax.swing.JButton btnVolver;
}
