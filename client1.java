import java.io.*;
import java.net.*;

public class client1 {
    public static void main(String[] args) throws IOException {
        Socket s1 = new Socket("localhost", 2021);
        DataOutputStream output1 = new DataOutputStream(s1.getOutputStream());
        output1.writeUTF("Hello");
        output1.flush();
        output1.close();
        s1.close();
    }
}