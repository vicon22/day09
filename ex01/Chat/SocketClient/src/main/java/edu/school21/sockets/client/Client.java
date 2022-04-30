package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    int port;
    BufferedReader bufferedReader;
    PrintWriter printWriter;
    Socket socket;
    Scanner scanner;
    String nickname;

    public Client(int port) {
        this.port = port;
    }

    public void getConnection() throws IOException {
        scanner = new Scanner(System.in);
        socket = new Socket("localhost", port);
        String command;

        bufferedReader = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);

        System.out.println(bufferedReader.readLine());
        registration();
        new ReadMessage().start();
        new WriteMessage().start();

    }

    private void closeConnections() {
        if (!socket.isClosed()) {
            try {
                socket.close();
                bufferedReader.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registration() {
        try {
            String cmd = "";
            while (!cmd.equals("signIn") && !cmd.equals("signUp")) {
                System.out.println("Введите команду: signIn или signUp");
                cmd = scanner.nextLine();
            }
            printWriter.println(cmd);
            String serverMsg = bufferedReader.readLine();
            System.out.println(serverMsg);
            nickname = scanner.nextLine();
            printWriter.println(nickname);

            serverMsg = bufferedReader.readLine();
            System.out.println(serverMsg);
            String password = scanner.nextLine();
            printWriter.println(password);
            serverMsg = bufferedReader.readLine();
            if (serverMsg.equals("0"))
                System.out.println("Start messaging");
            else if (serverMsg.equals("1")) {
                System.out.println("User with this name already exists. Use signIn");
                this.closeConnections();
                System.exit(1);
            } else {
                System.out.println("Incorrect password or login");
                this.closeConnections();
                System.exit(1);
            }
        } catch (IOException ignored) {
        }
    }


        private class ReadMessage extends Thread {
        @Override
        public void run() {
            String str;
            while (true) {
                try {
                    str = bufferedReader.readLine();
                    if (str.equals("You have left the chat.")){
                        Client.this.closeConnections();
                        break;
                    }
                    System.out.println(str);
                } catch (IOException e) {
                    Client.this.closeConnections();
                }
            }
        }
    }


    private class WriteMessage extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                userWord = scanner.nextLine();
                if (userWord.equals("Exit")) {
                    printWriter.println("Exit");
                    Client.this.closeConnections();
                    break;
                } else {
                    printWriter.println(nickname + ": " + userWord);
                }
            }
        }
    }


}
