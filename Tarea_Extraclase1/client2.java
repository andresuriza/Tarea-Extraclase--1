package Tarea_Extraclase1;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Esta clase es un cliente que se conecta a la clase servidor, utilizando sockets en un puerto especifico, al igual que
 * contiene el metodo necesario para procesar el calculo del dato recibido del cliente 1.
 *
 * @author Andres Uriza Lazo
 */
public class client2 {
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
     * abre los stream input/output para permitir la entrada y salida de datos.
     *
     * @throws IOException para los streams de input/output
     */
    private static void socket() throws IOException {
        Socket sc2 = new Socket(host, port); // Connects to server

        in = new DataInputStream(sc2.getInputStream());
        out = new DataOutputStream(sc2.getOutputStream());

        String received = in.readUTF();
        System.out.println(received);

        double sent = extract(received);
        out.writeDouble(sent);
        System.out.println(sent);

    }

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
            if (values.charAt(i) != comma) {   // Si no es coma
                if (current_value.equals("valor")) {
                    valor.append(values.charAt(i));
                }
                if (current_value.equals("peso")) {
                    peso.append(values.charAt(i));
                }
                if (current_value.equals("impuesto")) {
                    impuesto.append(values.charAt(i));
                }
            } else { // Si es coma
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
     */
    private static void ui() {
        JFrame frame = new JFrame("Usuario 2");
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
            double result = 0;

            try {
                out.writeUTF(message);
                result = in.readDouble();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            JLabel sent_label = new JLabel("Usuario 1: " + message);
            sent_label.setBounds(10, y, 400, 50);
            y += 20;

            System.out.println(result);
            JLabel received_label = new JLabel("Usuario 2: " + result);
            received_label.setBounds(10, y, 400, 50);
            y += 20;

            frame.add(sent_label);
            frame.add(received_label);

            frame.revalidate();
            frame.repaint();

        });

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * ejecuta la clase socket y ui
     *
     * @param args
     * @throws IOException para la clase socket y ui
     */
    public static void main(String[] args) throws IOException {
        ui();
        socket();
    }
}