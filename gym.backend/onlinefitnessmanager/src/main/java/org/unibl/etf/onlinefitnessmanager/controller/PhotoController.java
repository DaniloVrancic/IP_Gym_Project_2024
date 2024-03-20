package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.unibl.etf.onlinefitnessmanager.service.UserService;

import java.io.File;

@Controller
@RequestMapping("/photo")
public class PhotoController {


    UserService userService;

    @Autowired
    public PhotoController(UserService userService)
    { this.userService = userService; }

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

}
