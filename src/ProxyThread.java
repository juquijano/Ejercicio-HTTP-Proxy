import java.time.LocalDateTime;
import java.util.*;
import java.net.*;
import java.io.*;

public class ProxyThread extends Thread {
    private Socket socket;
    private static final int BUFFER_SIZE = 32768;
    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        //get input from user
        //send request to server
        //get response from server
        //send response to user

        try {
            BufferedReader from_client = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream to_client = new DataOutputStream(socket.getOutputStream());

            String inputLine;
            int count = 0;
            String urlToCall = "";
            //comenzando a recibir solicitudes del cliente
            System.out.println(LocalDateTime.now() + " : Request: ");
            while ((inputLine = from_client.readLine()) != null) {
                try {
                    // inputLine que contiene los headers del request
                    System.out.println(LocalDateTime.now() + " : " + inputLine);
                    StringTokenizer token = new StringTokenizer(inputLine);
                    token.nextToken();
                } catch (Exception e) {
                    break;
                }
                // tomo la primer linea para obtener la url
                if (count == 0) {
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[1];
                }
                count++;
            }
            // fin de las solicitudes del cliente
            BufferedReader reader = null;
            try {
                // enviando request al server y obteniendo respuestas
                URL url = new URL(urlToCall);
                URLConnection conn = url.openConnection();
                HttpURLConnection huc = (HttpURLConnection)conn;
                conn.setDoInput(true);
                conn.setDoOutput(true);
                // leyendo respuestas del server
                InputStream from_server = null;
                if (conn.getContentLength() > 0) {
                    try {
                        from_server = conn.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(from_server));
                        System.out.println(LocalDateTime.now() + " : Response:");
                        System.out.println(LocalDateTime.now() + " : Status: " + huc.getResponseCode());
                    } catch (IOException ioe) {
                        System.err.println(LocalDateTime.now() + " : IO EXCEPTION: " + ioe);
                    } catch (NullPointerException e) {
                        System.err.println(LocalDateTime.now() + " : Ups! Algo malió sal.");
                    }
                }
                // fin de las solicitudes al server
                // enviando respuestas al cliente
                byte buffer[] = new byte[ BUFFER_SIZE ];
                int bytes_read;
                while ((bytes_read = from_server.read( buffer, 0, BUFFER_SIZE )) != -1 )
                {
                    // la siguiente linea está comentada porque me resultó mucho spam "loguear" la respuesta completa.
                    // System.out.println(LocalDateTime.now() + " : To client: " + new String(buffer, "UTF-8")+"<---");
                    to_client.write( buffer, 0, bytes_read );
                    to_client.flush();
                }
                to_client.close();
                // fin de envio de respuestas
            } catch (NullPointerException e) {
                System.err.println(LocalDateTime.now() + " : Sin respuesta del server.");
            } catch (Exception e) {
                // en caso de error, se envía respuesta vacía y continúa el proceso
                System.err.println(LocalDateTime.now() + " : Conexión cerrada: " + e);
                to_client.writeBytes("");
            } finally {
                to_client.close();
                from_client.close();
                socket.close();
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
