package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.unibl.etf.onlinefitnessmanager.exception.UserNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find_all")
    public ResponseEntity<List<UserEntity>> getAllUsers()
    {
        try
        {


        List<UserEntity> allUsers = userService.findAllUsers();

        if(allUsers != null)
        {
            return new ResponseEntity<>(allUsers, HttpStatus.OK); //if any user is found
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); //if the command executed successfully but no users are found
        }
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //if an error happened during execution, return code 400
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") Integer id)
    {
        try {
            UserEntity user = userService.findUserById(id);

            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK); //executes if a user is found
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); //if command is executed successfully, but no user found
            }
        }
        catch (UserNotFoundException ex)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //If user not found, return 404
        }
    }

    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user)
    {
        UserEntity newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED); //executes if a user is found
    }

    @PutMapping("/update")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user)
    {
        UserEntity newUser = userService.updateUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK); //executes if a user is found
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id)
    {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK); //executes if a user is found
    }



}
