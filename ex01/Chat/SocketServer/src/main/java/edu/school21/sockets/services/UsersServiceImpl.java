package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@ComponentScan(basePackages = {"edu.school21.sockets"})
public class UsersServiceImpl implements UsersService{

    UsersRepository usersRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signUp(String name, String password) {

        User user = new User(name, passwordEncoder.encode(password));

        if (usersRepository.findByName(name).isPresent()){
            return null;
        } else {
            usersRepository.save(user);
        }
        return user.getPassword();
    }

    @Override
    public boolean signIn(String name, String password) {

        User user = usersRepository.findByName(name).orElse(null);
        if (user != null) {

            return passwordEncoder.matches(password, user.getPassword());
        }

        return false;
    }

    public void saveMessage(String message) {
        usersRepository.saveMessage(message);
    }
}
