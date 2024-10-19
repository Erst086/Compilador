package fes.aragon.compilador;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Juego extends JPanel {
    //declaracion de variables
    private int gridSize = 50;
    private int rows = 10;
    private int cols = 10;
    private int bichoRow = 0;
    private int bichoCol = 0;
    private int initialBichoRow = 0;
    private int initialBichoCol = 0;
    private int HKRow;
    private int HKCol;
    private boolean HKExiste;
    private ArrayList<String> comandos = new ArrayList<>();
    private int indice = 0;
    private boolean iniciar = false;
    private Timer timer;
    private Timer rotationTimer;
    private int rotationIndex = 0;
    private JPanel infoPanel;
    private JLabel puntuacionLabel;
    private JLabel completadoLabel;
    private boolean[][] obstaculos;

    // Imágenes
    private Image bichoArribaImagen;
    private Image bichoAbajoImagen;
    private Image bichoIzquierdaImagen;
    private Image bichoDerechaImagen;
    private Image imagenPorDefecto;
    private Image HKImagen;
    private Image fondoImagen;
    private Image obstaculoImagen;

    // Música
    private Clip musicaFondo;
    private Clip sonidoMeta;
    private Clip audioinicio;
    // Puntuación
    private int puntuacion = 0;
    private int completado = 0;

    public Juego() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        HKExiste = false;
        generarObstaculos();
        generarCaballerito();
        cargaImagenes();
        cargaSonido();

        // Crear y configurar el panel de información
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        puntuacionLabel = new JLabel("Puntuación: 0");
        completadoLabel = new JLabel("Completado: 0");
        infoPanel.add(puntuacionLabel);
        infoPanel.add(completadoLabel);
        infoPanel.setBackground(Color.LIGHT_GRAY);
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoPanel.setMaximumSize(new Dimension(50, 50)); // Ajustar tamaño

        // Crear un panel contenedor para centrar el panel de información arriba
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(infoPanel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_C: // Leer archivo fuente.txt
                        try {
                            leerArchivo();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case KeyEvent.VK_E: // Iniciar ejecución de comandos
                        inicioaudio();
                        iniciarAnimacionRotacion();
                        break;
                }
            }
        });
        setFocusable(true);
    }

    private void generarObstaculos() {
        obstaculos = new boolean[rows][cols];
        int numObstaculos = (rows * cols) / 5;
        for (int i = 0; i < numObstaculos; i++) {
            int row;
            int col;
            do {
                row = (int) (Math.random() * rows);
                col = (int) (Math.random() * cols);
            } while (obstaculos[row][col] || (row == bichoRow && col == bichoCol) || (row == HKRow && col == HKCol));
            obstaculos[row][col] = true;
        }
    }

    private void cargaSonido() {
        try {
            musicaFondo = AudioSystem.getClip();
            sonidoMeta = AudioSystem.getClip();
            audioinicio = AudioSystem.getClip();

            AudioInputStream backgroundStream = AudioSystem.getAudioInputStream(new File("src/fes/aragon/compilador/musica/fondo.wav"));
            AudioInputStream goalStream = AudioSystem.getAudioInputStream(new File("src/fes/aragon/compilador/musica/meta.wav"));
            AudioInputStream audioiniciarStream = AudioSystem.getAudioInputStream(new File("src/fes/aragon/compilador/musica/inicio.wav"));

            musicaFondo.open(backgroundStream);
            sonidoMeta.open(goalStream);
            audioinicio.open(audioiniciarStream);

            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); // Ciclar música de fondo
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void metaAudio() {
        if (sonidoMeta != null) {
            sonidoMeta.setFramePosition(0);
            sonidoMeta.start();
        }
    }

    private void inicioaudio() {
        if (audioinicio != null) {
            audioinicio.setFramePosition(0); // Rewind to the beginning
            audioinicio.start();
        }
    }

    private void cargaImagenes() {
        try {
            bichoArribaImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Bicho_Arriba.png")); 
            bichoAbajoImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Bicho_Abajo.png"));
            bichoIzquierdaImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Bicho_Izquierda.png"));
            bichoDerechaImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Bicho_Derecha.png"));
            imagenPorDefecto = bichoDerechaImagen; // Imagen inicial del avión
            HKImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/HK_Objetivo.png"));
            fondoImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Fondo.png"));
            obstaculoImagen = ImageIO.read(new File(System.getProperty("user.dir") + "/src/fes/aragon/compilador/imagenes/Obstaculo.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int totalGridWidth = cols * gridSize;
        int totalGridHeight = rows * gridSize;
        int xOffset = (panelWidth - totalGridWidth) / 2;
        int yOffset = (panelHeight - totalGridHeight) / 2;

        // Dibujar fondo
        if (fondoImagen != null) {
            g.drawImage(fondoImagen, 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujar la cuadrícula
        g.setColor(Color.BLUE);
        for (int i = 0; i <= rows; i++) {
            g.drawLine(xOffset, yOffset + i * gridSize, xOffset + totalGridWidth, yOffset + i * gridSize);
        }
        for (int i = 0; i <= cols; i++) {
            g.drawLine(xOffset + i * gridSize, yOffset, xOffset + i * gridSize, yOffset + totalGridHeight);
        }

        g.drawRect(xOffset, yOffset, totalGridWidth, totalGridHeight);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (obstaculos[row][col]) {
                    if (obstaculoImagen != null) {
                        g.drawImage(obstaculoImagen, xOffset + col * gridSize, yOffset + row * gridSize, gridSize, gridSize, this);
                    }
                    else {
                        g.setColor(Color.GRAY);
                        g.fillRect(xOffset + col * gridSize, yOffset + row * gridSize, gridSize, gridSize);
                    }
                }
            }
        }

        // Dibujar la manzana
        if (HKExiste && HKImagen != null) {
            g.drawImage(HKImagen, xOffset + HKCol * gridSize, yOffset + HKRow * gridSize, gridSize, gridSize, this);
        }
        else {
            g.setColor(Color.GREEN);
            g.fillOval(xOffset + HKCol * gridSize, yOffset + HKRow * gridSize, gridSize, gridSize);
        }

        // Dibujar el avión
        if (imagenPorDefecto != null) {
            g.drawImage(imagenPorDefecto, xOffset + bichoCol * gridSize, yOffset + bichoRow * gridSize, gridSize, gridSize, this);
        }
        else {
            g.setColor(Color.RED);
            g.fillRect(xOffset + bichoCol * gridSize, yOffset + bichoRow * gridSize, gridSize, gridSize);
        }
    }

    private void generarCaballerito() {
        do {
            HKRow = (int) (Math.random() * rows);
            HKCol = (int) (Math.random() * cols);
        }
        while (obstaculos[HKRow][HKCol]); // Asegurarse de que la manzana no esté en un obstáculo
        HKExiste = true;
    }

    private void leerArchivo() throws IOException {
        comandos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("fuente.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                comandos.add(linea.trim());
            }
        }
    }

    private void iniciar() {
        bichoRow = initialBichoRow;
        bichoCol = initialBichoCol;
        indice = 0;
        iniciar = true;
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(500, e -> ejecutar());
        timer.start();
    }

    private void ejecutar() {
        if (iniciar && indice < comandos.size()) {
            String comando = comandos.get(indice);
            String[] partes = comando.split(" ");
            if (partes.length >= 1) {
                String instruccion = partes[0].toLowerCase();
                switch (instruccion) {
                    case "mover":
                        if (partes.length == 3) {
                            String direccion = partes[1];
                            int cantidad = Integer.parseInt(partes[2]);
                            moveNaveGradualmente(direccion.toLowerCase(), cantidad);
                        } else {
                            System.out.println("Formato de comando incorrecto: " + comando);
                        }
                        break;
                    case "inicio();":
                        initialBichoRow = bichoRow;
                        initialBichoCol = bichoCol;
                        break;
                    case "fin();":
                        initialBichoRow = bichoRow;
                        initialBichoCol = bichoCol;
                        iniciar = false;
                        timer.stop();
                        return;
                    default:
                        System.out.println("Instrucción no reconocida: " + instruccion);
                }
                indice++;
                verificarCaballerito();
            } else {
                System.out.println("Formato de comando incorrecto: " + comando);
                indice++;
            }
        } else {
            timer.stop();
        }
    }

    private void moveNaveGradualmente(String direccion, int cantidad) {
        int stepDelay = 100; // Retardo entre pasos en milisegundos
        int stepCount = cantidad; // Número de pasos basado en la cantidad
        Timer moveTimer = new Timer(stepDelay, null);

        ActionListener moveListener = new ActionListener() {
            int stepsTaken = 0;
            int targetRow = bichoRow;
            int targetCol = bichoCol;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepsTaken < stepCount) {
                    switch (direccion) {
                        case "arriba":
                            if (bichoRow > 0 && !obstaculos[bichoRow - 1][bichoCol]) {
                                bichoRow--;
                                verificarCaballerito();
                            }
                            else {
                                mostrarAdvertenciaYReiniciar();
                                return;
                            }
                            imagenPorDefecto = bichoArribaImagen;
                            break;
                        case "abajo":
                            if (bichoRow < rows - 1 && !obstaculos[bichoRow + 1][bichoCol]) {
                                bichoRow++;
                                verificarCaballerito();
                            } else {
                                mostrarAdvertenciaYReiniciar();
                                moveTimer.stop();
                                return;
                            }
                            imagenPorDefecto = bichoAbajoImagen;
                            break;
                        case "izquierda":
                            if (bichoCol > 0 && !obstaculos[bichoRow][bichoCol - 1]) {
                                bichoCol--;
                                verificarCaballerito();
                            } else {
                                mostrarAdvertenciaYReiniciar();
                                moveTimer.stop();
                                return;
                            }
                            imagenPorDefecto = bichoIzquierdaImagen;
                            break;
                        case "derecha":
                            if (bichoCol < cols - 1 && !obstaculos[bichoRow][bichoCol + 1]) {
                                bichoCol++;
                                verificarCaballerito();
                            } else {
                                mostrarAdvertenciaYReiniciar();
                                moveTimer.stop();
                                return;
                            }
                            imagenPorDefecto = bichoDerechaImagen;
                            break;
                        default:
                            System.out.println("Comando no reconocido: " + direccion);
                            moveTimer.stop();
                            return;
                    }
                    stepsTaken++;
                    repaint();
                } else {
                    moveTimer.stop();
                    verificarCaballerito();
                }
            }
        };

        moveTimer.addActionListener(moveListener);
        moveTimer.start();
    }

    private void mostrarAdvertenciaYReiniciar() {
        JOptionPane.showMessageDialog(this, "No se puede continuar debido a un obstáculo. El juego se reiniciará.");
        reiniciarJuego();
    }

    private void reiniciarJuego() {
        bichoRow = 0;
        bichoCol = 0;
        initialBichoRow = 0;
        initialBichoCol = 0;
        generarObstaculos();
        generarCaballerito();
        puntuacion = 0;
        completado = 0;
        puntuacionLabel.setText("Puntuación: " + 0);
        completadoLabel.setText("Completado: " + 0);
        repaint();
    }

    private void iniciarAnimacionRotacion() {
        rotationIndex = 0;
        if (rotationTimer != null) {
            rotationTimer.stop();
        }
        rotationTimer = new Timer(200, e -> {
            switch (rotationIndex % 4) {
                case 0:
                    imagenPorDefecto = bichoArribaImagen;
                    break;
                case 1:
                    imagenPorDefecto = bichoDerechaImagen;
                    break;
                case 2:
                    imagenPorDefecto = bichoAbajoImagen;
                    break;
                case 3:
                    imagenPorDefecto = bichoIzquierdaImagen;
                    break;
            }
            rotationIndex++;
            repaint();
            if (rotationIndex >= 8) { // Rotar dos veces
                rotationTimer.stop();
                iniciar(); // Iniciar los comandos después de la animación
            }
        });
        rotationTimer.start();
    }

    private void verificarCaballerito() {
        if (HKExiste && bichoRow == HKRow && bichoCol == HKCol) {
            HKExiste = false;
            generarCaballerito();
            metaAudio();
            puntuacion += 10; // Incrementar puntuación
            completado++; // Incrementar contador de completado
            puntuacionLabel.setText("Puntuación: " + puntuacion);
            completadoLabel.setText("Completado: " + completado);
        }
    }
}
