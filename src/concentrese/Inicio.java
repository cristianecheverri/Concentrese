package concentrese;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.*;

/**
 * Este JFrame contiene el inicio del juego, muestra una interfaz con una imagen
 * logo, texto acerca de la funcionalidad del juego y botones de acción para
 * iniciar o cerrar
 *
 * @author Cristian Echeverri
 * Git: cristianecheverri 
 * Segundo proyecto Estructuras de datos 
 * Ingeniería en informática 
 * Tercer semestre 
 * Universidad de Caldas - Colombia
 * Docente: Carlos Cuesta Iglesias
 */
public class Inicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblLogo; //Contiene el logo del juego
    private JTextArea textoExplicacion; //Contiene la informacion acerca del juego
    private JButton btnIniciar, btnCerrar; //Botones de accion para iniciar o dejar el juego
    private JPanel panelTexto, panelBotones; //Paneles que contienen los componentes del frame

    public Inicio() {
        inicializarComponentes();
    }

    /**
     * Inicializa las variables y las ubica en los paneles que se agregarán al
     * frame
     */
    private void inicializarComponentes() {
        setTitle("¿En que consiste el juego?");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 700));
        pack();
        setVisible(true);

        URL url = this.getClass().getResource("/imagenes/logo.png"); //Contiene la ruta del logo
        ImageIcon icono = new ImageIcon(url); //Asigna la ruta a una variable que guardará la imagen

        lblLogo = new JLabel();
        lblLogo.setIcon(icono); //Agrega la imagen a la etiqueta lblLogo
        lblLogo.setVisible(true);

        this.add(lblLogo, BorderLayout.NORTH);//Añade la imagen al norte del Frame

        panelTexto = new JPanel();

        textoExplicacion = new JTextArea();
        textoExplicacion.setPreferredSize(new Dimension(475, 560));
        textoExplicacion.setEditable(false);
        textoExplicacion.setOpaque(false);

        JScrollPane scroll = new JScrollPane(textoExplicacion);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        textoExplicacion.setText("El juego consiste en un número par de fichas"
                + "\ncon imágenes de un lado y reverso genérico."
                + "\nCada imagen aparece precisamente en dos fichas."
                + "\n\nCuando empieza el juego todas las fichas están"
                + "\nvolteadas boca abajo."
                + "\n\nEntonces el jugador voltea dos fichas, seleccionándolas"
                + "\nal hacer clic sobre ellas. Si las dos fichas tienen la"
                + "\nmisma imagen, permanecen boca arriba. De lo contrario,"
                + "\nlas fichas se voltean boca abajo después de un pequeño"
                + "\nperiodo de tiempo."
                + "\n\nEl objetivo del juego es conseguir todas las fichas"
                + "\nvolteadas boca arriba (es decir, encontrar todos los"
                + "\npares de imagenes que coincidan) en el menor número"
                + "\nde intentos. Eso significa que el menor numero de"
                + "\nintentos es una mejor puntuacion.");

        textoExplicacion.setFont(new Font("Bookman Old Style", Font.BOLD, 16));
        panelTexto.add(scroll); //Añade al panelTexto el scroll que contiene el texto a mostrar

        this.add(panelTexto, BorderLayout.CENTER); //Añade al centro del Frame el panel que contiene el texto

        panelBotones = new JPanel();

        btnIniciar = new JButton("Iniciar el juego");
        btnIniciar.setBackground(Color.LIGHT_GRAY);
        btnIniciar.addActionListener((ActionEvent evento) -> {
            Concentrese concentrese = new Concentrese();// Crea un objeto de tipo Concentrese que llama al frame del juego
            dispose(); //Cierra la actual ventana sin afectar el funcionamiento
        });

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBackground(Color.LIGHT_GRAY);
        btnCerrar.addActionListener((ActionEvent evento) -> {
            System.exit(0);//Cierra la aplicacion
        });

        panelBotones.add(btnIniciar); //Añade al panelBotones el botón iniciar
        panelBotones.add(btnCerrar); //Añade al panelBotones el botón cerrar
        this.add(panelBotones, BorderLayout.SOUTH); //Añade el panel que contiene los botones al Frame
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Inicio().setVisible(true);
        });
    }
}
