package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Main {

    public static LinkedList<Server> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0].split("=")[1]);


        ServerSocket server = new ServerSocket(port);

        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new Server(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
