package edu.school21.sockets.services;

public interface UsersService {

    String signUp(String name, String password);
    boolean signIn(String name, String password);
}
