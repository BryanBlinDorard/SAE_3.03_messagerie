import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        System.out.println("En attente de connexion d'un client");
        Socket s = server.accept();
        System.out.println("Client connecte");

        //On récupère le nom du client
        DataInputStream in = new DataInputStream(s.getInputStream());
        String nomClient = in.readUTF();

        //On envoie un message au client
        String message = "Bonjour " + nomClient;
        //On envoie le message au client
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.writeUTF(message);
    }
}