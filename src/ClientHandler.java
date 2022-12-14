import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDateTime; 

public class ClientHandler implements Runnable{
    private Socket client;
    private List<Socket> clients;
    public ClientHandler(Socket socket, List<Socket> clients){
        this.client = socket;
        this.clients = clients;
    }
    
    public void run(){
        try {
            // On récupère le nom du client
            DataInputStream in = new DataInputStream(client.getInputStream());
            String nomClient = in.readUTF();
            System.out.println(nomClient + " vient de se connecter.");

            while(true){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                // On lit le message envoyé par le client
                String message = in.readUTF();
                String msg_a_envoyer = nomClient+ " - "+ dtf.format(now)+ " - " + message;
                System.out.println(msg_a_envoyer);

                // On envoie le message à tous les clients
                for(Socket s : clients){
                    if(s != client){
                        DataOutputStream out = new DataOutputStream(s.getOutputStream());
                        out.writeUTF(msg_a_envoyer);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Un client vient de se déconnecter.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}