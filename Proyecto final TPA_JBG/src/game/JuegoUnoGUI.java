/**

 Package game contiene las clases principales que gestionan la lógica del juego UNO.
 */
package game;

import baraja.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**

 Clase principal para ejecutar el juego UNO.

 Método principal que contiene la lógica del flujo del juego, incluyendo
 la creación de jugadores, la gestión de rondas y turnos, y la finalización del juego.
 */
public class JuegoUnoGUI extends JFrame {
    private JuegoUNO juego;
    private JPanel panelCentro;
    private JPanel panelJugador;
    private JLabel lblCartaCentro;
    private JLabel lblColorCarta;
    private JButton btnRobar;
    private JButton btnHistorial;
    private JTextArea historial;

    public JuegoUnoGUI(Jugador[] jugadores) {
        juego = new JuegoUNO(jugadores);

        setTitle("Juego UNO");
        setSize(1200, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel centro
        panelCentro = new JPanel();
        lblCartaCentro = new JLabel();
        panelCentro.add(lblCartaCentro);
        lblColorCarta = new JLabel();
        panelCentro.add(lblColorCarta);
        add(panelCentro, BorderLayout.CENTER);

        // Panel jugador
        panelJugador = new JPanel();
        panelJugador.setLayout(new FlowLayout());
        add(panelJugador, BorderLayout.SOUTH);

        // Historial de acciones
        historial = new JTextArea(10, 30);
        historial.setEditable(false);

        // Botones
        btnRobar = new JButton("Robar Carta");
        btnHistorial = new JButton("Ver Historial");
        panelJugador.add(btnRobar);
        panelJugador.add(btnHistorial);

        btnRobar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarHistorial(juego.jugadorActual().getNombre().toUpperCase() + " robó una carta.");
                juego.robarCarta();
                actualizarPanelJugador();
                juego.cambioTurno();
                actualizarCartaCentro();
                actualizarPanelJugador();
            }
        });



        btnHistorial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarHistorial();
            }
        });

        actualizarCartaCentro();
        actualizarPanelJugador();
    }

    private void actualizarCartaCentro() {
        lblCartaCentro.setText("Carta en el centro: " + juego.baraja.getUltimaCarta().toString() + " - ");
        lblColorCarta.setText("Color carta actual : " + juego.baraja.getColorActual());
    }

    private void actualizarPanelJugador() {
        panelJugador.removeAll();
        Jugador jugador = juego.jugadorActual();
        panelJugador.add(new JLabel("Es el turno de: " + jugador.getNombre()));
        panelJugador.add(Box.createRigidArea(new Dimension(40, 0)));

        for (int i = 0; i < jugador.getMano().size(); i++) {
            JButton btnCarta = new JButton(jugador.getCartaAt(i).toString());
            final int index = i;
            btnCarta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Carta aux = jugador.getCartaAt(index);
                    Jugador jugadorActual = jugador;
                    if (juego.cartaCompatible(index)) {
                        actualizarHistorial(jugadorActual.getNombre().toUpperCase() + " jugó " + aux.toString());
                        if (juego.finJuego(jugador)){
                            actualizarHistorial(jugadorActual.getNombre().toUpperCase() + " ganó la partida!");
                            btnHistorial.doClick();
                            panelJugador.setVisible(false);
                        }
                        juego.cambioTurno();
                        actualizarCartaCentro();
                        actualizarPanelJugador();
                    } else {
                        JOptionPane.showMessageDialog(null, "No puedes echar esa carta");
                    }
                }
            });
            panelJugador.add(btnCarta);
        }
        panelJugador.add(Box.createRigidArea(new Dimension(40, 0)));
        panelJugador.add(btnRobar);
        panelJugador.add(btnHistorial);
        panelJugador.revalidate();
        panelJugador.repaint();
    }

    private void actualizarHistorial(String accion) {
        historial.append(accion + "\n");
    }

    private void mostrarHistorial() {
        JOptionPane.showMessageDialog(this, new JScrollPane(historial), "Historial de acciones", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        Jugador[] jugadores = {
                new Jugador("Jugador 1"),
                new Jugador("Jugador 2"),
                new Jugador("Jugador 3"),
                new Jugador("Jugador 4")
        };
        JuegoUnoGUI gui = new JuegoUnoGUI(jugadores);
        gui.setVisible(true);
    }
}
