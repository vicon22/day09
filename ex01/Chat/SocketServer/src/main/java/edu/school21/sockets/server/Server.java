package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.*;
import java.net.Socket;

import static edu.school21.sockets.app.Main.serverList;


@ComponentScan(basePackages = {"edu.school21.sockets"})
public class Server extends Thread {

    private Socket socket;
    private BufferedReader bufferedReader;
    private UsersServiceImpl usersService;
    PrintWriter printWriter;

    public Server(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        introduce();
        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        usersService = context.getBean("usersServiceImpl", UsersServiceImpl.class);
        start();
    }

    @Override
    public void run() {
        String message;
        try {
            message = bufferedReader.readLine();
            if (!message.equals(""))
                usersService.saveMessage(message);
            printWriter.println(message);
            try {
                while (true) {
                    message = bufferedReader.readLine();
                    if(message.equals("stop")) {
                        this.closeConnections();
                        break;
                    }
                    System.out.println(message);
                    for (Server server : serverList) {
                        server.sendMessage(message);
                    }
                    if (!message.equals(""))
                        usersService.saveMessage(message);
                }
            } catch (NullPointerException ignored) {}
        } catch (IOException e) {
            this.closeConnections();
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
    }

    private void introduce() {
        String cmd;
        System.out.println();
        try {
            printWriter.println("Hello from server!");
            cmd = bufferedReader.readLine();
            System.out.println(cmd);
            if (cmd.equals("signUp"))
                registerUser();
            else if (cmd.equals("signIn"))
                loginUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginUser() {
        try {
            printWriter.println("Enter username:");
            String userName = bufferedReader.readLine();
            printWriter.println("Enter password:");
            String password = bufferedReader.readLine();
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            UsersService usersService = context.getBean("usersServiceImpl", UsersService.class);
            System.out.println(userName + " " + password);
            if (usersService.signIn(userName, password)) {
                printWriter.println("0");
            } else {
                printWriter.println("2");
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.closeConnections();
        }
    }

    private void registerUser() {
        try {
            printWriter.println("Enter username:");
            String userName = bufferedReader.readLine();
            printWriter.println("Enter password:");
            String password = bufferedReader.readLine();
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            UsersService usersService = context.getBean(UsersService.class);
            System.out.println(userName + " " + password);
            if (usersService.signUp(userName, password) != null) {
                printWriter.println("0");
            } else {
                printWriter.println("1");
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                bufferedReader.close();
                printWriter.close();
                for (Server server : serverList) {
                    if(server.equals(this)) server.interrupt();
                    serverList.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}
