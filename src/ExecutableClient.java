import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ExecutableClient {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        ClientS client1 = new ClientS("");
        Boolean connecte = true;
        client1.startClient();
        DataInputStream in = new DataInputStream(client1.getSocket().getInputStream());
        DataOutputStream out = new DataOutputStream(client1.getSocket().getOutputStream());
        
        try {
            while (connecte) {
                String message = sc.nextLine();
                if (message.length() > 0) {
                    out.writeUTF(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Déconnexion forcé du serveur, fermeture du client");
        }
    }
}