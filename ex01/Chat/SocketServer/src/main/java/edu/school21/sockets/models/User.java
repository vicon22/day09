package edu.school21.sockets.models;

public class User {

    private long identifier;
    private String name;
    private String password;

    public User(long identifier, String name, String password) {
        this.identifier = identifier;
        this.name = name;
        this.password = password;
    }

    public User(String name, String password) {
        this.identifier = -1;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
