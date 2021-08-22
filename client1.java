import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;

public class client1 {
    public static int y = 10;

    public static double extract(String product) {
        int i = 0;
        String valor = "";
        String peso = "";
        String impuesto = "";
        String current_value = "valor";
        char comma = ',';

        while (i != product.length()) {
            if (product.charAt(i) != comma) {   // Si no es coma
                if (current_value == "valor") {
                    valor += String.valueOf(product.charAt(i));
                }
                if (current_value == "peso") {
                    peso += String.valueOf(product.charAt(i));
                }
                if (current_value == "impuesto") {
                    impuesto += String.valueOf(product.charAt(i));
                }
                i++;
            }        
            else { // Si es coma
                if (current_value == "valor"){
                    current_value = "peso";
                }
                else {
                    current_value = "impuesto";
                }   
                i++;
            }
        }
        return Double.parseDouble(valor) * Double.parseDouble(impuesto) / 100 + Double.parseDouble(peso) * 0.15; 
    }
    public static void main(String[] args) throws IOException {
        Socket s1 = new Socket("localhost", 2021);
        DataOutputStream output1 = new DataOutputStream(s1.getOutputStream());  // Sets output
        output1.writeUTF("Hello");
        output1.flush();
        output1.close();
        

        JFrame frame = new JFrame("Chat box 1"); 
        frame.setSize(400,500); 

        JButton button = new JButton("Send");
        button.setBounds(300, 400, 80, 30); 
        frame.add(button);
        
        JTextField textbox = new JTextField();
        textbox.setBounds(10, 400, 270, 20);
        textbox.setVisible(true);
        frame.add(textbox);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String value = textbox.getText();
                double result = extract(value);
                String result_string = Double.toString(result);
                System.out.println(result_string);

                JLabel result_label = new JLabel(result_string);
                result_label.setBounds(10, y, 400, 50);
                y += 20;

                frame.add(result_label);
                frame.revalidate();
                frame.repaint();    // Reescribe la label (no se crea una nueva si no que se vuelve a escribir)
            }
        });
    
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);  
        
        s1.close();
    }
}