package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    int port;

    public Client(int port) {
        this.port = port;
    }

    public void getConnection() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("localhost", port);

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(s.getInputStream()));
        PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);

        System.out.println(bufferedReader.readLine());

        printWriter.println(scanner.nextLine());

        System.out.println(bufferedReader.readLine());
        printWriter.println(scanner.nextLine());
        System.out.println(bufferedReader.readLine());
        printWriter.println(scanner.nextLine());
        System.out.println(bufferedReader.readLine());
    }
}
