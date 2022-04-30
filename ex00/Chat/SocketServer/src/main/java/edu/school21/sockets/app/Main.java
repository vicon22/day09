package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0].split("=")[1]);

        Server server = new Server(port);
        server.start();
    }
}
