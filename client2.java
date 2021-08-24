import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class client2 {
    public static String socket(String message) throws IOException {
        String host = "127.0.0.1";
        int port = 5000;
        DataInputStream in;
        DataOutputStream out;

        Socket sc2 = new Socket(host, port); // Connects to server

        in = new DataInputStream(sc2.getInputStream());
        out = new DataOutputStream(sc2.getOutputStream());
        String new_message;

        while (true) {
            out.writeUTF(message);
            new_message = in.readUTF();
            break;
        }
        return new_message;
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
        JFrame frame = new JFrame("Chat box");
        frame.setSize(400,500);

        JButton button = new JButton("Send");
        button.setBounds(300, 400, 80, 30);
        frame.add(button);

        JTextField textbox = new JTextField();
        textbox.setBounds(10, 400, 270, 20);
        textbox.setVisible(true);
        frame.add(textbox);

        button.addActionListener(e -> {
            String value_pending = null;
            String value = textbox.getText();
            try {
                value_pending = socket(value);  // Envia al server
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }

            double result = extract(Objects.requireNonNull(value_pending));
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
    }
}