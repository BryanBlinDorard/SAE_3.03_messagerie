import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientHandler implements Runnable{
    private Socket client;
    
    public ClientHandler(Socket socket){
        this.client = socket;
    }
    
    public void run(){
        DataInputStream in;
        try {
            in = new DataInputStream(client.getInputStream());
            String nomClient = in.readUTF();
            while(true){
                String message = in.readUTF();
                System.out.println(nomClient + " : " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// DataInputStream in = new DataInputStream(client.getInputStream());
// String nomClient = in.readUTF();
// System.out.println("Client : " + nomClient);
// while (true) {
//     String message = in.readUTF();
//     System.out.println(nomClient + " : " + message);
// }