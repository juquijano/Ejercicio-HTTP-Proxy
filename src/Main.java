import java.time.LocalDateTime;
import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        int puerto = 8080;	//por defecto

        try {
            puerto = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println(LocalDateTime.now() + " : Sin parámetro, se utilizará el puerto 8080 por defecto.");
        }

        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println(LocalDateTime.now() + " : Iniciando Server Socket: " + serverSocket);
        } catch (IOException e) {
            System.err.println(LocalDateTime.now() + " : Error: No se puede escuchar en el puerto " + puerto);
            System.err.println(LocalDateTime.now() + " : Error: " + e);
            System.exit(-1);
        }
        try {
            while (listening) {
                new ProxyThread(serverSocket.accept()).start();
            }
        } catch (Exception e) {
            System.err.println(LocalDateTime.now() + " : Error: No se puede iniciar el Server Socket " + serverSocket);
            System.err.println(LocalDateTime.now() + " : Compruebe que no haya otro server corriendo en el mismo puerto," +
                    " o contáctese con julian.quijano@mercadolibre.com. Disculpe las molestias.");
            System.err.println(LocalDateTime.now() + " : Error: " + e);
        } finally {
            serverSocket.close();
        }
    }
}
