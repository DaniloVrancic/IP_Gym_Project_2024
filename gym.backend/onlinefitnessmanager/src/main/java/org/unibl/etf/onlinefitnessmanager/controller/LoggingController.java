package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/logging")
public class LoggingController {

    @GetMapping("/get")
    public ResponseEntity<?> getSpringLog()
    {
        Path pathToLog = Paths.get("./spring.log");
        String linesToReturn = null;
        try
        {
            linesToReturn = new String(Files.readAllBytes(pathToLog));
        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
        }

        return ResponseEntity.ok(linesToReturn);

    }
}
