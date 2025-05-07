# Equipo18-TarjetaPerfil
## Proyecto: Panel Tarjeta de Usuario
---

##  Descripción del Proyecto
TarjetaPerfil es un componente Swing personalizado que permite mostrar información de perfil de usuario en un formato de tarjeta visualmente atractivo. El componente incluye una sección de cabecera con imagen de perfil circular y fondo personalizable, así como una sección de contenido para mostrar información estructurada como nombre, edad, fecha de nacimiento y descripción.

---

## Características principales
- **Diseño moderno :** Interfaz elegante con cabecera de color y sección de contenido.
- **Avatar personalizable :** Imagen de perfil circular que se puede cambiar con doble clic
- **Fondo personalizable :** Permite cambiar la imagen de fondo de la cabecera
- **Opciones contextuales :** Menú emergente para mostrar/ocultar elementos
- **Adaptable :** Tamaño fijo pero con contenido flexible.
---

## Métodos y propiedades relevantes

- Propiedades principales
```bash
// Dimensiones y colores predefinidos
private static final int PANEL_WIDTH = 400;
private static final int PANEL_HEIGHT = 520;
private static final int HEADER_HEIGHT = 180;
private static final int AVATAR_SIZE = 130;
private static final Color HEADER_BG_COLOR = new Color(0, 102, 102);
private static final Color CONTENT_BG_COLOR = new Color(245, 245, 245);
```

- Métodos para establecer datos
```bash
// Establece el nombre en la tarjeta
public void setNombre(String nombre) {
    txtNombre.setText(nombre != null ? nombre : "");
}

// Establece la edad en la tarjeta
public void setEdad(String edad) {
    txtEdad.setText(edad != null ? edad : "");
}

// Establece la fecha de nacimiento en la tarjeta
public void setFechaNacimiento(String fecha) {
    txtFechaN.setText(fecha != null ? fecha : "");
}

// Establece la descripción con formato HTML para permitir múltiples líneas
public void setDescripcion(String descripcion) {
    if (descripcion != null && !descripcion.isEmpty()) {
        txtDescripcion.setText("<html><body style='width: 280px'>" + descripcion + "</body></html>");
    } else {
        txtDescripcion.setText("");
    }
}

// Establece la imagen de perfil
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
```
- Método para cargar datos desde una lista
```bash
// Carga datos desde una lista de pares clave-valor
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
```
- Métodos para gestionar imagen y fondo
```bash
// Cambia la imagen de perfil mediante un selector de archivos
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

// Cambia el fondo mediante un selector de archivos
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
```
---

## Instrucciones de uso
### Integración en un proyecto
- Haz clic derecho en la Paleta de Componentes y selecciona Palette Manager
- Haz clic en Add from JAR, elije el componete.jar
- Selecciona la clase TarjetaPerfil
- Ahora puedes arrastrar el componente desde la paleta al formulario.

---

## Interacción con el componente
El componente ya incluye las siguientes interacciones:
- **Doble clic en la imagen de perfil :** Abre un diálogo para seleccionar una nueva imagen
![Image](https://github.com/CesarMiguel-GP/safafa/blob/main/1.png)
![Image](https://github.com/CesarMiguel-GP/safafa/blob/main/2.png)
- **Doble clic en el fondo :** Abre un diálogo para seleccionar una imagen de fondo
![Image](https://github.com/CesarMiguel-GP/safafa/blob/main/3.png)
![Image](https://github.com/CesarMiguel-GP/safafa/blob/main/4.png)
- **Haga clic derecho en la imagen :** Muestra un menú contextual con opciones para mostrar/ocultar o cambiar la imagen.
![Image](https://github.com/CesarMiguel-GP/safafa/blob/main/5.png)
---
## Enlace al video de demostración

   https://youtu.be/QrDjHdac6-M

---

##  Requisitos mínimos

- Java 8 o superior.
- IDE como NetBeans 25, IntelliJ o Eclipse.
- No se requiere conexión a internet ni librerías externas.

---

## Créditos:

Este proyecto fue desarrollado como parte de un ejercicio académico para la materia de programación, por el equipo 18, integrado por:

- Sandoval Reyes Miguel  
- García Pérez César Miguel
