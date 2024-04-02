package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unibl.etf.onlinefitnessmanager.service.FitnessProgramService;
import org.unibl.etf.onlinefitnessmanager.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Base64;
import java.util.Random;

@Controller
@RequestMapping("/photo")
public class    PhotoController {


    UserService userService;
    FitnessProgramService fitnessProgramService;

    @Autowired
    public PhotoController(UserService userService, FitnessProgramService fitnessProgramService)
    {
        this.userService = userService;
        this.fitnessProgramService = fitnessProgramService;
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<FileSystemResource> getProfilePhoto(@PathVariable("id") Integer profileId)
    {


        String pathToPhoto = userService.findUserById(profileId).getAvatar();

        File photoFile = new File(pathToPhoto);

        System.out.println("CONTROLER FOR PROFILE--------------------");
        System.out.println("PhotoFile:" + photoFile.toString());
        System.out.println("pathToPhoto: " + pathToPhoto);
        if(photoFile.exists())
        {
            if(pathToPhoto.endsWith(".png"))
            {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            }
            else if(pathToPhoto.endsWith(".gif"))
            {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_GIF) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            }
            else
            {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            }

        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/program/{id}")
    public ResponseEntity<?> getFitnessProgramPhoto(@PathVariable("id") Integer programId) {


        String pathToPhoto = fitnessProgramService.findFitnessProgramById(programId).getImageUrl();

        if(pathToPhoto == null)
        {
            File randomPic = getRandomDefaultProgramImage();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG) // Change the content type based on your photo type
                    .body(new FileSystemResource(randomPic.toPath()));
        }
        File photoFile = new File(pathToPhoto);

        if (photoFile.exists()) { //if the photo exists on the local fileSystem
            if (pathToPhoto.endsWith(".png")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            } else if (pathToPhoto.endsWith(".gif")) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_GIF) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // Change the content type based on your photo type
                        .body(new FileSystemResource(photoFile));
            }

        } else { //if the photo doesn't exist, send a default photo

            File randomPic = getRandomDefaultProgramImage();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG) // Change the content type based on your photo type
                    .body(new FileSystemResource(randomPic.toPath()));
        }
    }


    private File getRandomDefaultProgramImage()
    {

        Random random = new Random();
        int randomNumber = 0;
        File randomImage = null;

        synchronized (this)
        {

            randomNumber = random.nextInt(5) + 1;
            randomImage = new File("./default/fitness-program-image/defaultImage" + randomNumber + ".png");
        }
        return randomImage;
    }


}
