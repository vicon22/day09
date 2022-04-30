package edu.school21.sockets.server;

import edu.school21.sockets.app.Main;
import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@ComponentScan(basePackages = {"edu.school21.sockets"})
public class Server {

    @Autowired
    private UsersService usersService;
    private int port;
    ApplicationContext context;

    public Server(int port) {
        this.port = port;
        context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        usersService = context.getBean("usersServiceImpl", UsersService.class);
    }

//    public static void main(String[] args) throws IOException {
//
//
//        Integer port = Integer.parseInt(args[0].split("=")[1]);
//        ApplicationContext context = new AnnotationConfigApplicationContext(Server.class);
//        UsersService usersService = context.getBean(Server.class).usersService;
//
//
//        ServerSocket ss = new ServerSocket(port);
//        Socket s = ss.accept();
//
//        PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
//        printWriter.println("Hello from Server!");
//
//        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(s.getInputStream()));
//
//        if (bufferedReader.readLine().equals("signUp")){
//            signUp(bufferedReader, printWriter, usersService);
//        }else{
//            System.out.println("ByBy");
//            printWriter.println("Server: ByBy");
//        }
//
//
//    }

    public void start() throws IOException {

        ServerSocket ss = new ServerSocket(port);
        Socket s = ss.accept();

        PrintWriter printWriter = new PrintWriter(s.getOutputStream(), true);
        printWriter.println("Hello from Server!");
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(s.getInputStream()));

        if (bufferedReader.readLine().equals("signUp")){
            signUp(bufferedReader, printWriter, usersService);
        }else{
            System.out.println("ByBy");
            printWriter.println("Server: ByBy");
        }

    }

    private static void signUp(BufferedReader bufferedReader, PrintWriter printWriter, UsersService usersService) throws IOException {
        printWriter.println("Enter username:");
        String username = bufferedReader.readLine();
        printWriter.println("Enter password:");
        String password = bufferedReader.readLine();

        if (usersService.signUp(username, password) != null) {
            printWriter.println("Successful!");
        }else {
            printWriter.println("Something went wrong!");
        }

    }
}
