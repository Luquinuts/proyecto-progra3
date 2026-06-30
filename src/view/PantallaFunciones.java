package view;

import model.Funcion;
import model.Pelicula;
import model.Sala;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PantallaFunciones extends javax.swing.JPanel implements IPantallaBase {

    private VentanaPrincipal ventanaPrincipal;
    private Map<Integer, String> peliculasMap;
    private Map<Integer, String> salasMap;
    private ArrayList<Funcion> funciones;

    public PantallaFunciones(VentanaPrincipal vp) {
        this.ventanaPrincipal = vp;
        this.peliculasMap = new HashMap<>();
        this.salasMap = new HashMap<>();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblTitulo = new javax.swing.JLabel();
        scrollTabla = new javax.swing.JScrollPane();
        tablaFunciones = new javax.swing.JTable();
        btnNueva = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();

        setBackground(new java.awt.Color(240, 240, 240));

        lblTitulo.setText("ABM de Funciones");
        lblTitulo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        tablaFunciones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
        tablaFunciones.setRowHeight(28);
        tablaFunciones.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
        tablaFunciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollTabla.setViewportView(tablaFunciones);

        btnNueva.setText("Nueva Funcion");
        btnNueva.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnNueva.addActionListener(this::btnNuevaActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnVolver.setText("Volver");
        btnVolver.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(30)
                .addComponent(scrollTabla, 640, 640, 640)
                .addGap(30))
            .addGroup(layout.createSequentialGroup()
                .addGap(100)
                .addComponent(btnNueva)
                .addGap(20)
                .addComponent(btnEditar)
                .addGap(20)
                .addComponent(btnEliminar)
                .addGap(20)
                .addComponent(btnVolver)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(15)
            .addComponent(lblTitulo)
            .addGap(10)
            .addComponent(scrollTabla, 280, 280, 280)
            .addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnNueva)
                .addComponent(btnEditar)
                .addComponent(btnEliminar)
                .addComponent(btnVolver))
            .addGap(15)
        );
    }

    @Override
    public void onShow() {
        cargarFunciones();
    }

    private void cargarFunciones() {
        // Pre-cargar mapas de nombres
        peliculasMap.clear();
        for (Pelicula p : database.PeliculaDAO.obtenerTodas()) {
            peliculasMap.put(p.getIdPelicula(), p.getTitulo());
        }
        salasMap.clear();
        for (Sala s : database.SalaDAO.obtenerTodas()) {
            salasMap.put(s.getIdSala(), s.getNombre());
        }

        funciones = database.FuncionDAO.obtenerTodas();

        String[] columnas = {"ID", "Pelicula", "Sala", "Fecha", "Hora", "Precio", "Activa"};
        Object[][] filas = new Object[funciones.size()][7];

        for (int i = 0; i < funciones.size(); i++) {
            Funcion f = funciones.get(i);
            filas[i][0] = f.getIdFuncion();
            filas[i][1] = peliculasMap.getOrDefault(f.getIdPelicula(), "?");
            filas[i][2] = salasMap.getOrDefault(f.getIdSala(), "?");
            filas[i][3] = f.getFecha();
            filas[i][4] = f.getHora();
            filas[i][5] = "$" + String.format("%.2f", f.getPrecio());
            filas[i][6] = f.isActiva() ? "Si" : "No";
        }

        tablaFunciones.setModel(new javax.swing.table.DefaultTableModel(filas, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private int getFuncionSeleccionadaIndex() {
        int row = tablaFunciones.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Seleccione una funcion de la tabla.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return row;
    }

    private void btnNuevaActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("form-funcion");
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {
        int row = getFuncionSeleccionadaIndex();
        if (row < 0) return;

        Funcion f = funciones.get(row);
        ventanaPrincipal.setFuncionEditar(f);
        ventanaPrincipal.mostrarPantalla("form-funcion");
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int row = getFuncionSeleccionadaIndex();
        if (row < 0) return;

        Funcion f = funciones.get(row);

        // Verificar si tiene reservas
        if (database.FuncionDAO.tieneReservas(f.getIdFuncion())) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se puede eliminar la funcion porque ya tiene reservas asociadas.",
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Si ya esta inactiva
        if (!f.isActiva()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La funcion ya se encuentra dada de baja.",
                    "Informacion", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                "Confirma que desea dar de baja la funcion seleccionada?",
                "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            database.FuncionDAO.desactivar(f.getIdFuncion());
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Funcion dada de baja correctamente.",
                    "Informacion", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            cargarFunciones();
        }
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        ventanaPrincipal.mostrarPantalla("admin");
    }

    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JTable tablaFunciones;
    private javax.swing.JButton btnNueva;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnVolver;
}
