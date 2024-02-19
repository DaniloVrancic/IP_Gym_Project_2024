package org.unibl.etf.onlinefitnessmanager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.UserRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user)
    {

        user.setPassword(hashString(user.getPassword()));//Hashing the plaintext password of the user

        return userRepository.save(user); //Saves user to repository (With hashed password)
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    private static String hashString(String text) {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(text.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }
        catch(NoSuchAlgorithmException ex)
        {
            System.err.println(ex.getLocalizedMessage());
            return text;
        }
    }

}
