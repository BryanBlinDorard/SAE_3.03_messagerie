import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDateTime; 

public class ClientHandler implements Runnable{
    private Socket client;
    private List<HashMap<Socket, String>> clients;
    public ClientHandler(Socket socket, List<HashMap<Socket, String>> clients){
        this.client = socket;
        this.clients = clients;
    }
    
    public void run(){
        try {
            // On récupère le nom du client et on vérifie si il est déjà utilisé
            Boolean isNameSet = false;
            String nomClient = "";
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while (!isNameSet) {
                nomClient = in.readUTF();
                if (nomClient.length() > 0) {
                    Boolean isNameUsed = false;
                    for (HashMap<Socket, String> client : clients) {
                        Socket keySocket = client.keySet().iterator().next();
                        if (client.get(keySocket).equals(nomClient)) {
                            isNameUsed = true;
                            out.writeUTF("true");
                        }
                    }
                    if (!isNameUsed) {
                        for (HashMap<Socket, String> client : clients) {
                            Socket keySocket = client.keySet().iterator().next();
                            if (keySocket == this.client) {
                                client.put(keySocket, nomClient);
                            }
                        }
                        isNameSet = true;
                        out.writeUTF("false");
                    }
                }
            }

            while(true){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                // On lit le message envoyé par le client
                String message = in.readUTF();
                String msg_a_envoyer = nomClient+ " - "+ dtf.format(now)+ " - " + message;
                System.out.println(msg_a_envoyer);

                // On envoie le message à tous les clients
                for (HashMap<Socket, String> client : clients) {
                    Socket keySocket = client.keySet().iterator().next();
                    if (keySocket != this.client) {
                        DataOutputStream out2 = new DataOutputStream(keySocket.getOutputStream());
                        out2.writeUTF(msg_a_envoyer);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Un client vient de se déconnecter.");
            for (HashMap<Socket, String> client : clients) {
                Socket keySocket = client.keySet().iterator().next();
                if (keySocket == this.client) {
                    clients.remove(client);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}