import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private String nameClient;
    private String salon;

    public Client(String nameClient){
        this.socket = null;
        this.nameClient = nameClient;
        this.salon = "";
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public void closeClient(){
        try{
            socket.close();
            System.out.println("Client disconnected");
        }catch(Exception e){
            System.out.println("Error closing client");
        }
    }

    public static void clearTerminal() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println("Erreur lors du nettoyage du terminal.");
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public void setConnexion(){
        clearTerminal();
        // On se connecte au serveur
        System.out.println("Adresse IP du serveur :");
        Scanner scanner = new Scanner(System.in);
        String ipServeur = scanner.nextLine();
        System.out.println("Port du serveur :");
        int portServeur = scanner.nextInt();
        scanner.nextLine();

        try{
            socket = new Socket(ipServeur, portServeur);
            System.out.println("Client connected");
            setSocket(socket);
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                System.out.println("Je ne trouve de serveur avec cette adresse IP.");
            } else if (e instanceof IOException) {
                System.out.println("Erreur de connexion.");
            }
        }
    }

    public void startClient() throws IOException{
        clearTerminal();
        // On se connecte au serveur
        setConnexion();
        demanderNom();
        miseEnEcoute(new DataInputStream(this.getSocket().getInputStream()));
    }

    public void miseEnEcoute(DataInputStream in){
        // On lance un thread qui va écouter les messages du serveur
        Thread t = new Thread(new Affichage(in));
        t.start();
    }

    public void demanderNom() throws IOException{
        clearTerminal();
        Scanner scanner = new Scanner(System.in);
        
        // On récupère le nom du client et on vérifie si il est déjà utilisé
        Boolean isNameSet = false;
        String nomClient = "";
        DataInputStream in = new DataInputStream(this.getSocket().getInputStream());
        DataOutputStream out = new DataOutputStream(this.getSocket().getOutputStream());
        while (!isNameSet) {
            // On demande le nom du client
            System.out.println("Nom du client :");
            nomClient = scanner.nextLine();
            
            // On envoie le nom au serveur
            if (nomClient.length() > 0) {
                out.writeUTF(nomClient);
                String isNameUsed = in.readUTF();

                if (isNameUsed.equals("true")) {
                    // Si le nom est déjà utilisé, on recommence
                    clearTerminal();
                    System.out.println("Ce nom est déjà utilisé.");
                } else {
                    // Sinon, on enregistre le nom du client
                    isNameSet = true;
                    this.setNameClient(nomClient);
                    clearTerminal();
                    System.out.println("Nom du client enregistré.");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ClientS [nameClient=" + nameClient + ", salon=" + salon + ", socket=" + socket + "]";
    }
}