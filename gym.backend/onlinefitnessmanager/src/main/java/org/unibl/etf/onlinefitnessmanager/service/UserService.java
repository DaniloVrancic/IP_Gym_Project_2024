package org.unibl.etf.onlinefitnessmanager.service;


import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.exception.UserAlreadyActivatedException;
import org.unibl.etf.onlinefitnessmanager.exception.UserNotFoundException;
import org.unibl.etf.onlinefitnessmanager.exception.VerificationTokenExpiredException;
import org.unibl.etf.onlinefitnessmanager.exception.VerificationTokenInvalidException;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.UserRepository;
import org.unibl.etf.onlinefitnessmanager.verification.VerificationToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {

    private final UserRepository userRepository;


    //////////////////// SECTION RESERVED ONLY FOR THE verificationTokens METHODS
    Map<VerificationToken, UserEntity> verificationTokens = new HashMap(); //Stores all the active verification tokens (expired Tokens and validated accounts will have these tokens removed from here)
    private final String hashMapFilePath = "./verificationTokens.ser"; // File to store serialized HashMap
    private final long HOURS_DEADLINE = 168L; //Number of hours that entries will keep existing in map after expiration date is over

    // Serialize HashMap to a file
    private void serializeHashMap(Map<VerificationToken, UserEntity> map) {
        try (FileOutputStream fileOut = new FileOutputStream(hashMapFilePath)){
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
             objectOut.writeObject(verificationTokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<VerificationToken, UserEntity> deserializeHashMap() {
        if (Files.exists(Paths.get(hashMapFilePath))) {
            try (FileInputStream fileIn = new FileInputStream(hashMapFilePath);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                return (Map<VerificationToken, UserEntity>) objectIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    private void removeActivatedUsers() { //No need to keep users which are already activated in the verification map
        verificationTokens.entrySet().removeIf(entry -> entry.getValue().getActivated() == 1);
    }

    private void removeExpiredUsers(long hours) //removes all entries if they are hanging in the map longer than this amount of hours
    {
        LocalDateTime expirationDealine = LocalDateTime.now().minusHours(hours);
        verificationTokens.entrySet().removeIf(entry -> expirationDealine.isAfter(entry.getKey().getExpiresAt()));
    }

    private void removeSufficientEntries()
    {
     removeActivatedUsers();
     removeExpiredUsers(this.HOURS_DEADLINE);
    }

    //////////////////// END_OF verificationTokens METHODS

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user)
    {
        user.setPassword(hashString(user.getPassword()));//Hashing the plaintext password of the user

        if(user.getActivated() == 0)
        {
        verificationTokens.put(new VerificationToken(LocalDateTime.now()),
                                                                        user);
        }
        //TODO: Implement the sending email METHOD AND CALL IT HERE
        return userRepository.save(user); //Saves user to repository (With hashed password)
    }


    /**
     * Depending on the UserID value, this photo will be saved on the file system in a predetermined folder, under
     * the UserID of the user that is requesting the save to take place.
     * @param base64encodedPhoto the full photo base64 encoded
     * @param user the user making the save request
     * @return relative Path to the photo
     */
    public String saveBase64EncodedPhoto(String base64encodedPhoto, UserEntity user) {
        String[] photoParts = base64encodedPhoto.split(",");
        String metadataAboutEncode = photoParts[0];
        String payload = photoParts[1];

        String regex = "image\\/(\\w*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(metadataAboutEncode);

        String photoType;

        if (matcher.find()) {
            photoType = matcher.group(1);
        } else {
            photoType = "";
        }

        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);

        Path directoryPath = Paths.get(".//profile_pictures//" + user.getId());
        Path outputPath = directoryPath.resolve("profilePic" + ((photoType.length() > 0) ? "." + photoType : photoType));

        try {
            // Check if the directory exists, if not create it
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getLocalizedMessage());
        }
        System.out.println(outputPath);
        return outputPath.toString();
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity updateUser(UserEntity user)
    {
        if(!user.getPassword().equals(findUserById(user.getId()).getPassword()))// if the password changed, hash the new password
        {
            user.setPassword(hashString(user.getPassword())); //hash newly set password before saving
        }
        return userRepository.save(user);
    }

    public void deleteUser(Integer id)
    {
        userRepository.deleteById(id);
    }

    public UserEntity findUserById(Integer id)
    {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User ID: [" + id + "], not found in repository."));
    }

    public UserEntity activateUser(String activationUUID)
    {
        VerificationToken searchToken = new VerificationToken(activationUUID);
        VerificationToken foundKey = null;

        //This for finds the original key from the entry set of the map
        for (Map.Entry<VerificationToken, UserEntity> entry : verificationTokens.entrySet()) {
            if (entry.getKey().equals(searchToken)) {
                foundKey = entry.getKey();
            }
        }

        if(foundKey.isExpired())
        {
            throw new VerificationTokenExpiredException();
        }
        else if(foundKey == null)
        {
            throw new VerificationTokenInvalidException();
        }

        UserEntity foundUser = null;
        if(foundKey != null) {
            foundUser = verificationTokens.get(foundKey);
        }


        if(foundUser != null)
        {
            if(foundUser.getActivated() == (byte)0)
            {
                foundUser.setActivated((byte)1);
                userRepository.save(foundUser); //save the change to the database
            }
            else
            {
                throw new UserAlreadyActivatedException();
            }
        }
        removeSufficientEntries();
        return foundUser;
    }

    // PRIVATE METHODS FOR THIS SERVICE'S INTERNAL USE

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
