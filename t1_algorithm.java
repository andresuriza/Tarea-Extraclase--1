import javax.swing.*;
import java.awt.event.*;

public class t1_algorithm {
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
                if (current_value == "impuesto") {
                    System.out.println("Error, solo los primeros 3 datos procesados");
                    break;
                
                }
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
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat box"); 
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
    }
}