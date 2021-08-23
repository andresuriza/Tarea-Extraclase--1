import java.io.*;
import java.net.*;

public class server {
    
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(2021);
        Socket s = ss.accept();

        DataOutputStream output = new DataOutputStream(s.getOutputStream());  // Sets output
        DataInputStream input = new DataInputStream(s.getInputStream());
        // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Succesfully connected");    // Confirmation message

        String value = input.readUTF();     // Recibe el string
        
        System.out.println(value);
    
        output.writeUTF(value); // Reenvia el string

        output.flush();

        
        // output.close();
        // ss.close();
    }    
}