package view;

import java.awt.CardLayout;
import java.awt.Component;

public class VentanaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = 
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());

    public VentanaPrincipal() {
        initComponents();
        
        // Las pantallas se agregan al contenedor DESPUES de initComponents
        // NOTA: El usuario debe agregar estas lineas DENTRO de initComponents
        // desde el disenador de Netbeans, arrastrando JPanels al contenedor
        contenedor.add(new PantallaMenu(this), "menu");
        contenedor.add(new PantallaCliente(this), "cliente");
        contenedor.add(new PantallaPeliculas(this), "peliculas");
        contenedor.add(new PantallaButacas(this), "butacas");
        contenedor.add(new PantallaConfirmacion(this), "confirmacion");
        contenedor.add(new PantallaAdmin(this), "admin");
        contenedor.add(new PantallaReporteSala(this), "reporte");
        contenedor.add(new PantallaAdminButacas(this), "admin-butacas");
        contenedor.add(new PantallaFunciones(this), "funciones");
        contenedor.add(new PantallaFormFuncion(this), "form-funcion");
        contenedor.add(new PantallaCantidad(this), "cantidad");
        
        mostrarPantalla("menu");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        contenedor = new javax.swing.JPanel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // NOTA: Reemplazar este layout manual por el generado por Netbeans
        // 1. En Netbeans, crear JFrame, arrastrar JPanel como contenedor
        // 2. Seleccionar el JPanel, cambiar layout a CardLayout
        // 3. El GroupLayout lo genera Netbeans automaticamente
        contenedor.setLayout(new java.awt.CardLayout());
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        
        pack();
    }

    public void mostrarPantalla(String nombre) {
        CardLayout cl = (CardLayout) contenedor.getLayout();
        cl.show(contenedor, nombre);
        for (Component c : contenedor.getComponents()) {
            if (c.isVisible()) {
                if (c instanceof IPantallaBase) {
                    ((IPantallaBase) c).onShow();
                }
                break;
            }
        }
    }

    // Shared session state
    private model.Cliente clienteActual = null;
    private model.Funcion funcionActual = null;
    private java.util.List<Integer> butacasSeleccionadas = null;
    private model.Funcion funcionEditar = null;
    private int cantidadEntradas = 0;

    public model.Cliente getClienteActual() { return clienteActual; }
    public void setClienteActual(model.Cliente c) { this.clienteActual = c; }
    public model.Funcion getFuncionActual() { return funcionActual; }
    public void setFuncionActual(model.Funcion f) { this.funcionActual = f; }
    public java.util.List<Integer> getButacasSeleccionadas() { return butacasSeleccionadas; }
    public void setButacasSeleccionadas(java.util.List<Integer> ids) { this.butacasSeleccionadas = ids; }
    public model.Funcion getFuncionEditar() { return funcionEditar; }
    public void setFuncionEditar(model.Funcion f) { this.funcionEditar = f; }
    public int getCantidadEntradas() { return cantidadEntradas; }
    public void setCantidadEntradas(int n) { this.cantidadEntradas = n; }

    // Variables declaration
    private javax.swing.JPanel contenedor;
    // End of variables declaration
}
