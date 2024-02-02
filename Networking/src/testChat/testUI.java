package testChat;

public class testUI {
    public static void main(String [] args){
        Client c = new Client();
        Server s = new Server();

        Server.startServer();
        System.out.println("9 here");
        Client.startClient();
    }
}
