import java.io.*;
import java.net.*;

public class client2 {
    public static void main(String[] args) throws IOException {
        Socket s2 = new Socket("localhost", 2021);
        DataOutputStream output2 = new DataOutputStream(s2.getOutputStream());
        output2.writeUTF("Hello");
        output2.flush();
        output2.close();
        output2.close();
    }
}