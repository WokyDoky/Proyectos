package testChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {


    public Server() {

    }

    public static void main (String [] args){
        startServer();

    }
    public static void startServer (){
        final ServerSocket serverSocket;
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("waiting on client...");

            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    for (;;){
                        msg = sc.next();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            sender.start();

            Thread receive = new Thread(new Runnable() {
                String txt = "Hola mundo";
                @Override
                public void run() {
                    try {
                        txt = in.readLine();
                        while (txt != null) {
                            System.out.println("Client: " + txt);
                            txt = in.readLine();
                        }
                        System.out.println("Client disconnected");

                        out.close();
                        clientSocket.close();
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receive.start();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
