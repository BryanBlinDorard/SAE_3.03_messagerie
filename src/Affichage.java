import java.io.DataInputStream;
import java.io.IOException;

public class Affichage implements Runnable {
    private DataInputStream in;
    public Affichage(DataInputStream in){
        this.in = in;
    }
    public void run(){
        while(true){
            try {
                // On lit le message envoyé par le serveur
                String message = in.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
