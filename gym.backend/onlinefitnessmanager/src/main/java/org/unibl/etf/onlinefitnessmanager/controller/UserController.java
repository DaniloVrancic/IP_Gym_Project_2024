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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            if(avatar != null && !avatar.isEmpty())
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
        UserEntity referencedUser = userService.findUserById(user.getId());
        referencedUser.setUsername(user.getUsername());
        referencedUser.setFirstName(user.getFirstName());
        referencedUser.setLastName(user.getLastName());
        referencedUser.setCity(user.getCity());
        referencedUser.setEmail(user.getEmail());
        String avatar = user.getAvatar();

        System.out.println(referencedUser.getId() + " username: " + referencedUser.getUsername() + " last name: " + referencedUser.getLastName() + " profile pic: " + referencedUser.getAvatar());


        if(avatar != null)
        {
            String[] photoParts = avatar.split(",");
            String metadataAboutEncode = null;
            String payload = null;

            if(photoParts.length != 2) //this means that avatar is not base 64
            {
                referencedUser.setAvatar(avatar);
            }
            else //this means that the avatar string can be base64 but it's not excluded that it's not
            {
                metadataAboutEncode = photoParts[0];
                payload = photoParts[1];

                if(photoParts[1].length() > 2_083) // this is definitely not a link
                {
                    referencedUser.setAvatar(userService.saveBase64EncodedPhoto(avatar, referencedUser));
                }
                else
                {
                    String regex = "image/(\\w*)";

                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(metadataAboutEncode);

                    if (matcher.find()) {
                        referencedUser.setAvatar(userService.saveBase64EncodedPhoto(avatar, referencedUser));
                    } else {
                        referencedUser.setAvatar(avatar);
                    }
                }

            }
        }
        else
        {
            referencedUser.setAvatar(null);
        }

        UserEntity newUser = userService.updateUser(referencedUser);
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
                return new ResponseEntity<>("user_not_found", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            System.out.println("FOUND USER: " + userFromDB.getId() + " " + userFromDB.getUsername() + " " + userFromDB.getEmail() + " " + userFromDB.getPassword());


            try{
            // Check if the password matches
                if (!userService.checkPassword(userFromDB, password)) {
                    return new ResponseEntity<>("incorrect_password", HttpStatus.UNAUTHORIZED);
                }
                else //the case if the password is correct
                {
                    if(userFromDB.getActivated().equals((byte)0))
                    {
                        System.out.println("USER IS NOT YET ACTIVATED!");
                        try
                        {
                            VerificationToken newVerificationToken = new VerificationToken(LocalDateTime.now());
                            userService.verificationTokens.put(newVerificationToken, userFromDB);
                            userService.serializeHashMap(userService.verificationTokens);
                            emailSender.send(userFromDB.getEmail(),
                                    userService.buildEmail(userFromDB.getFirstName(),
                                            "http://localhost:8080/user/verify?activationToken=" + newVerificationToken.getToken()));
                        }
                        catch(Exception ex)
                        {
                            System.err.println(ex.getLocalizedMessage());
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                System.out.println(ex.getLocalizedMessage());
            }
            userFromDB.setPassword("");
        return new ResponseEntity<UserEntity>(userFromDB, HttpStatus.OK);
            // If the user exists and has correct information but hasn't been activated

    }




}
