import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("Serveur allumé, en attente de connexion d'un client");
        while (true) {
            Socket client = server.accept();
            Thread t = new Thread(new ClientHandler(client));
            System.out.println("Connexion d'un client reussie");
            t.start();
        }
    }
}