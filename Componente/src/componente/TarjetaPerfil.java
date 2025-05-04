package componente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.List;

public class TarjetaPerfil extends JPanel {
    private static final int PANEL_WIDTH = 400;
    private static final int PANEL_HEIGHT = 520;
    private static final int HEADER_HEIGHT = 180;
    private static final int AVATAR_SIZE = 130;
    private static final Color HEADER_BG_COLOR = new Color(0, 102, 102);
    private static final Color CONTENT_BG_COLOR = new Color(245, 245, 245);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font CONTENT_FONT = new Font("Segoe UI", Font.PLAIN, 16);

    private final ImagenPanel imagenPanel;
    private final JPanel panelSuperior;
    private final JLabel txtNombre, txtEdad, txtFechaN;
    private final JLabel txtDescripcion;
    private final JMenuItem itemOcultarImagen;
    private final JMenuItem itemOcultarFondo;

    private ImageIcon imagenPerfil = null;
    private ImageIcon fondoPersonalizado = null;
    private boolean imagenVisible = true;
    private boolean fondoVisible = true;
    private String rutaImagenPerfil = null;
    private String rutaFondo = null;

    public TarjetaPerfil() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setMaximumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        panelSuperior = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fondoPersonalizado != null && fondoVisible) {
                    g.drawImage(fondoPersonalizado.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(HEADER_BG_COLOR);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelSuperior.setPreferredSize(new Dimension(PANEL_WIDTH, HEADER_HEIGHT));
        panelSuperior.setBackground(HEADER_BG_COLOR);

        imagenPanel = new ImagenPanel();
        imagenPanel.setOpaque(false);
        int centerX = (PANEL_WIDTH - AVATAR_SIZE) / 2;
        imagenPanel.setBounds(centerX, 25, AVATAR_SIZE, AVATAR_SIZE);
        panelSuperior.add(imagenPanel);
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelContenido = crearPanelDatos();
        add(panelContenido, BorderLayout.CENTER);

        txtNombre = (JLabel) findComponentByName(panelContenido, "txtNombre");
        txtEdad = (JLabel) findComponentByName(panelContenido, "txtEdad");
        txtFechaN = (JLabel) findComponentByName(panelContenido, "txtFechaN");
        txtDescripcion = (JLabel) findComponentByName(panelContenido, "txtDescripcion");

        JPopupMenu menuContextual = crearMenuContextualImagen();
        itemOcultarImagen = (JMenuItem) findComponentByName(menuContextual, "itemOcultarImagen");
        itemOcultarFondo = (JMenuItem) findComponentByName(menuContextual, "itemOcultarFondo");

        // Menú contextual para el panel de imagen
        JPopupMenu menuImagen = crearMenuContextualImagen();
        
        // Listener específico para el panel de imagen
        MouseAdapter imagenMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { 
                mostrarMenuSiNecesario(e, menuImagen); 
            }

            @Override
            public void mouseReleased(MouseEvent e) { 
                mostrarMenuSiNecesario(e, menuImagen); 
            }

            private void mostrarMenuSiNecesario(MouseEvent e, JPopupMenu menu) {
                if (e.isPopupTrigger()) {
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    cambiarImagenPerfil();
                }
            }
        };
        imagenPanel.addMouseListener(imagenMouseAdapter);

        // Doble clic sobre el fondo
        panelSuperior.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !imagenPanel.getBounds().contains(e.getPoint())) {
                    cambiarFondo();
                }
            }
        });
    }

    private JPanel crearPanelDatos() {
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBackground(CONTENT_BG_COLOR);

        panelDatos.add(crearCampo("Nombre", "txtNombre"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearCampo("Edad", "txtEdad"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearCampo("Fecha de nacimiento", "txtFechaN"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel panelDesc = new JPanel();
        panelDesc.setLayout(new BoxLayout(panelDesc, BoxLayout.Y_AXIS));
        panelDesc.setOpaque(false);
        panelDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblDesc = new JLabel("Descripción");
        lblDesc.setFont(LABEL_FONT);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel txtDescripcion = new JLabel();
        txtDescripcion.setName("txtDescripcion");
        txtDescripcion.setFont(CONTENT_FONT);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setVerticalAlignment(SwingConstants.TOP);
        txtDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtDescripcion.setBorder(null);

        panelDesc.add(lblDesc);
        panelDesc.add(Box.createRigidArea(new Dimension(0, 5)));
        panelDesc.add(txtDescripcion);
        panelDatos.add(panelDesc);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(CONTENT_BG_COLOR);
        wrapperPanel.add(panelDatos, BorderLayout.NORTH);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        return wrapperPanel;
    }

    private JPanel crearCampo(String etiqueta, String nombreComponente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTitulo = new JLabel(etiqueta);
        lblTitulo.setFont(LABEL_FONT);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblValor = new JLabel("");
        lblValor.setName(nombreComponente);
        lblValor.setFont(CONTENT_FONT);
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(lblValor);

        return panel;
    }
    
    private JPopupMenu crearMenuContextualImagen() {
        JPopupMenu menu = new JPopupMenu();
        
        JMenuItem itemOcultarImagen = crearItemMenu("Ocultar imagen perfil", e -> toggleImagenPerfil());
        itemOcultarImagen.setName("itemOcultarImagen");
        menu.add(itemOcultarImagen);
        
        JMenuItem itemCambiarImagen = crearItemMenu("Cambiar imagen perfil", e -> cambiarImagenPerfil());
        menu.add(itemCambiarImagen);
        
        return menu;
    }

    private JMenuItem crearItemMenu(String texto, ActionListener listener) {
        JMenuItem item = new JMenuItem(texto);
        item.addActionListener(listener);
        return item;
    }

    

    private void cambiarImagenPerfil() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            ImageIcon icono = new ImageIcon(selectedFile.getAbsolutePath());
            rutaImagenPerfil = selectedFile.getAbsolutePath();
            setImagen(icono);
            setPerfilVisible(true);
        }
    }

    private void cambiarFondo() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "gif"));
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            fondoPersonalizado = new ImageIcon(selectedFile.getAbsolutePath());
            rutaFondo = selectedFile.getAbsolutePath();
            setFondoVisible(true);
            panelSuperior.repaint();
        }
    }

    private void toggleImagenPerfil() {
        setPerfilVisible(!imagenVisible);
    }
    
    private void toggleFondoVisible() {
        setFondoVisible(!fondoVisible);
    }
    
    public void setPerfilVisible(boolean visible) {
        this.imagenVisible = visible;
        
        if (visible) {
            if (imagenPerfil != null) {
                imagenPanel.setImagen(imagenPerfil);
                if (itemOcultarImagen != null) {
                    itemOcultarImagen.setText("Ocultar imagen perfil");
                }
            }
        } else {
            imagenPanel.ocultarImagen();
            if (itemOcultarImagen != null) {
                itemOcultarImagen.setText("Mostrar imagen perfil");
            }
        }
    }
    
    public void setFondoVisible(boolean visible) {
        this.fondoVisible = visible;
        
        if (itemOcultarFondo != null) {
            itemOcultarFondo.setText(visible ? "Ocultar fondo" : "Mostrar fondo");
        }
        
        panelSuperior.repaint();
    }
    
    public boolean isPerfilVisible() {
        return imagenVisible;
    }
    
    public boolean isFondoVisible() {
        return fondoVisible;
    }

    private Component findComponentByName(Container container, String name) {
        for (Component component : container.getComponents()) {
            if (name.equals(component.getName())) {
                return component;
            } else if (component instanceof Container) {
                Component found = findComponentByName((Container) component, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public void setNombre(String nombre) {
        txtNombre.setText(nombre != null ? nombre : "");
    }

    public void setEdad(String edad) {
        txtEdad.setText(edad != null ? edad : "");
    }

    public void setFechaNacimiento(String fecha) {
        txtFechaN.setText(fecha != null ? fecha : "");
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.isEmpty()) {
            txtDescripcion.setText("<html><body style='width: 280px'>" + descripcion + "</body></html>");
        } else {
            txtDescripcion.setText("");
        }
    }

    public void setImagen(ImageIcon imagen) {
        this.imagenPerfil = imagen;
        if (imagen != null) {
            imagenPanel.setImagen(imagen);
            rutaImagenPerfil = imagen.toString();
            imagenVisible = true;
            if (itemOcultarImagen != null) {
                itemOcultarImagen.setText("Ocultar imagen perfil");
            }
        }
    }

    public void cargarDesdeLista(List<String[]> datos) {
        txtNombre.setText("");
        txtEdad.setText("");
        txtFechaN.setText("");
        txtDescripcion.setText("");

        if (datos == null || datos.isEmpty()) return;

        for (String[] par : datos) {
            if (par == null || par.length < 2 || par[0] == null || par[1] == null) continue;
            String clave = par[0].toLowerCase().trim();
            String valor = par[1].trim();

            switch (clave) {
                case "nombre" -> setNombre(valor);
                case "edad" -> setEdad(valor);
                case "fecha" -> setFechaNacimiento(valor);
                case "descripción", "descripcion" -> setDescripcion(valor);
            }
        }
    }

    private static class ImagenPanel extends JPanel {
        private Image imagen;

        public void setImagen(ImageIcon icono) {
            if (icono != null) {
                this.imagen = icono.getImage();
                repaint();
            }
        }

        public void ocultarImagen() {
            this.imagen = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int size = Math.min(getWidth(), getHeight());
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 80));
            g2.fillOval(x + 4, y + 4, size - 10, size - 10);

            if (imagen == null) {
                g2.setColor(Color.GRAY);
                g2.fillOval(x, y, size - 10, size - 10);
            } else {
                g2.setClip(new Ellipse2D.Float(x, y, size - 10, size - 10));
                g2.drawImage(imagen, x, y, size - 10, size - 10, this);
            }

            g2.dispose();
        }
    }
}
