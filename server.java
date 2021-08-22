import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(2021);
        Socket s1 = ss.accept();
        Socket s2 = ss.accept();

        DataInputStream input1 = new DataInputStream(s1.getInputStream());
        DataInputStream input2 = new DataInputStream(s2.getInputStream());

        System.out.println("Succesfully connected");

        ss.close();
    }    
}