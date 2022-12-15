import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server{
    public static void main(String[] args) throws IOException {
        // On crée un serveur sur le port 1234
        ServerSocket serveur = new ServerSocket(1234);
        // On crée une liste qui va contenir les clients
        List<HashMap<Socket, String>> clients = new ArrayList<HashMap<Socket, String>>();
        System.out.println("Serveur lancé, en attente de connexion...");
        while(true){
            // On attend une connexion d'un client
            Socket client = serveur.accept();
            System.out.println("Un client vient de se connecter.");
            HashMap<Socket, String> clientMap = new HashMap<Socket, String>();
            clientMap.put(client, "");
            clients.add(clientMap);

            // On lance un thread qui va gérer la connexion avec ce client
            Thread t = new Thread(new ClientHandler(client, clients));
            t.start();
        }
    }
}