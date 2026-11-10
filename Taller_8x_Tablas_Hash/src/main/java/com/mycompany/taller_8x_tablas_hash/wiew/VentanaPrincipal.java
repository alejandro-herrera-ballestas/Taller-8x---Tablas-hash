package com.mycompany.taller_8x_tablas_hash.wiew;
import javax.swing.*; 


public class VentanaPrincipal extends javax.swing.JFrame {
    
    //Constructor de los componentes
    public VentanaPrincipal() {
        initComponents(); 
        this.setLocationRelativeTo(null);
        this.setTitle("Tabla Hash - Estructura de Datos");
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // aleta de Colores
        java.awt.Color colorFondo = new java.awt.Color(30, 30, 36);        
        java.awt.Color colorPaneles = new java.awt.Color(43, 43, 54);      
        java.awt.Color colorTextoPrincipal = new java.awt.Color(230, 230, 230); 
        java.awt.Color colorAcento = new java.awt.Color(138, 43, 226);     
        java.awt.Color colorBotones = new java.awt.Color(60, 60, 75);
        
        java.awt.Font fuenteTitulos = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18);
        java.awt.Font fuenteConsola = new java.awt.Font("Consolas", java.awt.Font.PLAIN, 16);
        java.awt.Font fuenteBotones = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14);

        // Inicialización de Componentes
        javax.swing.JPanel panelFile = new javax.swing.JPanel();
        panelFile.setBackground(colorPaneles);
        panelFile.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(colorAcento), "File", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                fuenteTitulos, colorTextoPrincipal));
        panelFile.setLayout(null); 
        
        javax.swing.JPanel panelOperations = new javax.swing.JPanel();
        panelOperations.setBackground(colorPaneles);
        panelOperations.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(colorAcento), "Operations", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                fuenteTitulos, colorTextoPrincipal));
        panelOperations.setLayout(null);
        
        btnOpen = crearBotonEstilizado("Open", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnSave = crearBotonEstilizado("Save", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnClose = crearBotonEstilizado("Close", colorBotones, colorTextoPrincipal, fuenteBotones);
        
        btnInsert = crearBotonEstilizado("Insert", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnDelete = crearBotonEstilizado("Delete", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnSearch = crearBotonEstilizado("Search", colorBotones, colorTextoPrincipal, fuenteBotones);
        
        javax.swing.JLabel lblTituloColisiones = new javax.swing.JLabel("Collisions", javax.swing.SwingConstants.CENTER);
        lblTituloColisiones.setFont(fuenteTitulos);
        lblTituloColisiones.setForeground(colorTextoPrincipal);
        
        lblNumColisiones = new javax.swing.JLabel("0", javax.swing.SwingConstants.CENTER);
        lblNumColisiones.setOpaque(true);
        lblNumColisiones.setBackground(colorPaneles);
        lblNumColisiones.setForeground(new java.awt.Color(255, 85, 85)); 
        lblNumColisiones.setBorder(javax.swing.BorderFactory.createLineBorder(colorAcento, 2));
        lblNumColisiones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 36)); 
        
        javax.swing.JLabel lblT = new javax.swing.JLabel("Hash Table Structure (T)", javax.swing.SwingConstants.CENTER);
        lblT.setFont(fuenteTitulos);
        lblT.setForeground(colorTextoPrincipal);
        
        txtAreaTabla = new javax.swing.JTextArea();
        txtAreaTabla.setEditable(false);
        txtAreaTabla.setBackground(new java.awt.Color(20, 20, 25)); 
        txtAreaTabla.setForeground(new java.awt.Color(152, 251, 152)); 
        txtAreaTabla.setFont(fuenteConsola);
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(txtAreaTabla);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(colorAcento));
        
        lblRutaArchivo = new javax.swing.JLabel(" Ruta del archivo CSV aparecerá aquí...");
        lblRutaArchivo.setOpaque(true);
        lblRutaArchivo.setBackground(colorPaneles);
        lblRutaArchivo.setForeground(colorTextoPrincipal);
        lblRutaArchivo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblRutaArchivo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, colorAcento));

        radioDivision = new javax.swing.JRadioButton("División", true);
        estilizarRadioButton(radioDivision, colorFondo, colorTextoPrincipal);
        
        radioMultiplicacion = new javax.swing.JRadioButton("Multiplicación");
        estilizarRadioButton(radioMultiplicacion, colorFondo, colorTextoPrincipal);
        
        javax.swing.ButtonGroup grupoFunciones = new javax.swing.ButtonGroup();
        grupoFunciones.add(radioDivision);
        grupoFunciones.add(radioMultiplicacion);

        // Ventana
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        getContentPane().setBackground(colorFondo);
        setResizable(false);

        // Elementos  
        
        panelFile.setBounds(30, 30, 140, 190);
        panelOperations.setBounds(190, 30, 140, 190);
        getContentPane().add(panelFile);
        getContentPane().add(panelOperations);

        btnOpen.setBounds(15, 35, 110, 40);
        btnSave.setBounds(15, 85, 110, 40);
        btnClose.setBounds(15, 135, 110, 40);
        panelFile.add(btnOpen);
        panelFile.add(btnSave);
        panelFile.add(btnClose);

        btnInsert.setBounds(15, 35, 110, 40);
        btnDelete.setBounds(15, 85, 110, 40);
        btnSearch.setBounds(15, 135, 110, 40);
        panelOperations.add(btnInsert);
        panelOperations.add(btnDelete);
        panelOperations.add(btnSearch);

        // Centramos los Radio Buttons
        radioDivision.setBounds(480, 80, 120, 30);
        radioMultiplicacion.setBounds(480, 130, 150, 30);
        getContentPane().add(radioDivision);
        getContentPane().add(radioMultiplicacion);

        // Movemos las Colisiones a la derecha extrema
        lblTituloColisiones.setBounds(880, 40, 120, 30);
        lblNumColisiones.setBounds(880, 75, 120, 70);
        getContentPane().add(lblTituloColisiones);
        getContentPane().add(lblNumColisiones);

        // Panel Central 
        lblT.setBounds(30, 240, 1000, 30);
        getContentPane().add(lblT);
        
        scrollPane.setBounds(30, 275, 1000, 350); 
        getContentPane().add(scrollPane);

        // Ruta abajo
        lblRutaArchivo.setBounds(0, 645, 1080, 35);
        getContentPane().add(lblRutaArchivo);
        
        setSize(1080, 720);
    }
    
    // Método auxiliar actualizado para recibir la fuente
    private JButton crearBotonEstilizado(String texto, java.awt.Color bg, java.awt.Color fg, java.awt.Font fuente) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(fuente);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new java.awt.Color(80, 80, 100)), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return btn;
    }
    
    private void estilizarRadioButton(JRadioButton rb, java.awt.Color bg, java.awt.Color fg) {
        rb.setBackground(bg);
        rb.setForeground(fg);
        rb.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16)); // Texto de radio button más grande
        rb.setFocusPainted(false);
    }

    // Getters de  File
    public JButton getBtnOpen() { return btnOpen; }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnClose() { return btnClose; }

    // Getters de  Operations
    public JButton getBtnInsert() { return btnInsert; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    // Getters de Hash
    public JRadioButton getRadioDivision() { return radioDivision; }
    public JRadioButton getRadioMultiplicacion() { return radioMultiplicacion; }
    // Getters de visualización
    public JLabel getLblNumColisiones() { return lblNumColisiones; }
    public JLabel getLblRutaArchivo() { return lblRutaArchivo; }
    public JTextArea getTxtAreaTabla() { return txtAreaTabla; }

    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel lblNumColisiones;
    private javax.swing.JLabel lblRutaArchivo;
    private javax.swing.JRadioButton radioDivision;
    private javax.swing.JRadioButton radioMultiplicacion;
    private javax.swing.JTextArea txtAreaTabla;
}
