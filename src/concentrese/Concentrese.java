package concentrese;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

/**
 * Interfaz y parte logica para el juego concentrese
 *
 * @author Cristian Echeverri 
 * Git: cristianecheverri 
 * Segundo proyecto Estructuras de datos 
 * Ingeniería en informática 
 * Tercer semestre 
 * Universidad de Caldas - Colombia
 * Docente: Carlos Cuesta Iglesias
 *
 */
public class Concentrese extends JFrame {

    private static final long serialVersionUID = 1L;
    private final JLabel[][] casillasJuego = new JLabel[6][6]; // Contiene los pares de imagenes en estado aleatorio
    private final JLabel[][] casillasJuego2 = new JLabel[6][6]; // contiene la imagen generica que tapa el resto de pares
    private int[][] posicion;
    private Icon imgs[];
    private Icon imagGenerica;
    private int[] random;
    private int clic11, clic12, clic21, clic22;
    private int puntaje = 500, contadorParejas = 0, intentos = 0;
    private JPanel panel, panelbtn; // Paneles
    public static JLabel lblTiempo; // Muestra el hilo de tiempo que se lleva recorrido
    protected static JLabel lblPuntaje;
    private static JLabel lblLogo;
    private static JLabel lblBrain;
    private JButton btnNuevoJuego; // Reinicia el juego si se pulsa
    private Timer tempo;
    //private int h, m, s, cs;  // crear una clase tiempo y utilizar un objeto de dicha en vez de utilizar variables  separadas

    public Concentrese() {
        inicializarComponentes();
    }

    /**
     * Inicializa los componentes y los paneles del juego
     */
    private void inicializarComponentes() {
        setTitle("Concentrese");
        this.setLayout(null);

        this.add(getPanelPrincipal());
        this.add(getPanelBotones());
        this.add(getLblLogo());
        this.add(getLblBrain());

        tempo = new Timer(10, Tiempo.acciones);

        inicializarCasillas(); // Se llama al metodo que inicializa los vectores de etiquetas
        anadirCasillas(); // Añade al panel las imagenes recien inicializadas.

        random = new int[36]; // Almacena de manera aleatoria los nombres de los pares de imágenes
        posicion = new int[6][6]; //Guarda las posociones de las imagenes

        imgs = new Icon[36];// Vector de iconos que almacena los pares de imagenes
        imagGenerica = new ImageIcon(getClass().getResource("/imagenes/f.jpg")); // Contiene la imagen que oculta los
        // pares de imagenes

        jugar();// Llama al metodo que ordena de forma aleatoria los pares de imagenes y los
        // asigna a el vector imgs

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1900, 870));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
    }

    /**
     * Inicializa el panel que contiene las casillas con las imágenes
     *
     * @return Panel con el juego
     */
    private JPanel getPanelPrincipal() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(50, 5, 830, 830);
        panel.setBackground(Color.black);
        return panel;
    }

    /**
     * Inicializa el panel que contiene el botón de reiniciar juego, la etiqueta
     * con el puntaje y la etiqueta con el tiempo transcurrido
     *
     * @return panel con el botón y etiquetas
     */
    private JPanel getPanelBotones() {
        panelbtn = new JPanel();
        panelbtn.setLayout(null);
        panelbtn.setBounds(940, 10, 600, 55);
        panelbtn.setBackground(new Color(120, 31, 25));
        panelbtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        lblPuntaje = new JLabel("Puntaje: " + puntaje);
        lblPuntaje.setBounds(10, 10, 230, 30);
        lblPuntaje.setFont(new Font("Tahoma", 1, 25));
        lblPuntaje.setForeground(Color.WHITE);
        panelbtn.add(lblPuntaje);

        btnNuevoJuego = new JButton("REINICIAR JUEGO");
        btnNuevoJuego.setBounds(420, 10, 150, 30);
        btnNuevoJuego.setBackground(Color.LIGHT_GRAY);
        btnNuevoJuego.addActionListener((ActionEvent evento) -> {
            reiniciar(); // Llamado al metodo que inicia de nuevo el juego con valores aleatorios nuevos
        });
        panelbtn.add(btnNuevoJuego);

        lblTiempo = new JLabel("0:00");
        lblTiempo.setFont(new Font("Tahoma", 1, 25));
        lblTiempo.setBounds(210, 10, 200, 30);
        lblTiempo.setForeground(Color.WHITE);
        panelbtn.add(lblTiempo);

        return panelbtn;
    }

    /**
     * Inicializa la etiqueta que contiene el logo del juego
     *
     * @return La etiqueta con el logo del juego
     */
    private JLabel getLblLogo() {
        URL urlLogo = this.getClass().getResource("/imagenes/logo.png");
        ImageIcon iconoLogo = new ImageIcon(urlLogo);

        lblLogo = new JLabel();
        lblLogo.setBounds(1000, 40, 500, 300);
        lblLogo.setIcon(iconoLogo);
        lblLogo.setVisible(true);
        return lblLogo;
    }

    /**
     * Inicializa la etiqueta que contiene la imagen del cerebro
     *
     * @return etiqueta con la imagen del cerebro
     */
    private JLabel getLblBrain() {
        URL urlBrain = this.getClass().getResource("/imagenes/brain.png");
        ImageIcon imagen = new ImageIcon(urlBrain);

        lblBrain = new JLabel();
        lblBrain.setBounds(1000, 330, 500, 485);
        lblBrain.setIcon(imagen);
        lblBrain.setVisible(true);

        return lblBrain;
    }

    /**
     * Inicializa los arrays de etiquetas que contendrán los pares de imagenes y
     * las imagenes genéricas.
     */
    public void inicializarCasillas() {
        int contador = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                casillasJuego[i][j] = new JLabel("Casilla #" + contador);
                casillasJuego2[i][j] = new JLabel();
                contador++;

                eventoClic(i, j); // agrega a cada etiqueta el evento cuando es pulsada
            }
        }
    }

    /**
     * Añade al panel cada una de las casillas o JLabels que contendrán las
     * imagenes
     */
    public void anadirCasillas() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                //Unica las casillas en el panel, las agrega y les asigna un tamaño de 125*125
                casillasJuego[i][j].setBounds(j * 135 + 17, i * 135 + 17, 125, 125);
                casillasJuego2[i][j].setBounds(j * 135 + 17, i * 135 + 17, 125, 125);

                //panel.add(casillasJuego[i][j]);
                panel.add(casillasJuego2[i][j]);
                panel.add(casillasJuego[i][j]);
            }
        }
    }

    /**
     * LLena un vector con numeros aleatorios entre 1 y 36
     */
    public void random() {

        int position = 0; //Controla las posiciones del vector
        while (position < 36) {

            //Math.random() devuelve un numero decimal mayor o igual a 0.0 y menor a 1.0
            int num = (int) (Math.random() * (36) + 1);

            int cont = 0;
            for (int pos = 0; pos < 36; pos++) {
                //Verifica si el numero que se generó aleatoriamente ya existe en el vector
                if (random[pos] != num) {
                    cont++;
                }
            }

            if (cont == 36) { //Si el numero generado no está en el vector, asignelo a una posición
                random[position] = num;
                position++;
            }
        }
    }

    /**
     * Llena el vector de imagenes las imagenes ordenadas aleatoriamente
     * Almacena los nombres de las imegenes en el orden que están y añade a las
     * etiquetas las imágenes que van a contener
     */
    public void asignarImagenes() {
        int p = 0;
        for (int i = 0; i < 36; i++) {
            if (random[p] > 18) { //Si el numero en esa posicion es mayor a 18...
                random[p] = random[p] - 18; //restar 18 al numero.
            }
            // agrega al vector de imagenes las imagenes en orden aleatorio
            imgs[i] = new ImageIcon(getClass().getResource("/imagenes/s" + random[p] + ".jpg"));
            p++;
        }

        int posicio = 0;
        int contImagen = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                posicion[i][j] = random[posicio]; // Almacena los nombres de las imagenes para posteriormente comparar
                System.out.print(posicion[i][j] + "\t"); // Imprime las posiciones de las imagenes

                casillasJuego[i][j].setIcon(imgs[contImagen]);// Añade a cada etiqueta una imagen del vector de imagenes
                casillasJuego2[i][j].setIcon(imagGenerica); // Añade a cada etiqueta la imagen genérica
                contImagen++;
                posicio++;
            }
            System.out.println();
        }
        System.out.println("------------------------------------------");
    }

    /**
     *
     * @return true si se encontraron todas las imagenes o false si no
     */
    private boolean ganar() {
        boolean ganaste = true;

        for (JLabel[] vectorGenerica : casillasJuego2) {
            for (JLabel casillaGenerica : vectorGenerica) {
                if (!casillaGenerica.isVisible()) {
                    ganaste = true;
                } else {
                    return false;
                }
            }
        }
        return ganaste;
    }

    /**
     * Muestra cuadros de dialogo que contienen la información de cómo se
     * finalizó el juego
     */
    private void ganaste() {
        JOptionPane.showMessageDialog(null,
                "¡FELICITACIONES!\n\n" + "Intentos : " + intentos + "\nTiempo: " + lblTiempo.getText() + "\nPuntaje: " + puntaje,
                "¡GANASTE!", JOptionPane.INFORMATION_MESSAGE);

        int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas intentarlo de nuevo?", "¿Otra vez?",
                JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            reiniciar(); // Reinicia el juego y cambia las ubicaciones de las imagenes aleatoriamente
        } else {
            System.exit(0); // Cierra la aplicación
        }
    }

    /**
     * Inicializa todos los valores
     */
    public void jugar() {
        random();
        asignarImagenes();
        tempo.start(); // Pone a correr el hilo que controla la etiqueta del tiempo
    }

    /**
     * Inicia de nuevo el juego sin tener que ejecutarlo de nuevo
     */
    private void reiniciar() {
        setVisible(false);
        tempo.stop();
        tempo = null;
        dispose();

        Concentrese concentrese = new Concentrese(); // Crea un objeto de su misma clase
        concentrese.setVisible(true); // Inicia los componentes
    }

    /**
     * Añade a cada etiqueta el evento cuando es pulsada
     *
     * @param i fila
     * @param j columna
     */
    private void eventoClic(int i, int j) {
        int filas = i;
        int cols = j;
        casillasJuego2[i][j].addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clic21 = filas;
                clic22 = cols;
                casillasJuego2[clic21][clic22].setVisible(false);// Vuelve invisibles las etiquetas de imagen generica
                // seleccionadas
                contadorParejas++;

                panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(java.awt.event.MouseEvent evt) {

                        if (contadorParejas == 2) {
                            intentos++;
                            // si la imagen en la posicion... es igual a la que está en la posicion...
                            if (posicion[clic11][clic12] == posicion[clic21][clic22]) {
                                // Hace invisibles las etiquetas de la imagen generica seleccionada
                                casillasJuego2[clic11][clic12].setVisible(false);
                                casillasJuego2[clic21][clic22].setVisible(false);
                                contadorParejas = 0;
                                puntaje += 10; // Incrementa en 10 el puntaje
                                lblPuntaje.setText("Puntaje: " + puntaje);
                                if (ganar()) {// Si todas las imagenes estan visibles...
                                    tempo.stop(); //Detiene el hilo que controla el tiempo
                                    ganaste(); // Muestra los cuadros de dialogo que contienen los datos del juego
                                }
                            } else {// Si las imagenes no coinciden...
                                // deja visible las imagenes genericas para tapar las imagenes
                                casillasJuego2[clic21][clic22].setVisible(true);
                                casillasJuego2[clic11][clic12].setVisible(true);
                                contadorParejas = 0;
                                puntaje -= 10; // decrementa el puntaje en 10
                                lblPuntaje.setText("Puntaje: " + puntaje);
                            }
                            contadorParejas = 0;
                        } else {
                            clic11 = filas;
                            clic12 = cols;
                        }
                    }
                });
            }
        });
    }
}
