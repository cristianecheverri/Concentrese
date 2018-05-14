package concentrese;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase controla la etiqueta que muestra el tiempo transcurrido desde que
 * se inició el juego
 *
 * @author Cristian Echeverri
 *
 * Git: cristianecheverri 
 * Segundo proyecto Estructuras de datos 
 * Ingeniería en informática 
 * Tercer semestre 
 * Universidad de Caldas - Colombia 
 * Docente: Carlos Cuesta Iglesias
 */
public class Tiempo {

    private static int h; 
    private static int m;
    private static int s;
    private static int cs;
    
    /**
     * Controla el tiempo que se mestra en pantalla
     */
    protected final static ActionListener acciones = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            ++cs;
            if (cs == 100) {
                cs = 0;
                ++s;
            }
            if (s == 60) {
                s = 0;
                ++m;
            }
            if (m == 60) {
                m = 0;
                ++h;
            }
            actualizarLabelTiempo();
        }
    };

    /**
     * Actualiza la etiqueta que muestra el tiempo de ejecución
     */
    private static void actualizarLabelTiempo() {

        String tiempo = (h <= 9 ? "0" : "") + h + ":" + (m <= 9 ? "0" : "") + m + ":" + (s <= 9 ? "0" : "") + s + ":"
                + (cs <= 9 ? "0" : "") + cs;
        Concentrese.lblTiempo.setText(tiempo);
    }
}
