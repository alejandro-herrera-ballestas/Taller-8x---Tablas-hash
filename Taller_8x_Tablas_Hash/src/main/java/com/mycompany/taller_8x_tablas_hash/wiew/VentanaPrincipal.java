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

        // Paleta de colores con java.awt.color
        java.awt.Color colorFondo = new java.awt.Color(30, 30, 36);        
        java.awt.Color colorPaneles = new java.awt.Color(43, 43, 54);      
        java.awt.Color colorTextoPrincipal = new java.awt.Color(230, 230, 230); 
        java.awt.Color colorAcento = new java.awt.Color(138, 43, 226);     
        java.awt.Color colorBotones = new java.awt.Color(60, 60, 75);
        
        // Fuente de letras con java.awt.Font
        java.awt.Font fuenteTitulos = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18);
        java.awt.Font fuenteConsola = new java.awt.Font("Consolas", java.awt.Font.PLAIN, 16);
        java.awt.Font fuenteBotones = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14);

        // Inicialización de Componentes 
        JPanel panelFile = new JPanel();
        panelFile.setBackground(colorPaneles);
        panelFile.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorAcento), "File", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                fuenteTitulos, colorTextoPrincipal));
        panelFile.setLayout(null); 
        
        JPanel panelOperations = new JPanel();
        panelOperations.setBackground(colorPaneles);
        panelOperations.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(colorAcento), "Operations", 
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, 
                fuenteTitulos, colorTextoPrincipal));
        panelOperations.setLayout(null);
        
        // Botones (agregamos colores y fuentes) 
        btnOpen = crearBotonEstilizado("Open", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnSave = crearBotonEstilizado("Save", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnClose = crearBotonEstilizado("Close", colorBotones, colorTextoPrincipal, fuenteBotones);
        
        btnInsert = crearBotonEstilizado("Insert", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnDelete = crearBotonEstilizado("Delete", colorBotones, colorTextoPrincipal, fuenteBotones);
        btnSearch = crearBotonEstilizado("Search", colorBotones, colorTextoPrincipal, fuenteBotones);
        //Agregado de info tecnica 
        btnInfo = crearBotonEstilizado("Info Técnica", colorBotones, colorTextoPrincipal, fuenteBotones);

        // Elementos de Colisiones
        JLabel lblTituloColisiones = new JLabel("Collisions", SwingConstants.CENTER);
        lblTituloColisiones.setFont(fuenteTitulos);
        lblTituloColisiones.setForeground(colorTextoPrincipal);
        
        lblNumColisiones = new JLabel("0", SwingConstants.CENTER);
        lblNumColisiones.setOpaque(true);
        lblNumColisiones.setBackground(colorPaneles);
        lblNumColisiones.setForeground(new java.awt.Color(255, 85, 85)); 
        lblNumColisiones.setBorder(BorderFactory.createLineBorder(colorAcento, 2));
        lblNumColisiones.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 36)); // Número enorme
        
        // Área central (La "T")
        JLabel lblT = new JLabel("Hash Table Structure (T)", SwingConstants.CENTER);
        lblT.setFont(fuenteTitulos);
        lblT.setForeground(colorTextoPrincipal);
        
        txtAreaTabla = new JTextArea();
        txtAreaTabla.setEditable(false);
        txtAreaTabla.setBackground(new java.awt.Color(20, 20, 25)); 
        txtAreaTabla.setForeground(new java.awt.Color(152, 251, 152)); 
        txtAreaTabla.setFont(fuenteConsola);
        
        JScrollPane scrollPane = new JScrollPane(txtAreaTabla);
        scrollPane.setBorder(BorderFactory.createLineBorder(colorAcento));
        
        // Etiqueta de ruta abajo
        lblRutaArchivo = new JLabel(" Ruta del archivo CSV aparecerá aquí...");
        lblRutaArchivo.setOpaque(true);
        lblRutaArchivo.setBackground(colorPaneles);
        lblRutaArchivo.setForeground(colorTextoPrincipal);
        lblRutaArchivo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        lblRutaArchivo.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, colorAcento));

        // Radio buttons 
        radioDivision = new JRadioButton("División", true);
        estilizarRadioButton(radioDivision, colorFondo, colorTextoPrincipal);
        
        radioMultiplicacion = new JRadioButton("Multiplicación");
        estilizarRadioButton(radioMultiplicacion, colorFondo, colorTextoPrincipal);
        
        ButtonGroup grupoFunciones = new ButtonGroup();
        grupoFunciones.add(radioDivision);
        grupoFunciones.add(radioMultiplicacion);

        // Configuracion de ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        getContentPane().setBackground(colorFondo);
        setResizable(false);

        // (x, y, ancho, alto)        
        panelFile.setBounds(30, 30, 140, 190);
        panelOperations.setBounds(190, 30, 140, 190);
        getContentPane().add(panelFile);
        getContentPane().add(panelOperations);

        // Botones panel File
        btnOpen.setBounds(15, 35, 110, 40);
        btnSave.setBounds(15, 85, 110, 40);
        btnClose.setBounds(15, 135, 110, 40);
        panelFile.add(btnOpen);
        panelFile.add(btnSave);
        panelFile.add(btnClose);

        // Botones panel Operations
        btnInsert.setBounds(15, 35, 110, 40);
        btnDelete.setBounds(15, 85, 110, 40);
        btnSearch.setBounds(15, 135, 110, 40);
        panelOperations.add(btnInsert);
        panelOperations.add(btnDelete);
        panelOperations.add(btnSearch);

        // Radio Buttons 
        radioDivision.setBounds(370, 70, 120, 30);
        radioMultiplicacion.setBounds(370, 120, 150, 30);
        getContentPane().add(radioDivision);
        getContentPane().add(radioMultiplicacion);

        // Panel de Colisiones y Botón Info
        lblTituloColisiones.setBounds(560, 40, 120, 30);
        lblNumColisiones.setBounds(560, 75, 120, 70);
        btnInfo.setBounds(560, 160, 120, 40);
        getContentPane().add(lblTituloColisiones);
        getContentPane().add(lblNumColisiones);
        getContentPane().add(btnInfo);

        // Panel Central (La Tabla "T")
        lblT.setBounds(30, 240, 680, 30);
        getContentPane().add(lblT);
        
        scrollPane.setBounds(30, 275, 680, 240); 
        getContentPane().add(scrollPane);

        // Ruta abajo
        lblRutaArchivo.setBounds(0, 530, 760, 35);
        getContentPane().add(lblRutaArchivo);

        // Nuevo tamaño general de la ventana
        setSize(760, 600);
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
    public JButton getBtnInfo() { return btnInfo; }
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
    private javax.swing.JButton btnInfo;
    private javax.swing.JLabel lblNumColisiones;
    private javax.swing.JLabel lblRutaArchivo;
    private javax.swing.JRadioButton radioDivision;
    private javax.swing.JRadioButton radioMultiplicacion;
    private javax.swing.JTextArea txtAreaTabla;
}
