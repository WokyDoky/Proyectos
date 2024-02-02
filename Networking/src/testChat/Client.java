package testChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    Client(){}

    public static void main (String [] args){
        startClient();
    }
    public static void startClient (){
        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc = new Scanner(System.in);

        try {
            clientSocket = new Socket("127.0.0.1", 8888);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Thread sender = new Thread(new Runnable() {
                String txt;
                @Override
                public void run() {
                    for(;;){
                        txt = sc.nextLine();
                        out.println(txt);
                        out.flush();
                    }
                }
            });
            sender.start();

            Thread receiver = new Thread(new Runnable() {
                String txt = "Hello World";
                @Override
                public void run() {
                    try {
                        while (txt != null){
                            System.out.println("Server: "+txt);
                            txt = in.readLine();
                        }
                        System.out.println("Disconnected");
                        out.close();
                        clientSocket.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            receiver.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
