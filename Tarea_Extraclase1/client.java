package Tarea_Extraclase1;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Esta clase es un cliente que se conecta a la clase servidor, utilizando sockets en un puerto especifico, al igual que
 * contiene el metodo necesario para procesar el calculo del dato recibido.
 *
 * @author Andres Uriza Lazo
 */
public class client {
    /**
     * direccion IP local
     */
    static String host = "127.0.0.1";
    /**
     * puerto del servidor
     */
    static int port = 5000;
    /**
     * stream de input
     */
    static DataInputStream in;
    /**
     * stream de output
     */
    static DataOutputStream out;
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
     * @throws IOException para llamar a la clase socket
     */
    private static void ui() throws IOException {
        JFrame frame = new JFrame("Chat - Usuario 2");
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

        socket(frame);
    }

    /**
     * abre los stream input/output para permitir la entrada y salida de datos al igual que mostrarlos en la interfaz.
     *
     * @param frame es la ventana creada con el metodo ui para agregar las labels del mensaje recibido y el resultado
     * @throws IOException para los streams de input/output
     */
    private static void socket(JFrame frame) throws IOException {
        Socket sc = new Socket(host, port);

        in = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());

        while (true) {
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