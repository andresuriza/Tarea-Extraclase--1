package Tarea_Extraclase1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase crea un servidor que habilita la comunicacion entre dos sockets, el de la clase client y client 2, con
 * el fin de hacer un chat en el que se envie una serie de datos hacie el otro cliente, este realice un calculo y
 * devuelve el resultado.
 *
 * @author Andres Uriza Lazo
 */
public class server {
    /**
     * socket para el servidor
     */
    static ServerSocket server;
    /**
     * dos sockets a aceptar
     */
    static Socket sc, sc2;
    /**
     * flujos de input para los sockets
     */
    static DataInputStream in, in2;
    /**
     * flujos de output para los sockets
     */
    static DataOutputStream out, out2;
    /**
     * puerto del servidor
     */
    static int port = 5000;

    /**
     * inicia el servidor, abre un puerto y permite la entrada de los dos sockets, al igual que distribuye
     * sus datos.
     *
     * @throws IOException para el input/output stream de ambos sockets
     */
    private static void server_start() throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server online");
        sc = server.accept();
        System.out.println("Client 1 connected");
        sc2 = server.accept();
        System.out.println("Client 2 connected");

        while (true) {
            in = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            in2 = new DataInputStream(sc2.getInputStream());
            out2 = new DataOutputStream(sc2.getOutputStream());

            String received1 = in.readUTF();
            System.out.println(received1);
            out2.writeUTF(received1);

            double result2 = in2.readDouble();
            System.out.println(result2);
            out.writeDouble(result2);

            String received2 = in2.readUTF();
            System.out.println(received2);
            out.writeUTF(received2);

            double result1 = in.readDouble();
            System.out.println(result1);
            out2.writeDouble(result1);
        }
    }

    /**
     * ejecuta la clase servidor
     *
     * @param args
     * @throws IOException para el servidor
     */
    public static void main(String[] args) throws IOException {
        server_start();
    }
}