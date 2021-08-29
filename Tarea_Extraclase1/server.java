package Tarea_Extraclase1;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase crea un servidor que habilita la comunicacion entre el mismo y la clase client, con
 * el fin de hacer un chat en el que se envie una serie de datos hacia el otro usuario del chat, este realice un calculo
 * y retorne el resultado.
 *
 * @author Andres Uriza Lazo
 */
public class server {
    /**
     * socket para el servidor
     */
    static ServerSocket server;
    /**
     * socket a aceptar
     */
    static Socket sc;
    /**
     * flujo de input del socket
     */
    static DataInputStream in;
    /**
     * flujo de output del socket
     */
    static DataOutputStream out;
    /**
     * puerto del servidor
     */
    static int port = 5000;
    /**
     * valor eje y para el UI
     */
    static int y = 10;

    /**
     * extrae datos numericos a partir de un string recibido, el valor ideal deberia ser 3 enteros separados
     * por 3 comas.
     *
     * @param values string de 3 valores enteros separados por comas a procesar
     * @return double resultado del calculo realizado
     */
    private static double extract(String values) {
        int i = 0;
        StringBuilder valor = new StringBuilder();
        StringBuilder peso = new StringBuilder();
        StringBuilder impuesto = new StringBuilder();
        String current_value = "valor";

        char comma = ',';

        while (i != values.length()) {
            if (values.charAt(i) != comma) {
                if (current_value.equals("valor")) {
                    valor.append(values.charAt(i));
                }
                if (current_value.equals("peso")) {
                    peso.append(values.charAt(i));
                }
                if (current_value.equals("impuesto")) {
                    impuesto.append(values.charAt(i));
                }
            } else {
                if (current_value.equals("impuesto")) {
                    System.out.println("Error, solo los primeros 3 datos procesados");
                    break;

                }
                if (current_value.equals("valor")) {
                    current_value = "peso";
                } else {
                    current_value = "impuesto";
                }
            }
            i++;
        }
        return Double.parseDouble(valor.toString()) * Double.parseDouble(impuesto.toString()) / 100 + Double.parseDouble
                (peso.toString()) * 0.15;
    }

    /**
     * crea una interfaz utilizando Swing con un panel, caja de texto y boton para recibir, enviar y
     * desplegar datos.
     *
     * @throws IOException para llamar a la clase servidor
     */
    private static void ui() throws IOException {
        JFrame frame = new JFrame("Chat - Usuario 1");
        frame.setSize(400, 500);

        JButton button = new JButton("Send");
        button.setBounds(300, 400, 80, 30);
        frame.add(button);

        JTextField textbox = new JTextField("precio, peso, impuesto");
        textbox.setBounds(10, 400, 270, 20);
        textbox.setVisible(true);
        frame.add(textbox);

        button.addActionListener(e -> {
            String message = textbox.getText();

            try {
                out.writeUTF(message);

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        server_start(frame);
    }

    /**
     * inicia el servidor, abre un puerto y permite la entrada del socket, al igual que distribuye
     * sus datos.
     *
     * @param frame es la ventana creada con el metodo ui para agregar las labels del mensaje recibido y el resultado
     * @throws IOException para el input/output stream de ambos sockets
     */
    private static void server_start(JFrame frame) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server online");
        sc = server.accept();
        System.out.println("Client connected");

        while (true) {
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());

            String received = in.readUTF();
            double result = extract(received);
            out.writeDouble(result);

            JLabel received_label = new JLabel("Usuario 2: " + received);
            received_label.setBounds(10, y, 400, 50);
            y += 20;

            JLabel sent_label = new JLabel("Usuario 1: " + result);
            sent_label.setBounds(10, y, 400, 50);
            y += 20;

            frame.add(received_label);
            frame.add(sent_label);

            frame.revalidate();
            frame.repaint();
        }
    }

    /**
     * ejecuta la clase ui
     *
     * @param args
     * @throws IOException para la clase ui
     */
    public static void main(String[] args) throws IOException {
        ui();
    }
}