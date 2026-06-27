package palavecinoexamen2;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

public class IUMonitoreo extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IUMonitoreo.class.getName());

    public IUMonitoreo() {
        initComponents();
        cargarProductos();
        cargarPostulantes();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Monitoreo de Ofertas en Tiempo Real");

        jLabel1.setText("Producto:");
        jLabel2.setText("Postulante:");
        jLabel3.setText("Monto:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{"Postulante", "DNI", "Monto", "Fecha"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jButton1.setText("Ofertar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Volver");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(187, 187, 187)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void cargarProductos() {
        ArrayList<Producto> productos = Producto.obtener_productos();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Producto p : productos) {
            model.addElement(p.getId_producto() + " - " + p.getNombre() + " ($" + p.getPrecio_base() + ")");
        }
        jComboBox1.setModel(model);
        if (model.getSize() > 0) {
            actualizarTabla();
        }
    }

    private void cargarPostulantes() {
        ArrayList<Postulante> postulantes = Postulante.obtener_postulantes();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Postulante p : postulantes) {
            model.addElement(p.getDni() + " - " + p.getNombre() + " " + p.getApellido());
        }
        jComboBox2.setModel(model);
    }

    private void actualizarTabla() {
        String prodSel = (String) jComboBox1.getSelectedItem();
        if (prodSel == null) return;

        int idProducto = Integer.parseInt(prodSel.split(" - ")[0]);
        ArrayList<Oferta> ofertas = Oferta.obtener_ofertas_por_producto(idProducto);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        ArrayList<Postulante> postulantes = Postulante.obtener_postulantes();

        for (Oferta o : ofertas) {
            String nombrePostulante = String.valueOf(o.getPostulante_dni());
            for (Postulante p : postulantes) {
                if (p.getDni() == o.getPostulante_dni()) {
                    nombrePostulante = p.getNombre() + " " + p.getApellido();
                    break;
                }
            }
            model.addRow(new Object[]{
                nombrePostulante,
                o.getPostulante_dni(),
                "$" + o.getMonto(),
                o.getFecha_creacion()
            });
        }
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        actualizarTabla();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String prodSel = (String) jComboBox1.getSelectedItem();
            String postSel = (String) jComboBox2.getSelectedItem();
            if (prodSel == null || postSel == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar producto y postulante");
                return;
            }

            int idProducto = Integer.parseInt(prodSel.split(" - ")[0]);
            int dniPostulante = Integer.parseInt(postSel.split(" - ")[0]);
            double monto = Double.parseDouble(jTextField1.getText());

            Producto producto = new Producto(idProducto, "", 0);
            Postulante postulante = new Postulante("", "", dniPostulante, "");

            Oferente_Prosessor_Thread thread = new Oferente_Prosessor_Thread(postulante, producto, monto);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            actualizarTabla();
            jTextField1.setText("");
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: monto invalido", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        IUMenu menu = new IUMenu();
        this.dispose();
        menu.setVisible(true);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new IUMonitoreo().setVisible(true));
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
}
