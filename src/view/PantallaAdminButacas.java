package view;

import model.Butaca;
import model.Funcion;
import model.Pelicula;
import model.Sala;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PantallaAdminButacas extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;
    private Map<Integer, String> peliculasMap;
    private Map<Integer, String> salasMap;
    private List<Funcion> funciones;

    public PantallaAdminButacas(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        this.peliculasMap = new HashMap<>();
        this.salasMap = new HashMap<>();
        this.funciones = new ArrayList<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        cmbFunciones = new javax.swing.JComboBox<>();
        scrollButacas = new javax.swing.JScrollPane();
        panelButacas = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblTitulo.setText("Mapa de Butacas - Administracion");
        Theme.styleTitle(lblTitulo);

        cmbFunciones.setFont(Theme.FONT_BODY);
        cmbFunciones.addActionListener(this::cmbFuncionesActionPerformed);

        scrollButacas.setViewportView(panelButacas);

        lblInfo.setFont(Theme.FONT_BODY);
        lblInfo.setForeground(Theme.TEXT_SECONDARY);
        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        btnVolver.setText("Volver");
        Theme.styleButtonSecondary(btnVolver);
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(100)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbFunciones, 500, 500, 500)
                    .addComponent(scrollButacas, 500, 500, 500)
                    .addComponent(lblInfo, 500, 500, 500))
                .addGap(100))
            .addGroup(layout.createSequentialGroup()
                .addGap(300)
                .addComponent(btnVolver)
                .addContainerGap(300, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(15)
            .addComponent(lblTitulo)
            .addGap(10)
            .addComponent(cmbFunciones, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(scrollButacas, 280, 280, 280)
            .addGap(5)
            .addComponent(lblInfo)
            .addGap(10)
            .addComponent(btnVolver)
            .addGap(15)
        );
    }

    @Override
    public void onShow() {
        cargarFunciones();
    }

    private void cargarFunciones() {
        // Pre-cargar mapas de nombres
        for (Pelicula p : database.PeliculaDAO.obtenerTodas()) {
            peliculasMap.put(p.getIdPelicula(), p.getTitulo());
        }
        for (Sala s : database.SalaDAO.obtenerTodas()) {
            salasMap.put(s.getIdSala(), s.getNombre());
        }

        funciones = database.FuncionDAO.obtenerTodas();
        cmbFunciones.removeAllItems();

        if (funciones.isEmpty()) {
            lblInfo.setText("No hay funciones cargadas.");
            return;
        }

        for (Funcion f : funciones) {
            String titulo = peliculasMap.getOrDefault(f.getIdPelicula(), "?");
            String sala = salasMap.getOrDefault(f.getIdSala(), "?");
            cmbFunciones.addItem(titulo + " - " + f.getFecha() + " " + f.getHora() + " - " + sala);
        }

        // Mostrar la primera por defecto
        cmbFunciones.setSelectedIndex(0);
        mostrarButacas(0);
    }

    private void cmbFuncionesActionPerformed(java.awt.event.ActionEvent evt) {
        int idx = cmbFunciones.getSelectedIndex();
        if (idx >= 0 && idx < funciones.size()) {
            mostrarButacas(idx);
        }
    }

    private void mostrarButacas(int index) {
        panelButacas.removeAll();
        Funcion funcion = funciones.get(index);

        List<Butaca> todas = database.ButacaDAO.obtenerPorSala(funcion.getIdSala());
        List<Integer> idsOcupadas = database.ReservaDAO.obtenerDetallePorFuncion(funcion.getIdFuncion());

        if (todas.isEmpty()) {
            lblInfo.setText("No hay butacas cargadas para esta sala.");
            panelButacas.revalidate();
            panelButacas.repaint();
            return;
        }

        // Determinar filas y columnas
        java.util.Set<String> filasSet = new java.util.LinkedHashSet<>();
        int maxNumero = 0;
        for (Butaca b : todas) {
            filasSet.add(b.getFila());
            if (b.getNumero() > maxNumero) {
                maxNumero = b.getNumero();
            }
        }
        List<String> filas = new java.util.ArrayList<>(filasSet);
        int filasCount = filas.size();
        int colsCount = maxNumero;

        panelButacas.setLayout(new java.awt.GridLayout(filasCount, colsCount, 5, 5));

        int libres = 0;
        int ocupadas = 0;

        for (String fila : filas) {
            for (int num = 1; num <= maxNumero; num++) {
                Butaca butacaEncontrada = null;
                for (Butaca b : todas) {
                    if (b.getFila().equals(fila) && b.getNumero() == num) {
                        butacaEncontrada = b;
                        break;
                    }
                }

                if (butacaEncontrada != null) {
                    javax.swing.JLabel lbl = new javax.swing.JLabel(
                            butacaEncontrada.getFila() + butacaEncontrada.getNumero(),
                            javax.swing.SwingConstants.CENTER);
                    lbl.setFont(Theme.FONT_SMALL);
                    lbl.setOpaque(true);
                    lbl.setBorder(javax.swing.BorderFactory.createLineBorder(Theme.BORDER));

                    boolean ocupada = idsOcupadas.contains(butacaEncontrada.getIdButaca());
                    if (ocupada) {
                        lbl.setBackground(Theme.BUTACA_TAKEN);
                        lbl.setForeground(Theme.TEXT_PRIMARY);
                        ocupadas++;
                    } else {
                        lbl.setBackground(Theme.BUTACA_FREE);
                        lbl.setForeground(Theme.TEXT_PRIMARY);
                        libres++;
                    }

                    panelButacas.add(lbl);
                } else {
                    panelButacas.add(new javax.swing.JLabel(""));
                }
            }
        }

        String salaNombre = salasMap.getOrDefault(funcion.getIdSala(), "?");
        lblInfo.setText("Sala: " + salaNombre + " | Libres: " + libres + " | Ocupadas: " + ocupadas
                + " | Capacidad: " + (libres + ocupadas));

        panelButacas.revalidate();
        panelButacas.repaint();
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("admin");
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JComboBox<String> cmbFunciones;
    private javax.swing.JScrollPane scrollButacas;
    private javax.swing.JPanel panelButacas;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JButton btnVolver;
}
