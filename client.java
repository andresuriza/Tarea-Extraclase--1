import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client {
    public static double socket_send(String message, DataInputStream in, DataOutputStream out) throws IOException {
        out.writeUTF(message);
        return in.readDouble();
    }

    public static int y = 10;

    public static double extract(String product) {
        int i = 0;
        StringBuilder valor = new StringBuilder();
        StringBuilder peso = new StringBuilder();
        StringBuilder impuesto = new StringBuilder();
        String current_value = "valor";
        char comma = ',';

        while (i != product.length()) {
            if (product.charAt(i) != comma) {   // Si no es coma
                if (current_value.equals("valor")) {
                    valor.append(product.charAt(i));
                }
                if (current_value.equals("peso")) {
                    peso.append(product.charAt(i));
                }
                if (current_value.equals("impuesto")) {
                    impuesto.append(product.charAt(i));
                }
            }
            else { // Si es coma
                if (current_value.equals("impuesto")) {
                    System.out.println("Error, solo los primeros 3 datos procesados");
                    break;

                }
                if (current_value.equals("valor")){
                    current_value = "peso";
                }
                else {
                    current_value = "impuesto";
                }
            }
            i++;
        }
        return Double.parseDouble(valor.toString()) * Double.parseDouble(impuesto.toString()) / 100 + Double.parseDouble
                (peso.toString()) * 0.15;
    }
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 5000;
        DataInputStream in;
        DataOutputStream out;

        Socket sc = new Socket(host, port); // Connects to server

        in = new DataInputStream(sc.getInputStream());
        out = new DataOutputStream(sc.getOutputStream());

//----------------------------------------------------------------------------------------------------------------------
        JFrame frame = new JFrame("CLIENT");
        frame.setSize(400, 500);

        JButton button = new JButton("Send");
        button.setBounds(300, 400, 80, 30);
        frame.add(button);

        JTextField textbox = new JTextField();
        textbox.setBounds(10, 400, 270, 20);
        textbox.setVisible(true);
        frame.add(textbox);

        DataInputStream finalIn = in;
        DataOutputStream finalOut = out;

        button.addActionListener(e -> {
            String value = textbox.getText();
            double result = 0;

            try {
                result = socket_send(value, finalIn, finalOut);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            String result_string = Double.toString(result);

            JLabel result_label = new JLabel("Cliente: " + result_string);
            result_label.setBounds(10, y, 400, 50);
            y += 20;

            frame.add(result_label);
            frame.revalidate();
            frame.repaint();    // Reescribe la label (no se crea una nueva si no que se vuelve a escribir)
        });

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);

        out.writeDouble(extract(in.readUTF())); // Listo para recibir y devolver
    }
}