package view;

import model.ReporteSala;
import java.util.ArrayList;

public class PantallaReporteSala extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;

    public PantallaReporteSala(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaReporte = new javax.swing.JTable();
        btnVolver = new javax.swing.JButton();

        Theme.stylePanel(this);

        lblTitulo.setText("Reporte de Recaudacion por Sala");
        Theme.styleTitle(lblTitulo);

        Theme.styleTable(tablaReporte);
        tablaReporte.setEnabled(false); // read-only
        scrollTabla.setViewportView(tablaReporte);

        btnVolver.setText("Volver");
        Theme.styleButtonSecondary(btnVolver);
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50)
                .addComponent(scrollTabla, 600, 600, 600)
                .addGap(50))
            .addGroup(layout.createSequentialGroup()
                .addGap(300)
                .addComponent(btnVolver)
                .addContainerGap(300, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(20)
            .addComponent(lblTitulo)
            .addGap(15)
            .addComponent(scrollTabla, 300, 300, 300)
            .addGap(15)
            .addComponent(btnVolver)
            .addGap(20)
        );
    }

    private void cargarReporte() {
        ArrayList<ReporteSala> datos = database.SalaDAO.obtenerReporte();

        String[] columnas = {"Sala", "Funciones", "Entradas Vendidas", "Recaudacion Total"};
        Object[][] filas = new Object[datos.size()][4];

        for (int i = 0; i < datos.size(); i++) {
            ReporteSala r = datos.get(i);
            filas[i][0] = r.getNombre();
            filas[i][1] = r.getCantidadFunciones();
            filas[i][2] = r.getEntradasVendidas();
            filas[i][3] = "$" + String.format("%,.2f", r.getRecaudacionTotal());
        }

        tablaReporte.setModel(new javax.swing.table.DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("admin");
    }

    @Override
    public void onShow() {
        cargarReporte();
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaReporte;
    private javax.swing.JButton btnVolver;
}
