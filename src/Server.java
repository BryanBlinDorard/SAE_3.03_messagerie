import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server{
    private ServerSocket serverSocket;
    private int port;
    private String nameServer;
    private List<HashMap<Socket, String>> clients;
    
    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public Server(int port, String nameServer){
        this.port = port;
        this.nameServer = nameServer;
        this.clients = new ArrayList<HashMap<Socket, String>>();
    }

    public void startServer(){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        }catch(Exception e){
            System.out.println("Error starting server");
        }
    }

    public void stopServer(){
        try{
            serverSocket.close();
            System.out.println("Server stopped");
        }catch(Exception e){
            System.out.println("Error stopping server");
        }
    }

    public void acceptClient(){
        try{
            Socket client = serverSocket.accept();
            System.out.println("Client connected");
            HashMap<Socket, String> clientMap = new HashMap<Socket, String>();
            clientMap.put(client, "");
            clients.add(clientMap);
        }catch(Exception e){
            System.out.println("Error accepting client");
        }
    }

    public void removeClient(Socket socketClient){
        try{
            for (int i = 0; i < clients.size(); i++) {
                if(clients.get(i).containsKey(socketClient)){
                    clients.remove(i);
                    System.out.println("Client removed");
                }
            }
        }catch(Exception e){
            System.out.println("Error removing client");
        }
    }
    
}