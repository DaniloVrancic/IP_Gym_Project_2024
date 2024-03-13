package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.unibl.etf.onlinefitnessmanager.additional.email.EmailSender;
import org.unibl.etf.onlinefitnessmanager.exception.UserNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.service.UserService;
import org.unibl.etf.onlinefitnessmanager.verification.VerificationToken;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;
    private final EmailSender emailSender;

    @Autowired
    public UserController(UserService userService, EmailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
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
                user.setPassword("");
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

    @PostMapping(value = "/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        UserEntity newUser;
        try
        {
            String avatar = user.getAvatar(); //Saves the full raw data into String avatar
            user.setAvatar(null); //sets the Avatar inside user to null so that it doesn't try to save the whole raw picture data to the database
            newUser = userService.addUser(user); //saves to Database (without profile picture link)

            if(avatar != null && avatar.length() > 0)
            {
                newUser.setAvatar(userService.saveBase64EncodedPhoto(avatar, newUser));
                userService.updateUser(newUser); //updates the profile with profile picture link
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newUser.setPassword(""); // To hide password hash
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

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("activationToken") String token)
    {
        try
        {
            UserEntity activatedUser = userService.activateUser(token);
            String returnMessage = "User: " + activatedUser.getUsername() + " [ E-mail: " + activatedUser.getEmail() + " ], successfully activated!";
            return new ResponseEntity<>(returnMessage, HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<String>(ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserEntity user) {

            String username = user.getUsername();
            String password = user.getPassword();

            UserEntity userFromDB = null;
            try
            {
                userFromDB = userService.findUserByUsername(username);
            }
            catch(UserNotFoundException ex)
            {
                System.out.println("USER " + username + " NOT FOUND IN DB!");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            System.out.println("FOUND USER: " + userFromDB.getId() + " " + userFromDB.getUsername() + " " + userFromDB.getEmail() + " " + userFromDB.getPassword());


            try{


            // Check if the password matches
            if (!userService.checkPassword(userFromDB, password)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            }
            catch(Exception ex)
            {
                System.out.println(ex.getLocalizedMessage());
            }

            // If the user exists and has correct information but hasn't been activated
            if(userFromDB.getActivated().equals((byte)0))
            {
                System.out.println("USER IS NOT YET ACTIVATED!");
                try
                {
                    VerificationToken newVerificationToken = new VerificationToken(LocalDateTime.now());
                    emailSender.send(userFromDB.getEmail(),
                                        userService.buildEmail(userFromDB.getFirstName(),
                                            "http://localhost:8080/user/verify?activationToken=" + newVerificationToken.getToken()));
                }
                catch(Exception ex)
                {
                    System.err.println(ex.getLocalizedMessage());
                }
            }
            return new ResponseEntity<UserEntity>(userFromDB, HttpStatus.OK);
    }




}
