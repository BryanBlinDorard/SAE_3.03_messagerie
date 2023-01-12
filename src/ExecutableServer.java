import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExecutableServer {
    public static void main(String[] args) throws IOException {
        // On crée un serveur sur le port 1234
        ServerSocket serveur = new ServerSocket(1234);

        // On crée une liste qui va contenir les clients
        List<ClientS> clients = new ArrayList<ClientS>();
        System.out.println("Serveur lancé, en attente de connexion...");

        List<Salon> salons = new ArrayList<Salon>();
        while(true){
            // On attend une connexion d'un client
            Socket socket = serveur.accept();
            System.out.println("Client connecté");
            ClientS client = new ClientS("");
            client.setSocket(socket);
            clients.add(client);

            // On lance un thread qui va gérer la connexion avec ce client
            Thread t = new Thread(new ClientHandler(client, clients, salons));
            t.start();
        }
    }
}
