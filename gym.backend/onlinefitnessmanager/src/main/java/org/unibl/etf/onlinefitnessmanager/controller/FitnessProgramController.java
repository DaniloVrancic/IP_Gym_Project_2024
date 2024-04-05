package org.unibl.etf.onlinefitnessmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.service.FitnessProgramService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/program")
public class FitnessProgramController {


    private final FitnessProgramService fitnessProgramService;
    private final String FILEPATH_FOR_UPDATE_SUBSCRIBERS = "./new_programs/";
    private final String FILE_NAME = "programs.txt";

    private final Path fullPath = Paths.get(FILEPATH_FOR_UPDATE_SUBSCRIBERS + FILE_NAME);

    private PrintWriter fileWriter = null;

    @Autowired
    public FitnessProgramController(FitnessProgramService fitnessProgramService) {
        this.fitnessProgramService = fitnessProgramService;

        Path dir = Paths.get(FILEPATH_FOR_UPDATE_SUBSCRIBERS);
        if(!Files.exists(dir))
        {
            try
            {
                Files.createDirectories(dir);
            }
            catch (IOException ex)
            {
                ex.getLocalizedMessage();
            }
        }

        if(!Files.exists(fullPath))
        {
            try{
                Files.createFile(fullPath);
            }
            catch (IOException ex)
            {
                ex.getLocalizedMessage();
            }
        }
        if(fullPath.toFile().length() == 0)
        {
            try
            {
            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(fullPath.toString())));
            fileWriter.println("TYPE_ID\t-\tTYPE\t-\tPROGRAM_ID\t-\tPROGRAM_NAME\t-\tPROGRAM_DURATION\t-\tPROGRAM_PRICE\t-\tUSER_ID\t-\tUSER_NAME\t-\tUSER_LOCATION\t-\tUSER_EMAIL\t-\tDATE");
            fileWriter.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }


    }

    @GetMapping("/find_all")
    public ResponseEntity<List<FitnessProgramEntity>> findAllFitnessPrograms()
    {
        List<FitnessProgramEntity> entityList = fitnessProgramService.findAllFitnessPrograms();
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/find_by_name/{name}")
    public ResponseEntity<List<FitnessProgramEntity>> findFitnessProgramsByName(@PathVariable("name") String name)
    {
        List<FitnessProgramEntity> entityList = fitnessProgramService.findFitnessProgramsByName(name);
        return new ResponseEntity<>(entityList, HttpStatus.OK);
    }

    @GetMapping("/find_with_filter")
    public ResponseEntity<List<FitnessProgramEntity>> findFitnessProgramsWithFilter(@RequestBody Object filterObject)
    {
        return new ResponseEntity<>(null, HttpStatus.OK); //TODO: MAKE A NEW CLASS FOR FILTER PARAMETERS AND IMPLEMENT STREAM API FILTERING HERE
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<FitnessProgramEntity> findFitnessProgramById(@PathVariable Integer id)
    {
        FitnessProgramEntity entity = fitnessProgramService.findFitnessProgramById(id);


        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<FitnessProgramEntity> addFitnessProgram(@RequestBody FitnessProgramEntity newEntity)
    {
        FitnessProgramEntity newProgramWithPhoto; //Can also be without photo if no photo was ever set
        try
        {
            String coverPhoto = newEntity.getImageUrl(); //Saves the full raw data into String avatar
            newEntity.setImageUrl(null); //sets the Avatar inside user to null so that it doesn't try to save the whole raw picture data to the database
            newProgramWithPhoto = fitnessProgramService.addFitnessProgram(newEntity); //saves to Database (without profile picture link)

            if(coverPhoto != null && !coverPhoto.isEmpty()) //checks if there is any Base64 data present.
            {
                newProgramWithPhoto.setImageUrl(fitnessProgramService.saveBase64EncodedPhoto(coverPhoto, newProgramWithPhoto));
                fitnessProgramService.updateFitnessProgram(newProgramWithPhoto); //updates the daTabase with the link to the uploaded photo
            }

            //WRITING THIS NEW PROGRAM TO THE TEXT FILE FOR SUBSCRIBERS -->

            fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(fullPath.toString(), true)));
            StringBuilder sb = new StringBuilder();
            sb.append(newProgramWithPhoto.getFitnessProgramType().getId());
            sb.append("#");
            sb.append(newProgramWithPhoto.getFitnessProgramType().getName());
            sb.append("#");
            sb.append(newProgramWithPhoto.getId());
            sb.append("#");
            sb.append(newProgramWithPhoto.getName());
            sb.append("#");
            sb.append(newProgramWithPhoto.getDuration());
            sb.append("#");
            sb.append(newProgramWithPhoto.getPrice());
            sb.append("#");
            sb.append(newProgramWithPhoto.getUser_creator().getId());
            sb.append("#");
            sb.append(newProgramWithPhoto.getUser_creator().getUsername());
            sb.append("#");
            sb.append(newProgramWithPhoto.getUser_creator().getCity());
            sb.append("#");
            sb.append(newProgramWithPhoto.getUser_creator().getEmail());
            sb.append("#");
            sb.append(LocalDateTime.now());

            fileWriter.println(sb.toString());
            fileWriter.close();
            //<-- WRITING CODE UNTIL HERE

        }
        catch(Exception ex)
        {
            System.out.println(ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(newProgramWithPhoto, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<FitnessProgramEntity> updateFitnessProgram(@RequestBody FitnessProgramEntity newEntity)
    {
        FitnessProgramEntity updatedEntity = fitnessProgramService.updateFitnessProgram(newEntity);

        return new ResponseEntity<>(updatedEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFitnessProgram(@PathVariable Integer id)
    {
        fitnessProgramService.deleteFitnessProgramById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Scheduled(fixedRate = 10_000)
    public void sendEmailsToSubscribers()
    {
        System.out.println("RUNNING MAILING METHOD AT: " + new Date());
        try(BufferedReader reader = new BufferedReader(new FileReader(fullPath.toFile())))
        {
            reader.readLine(); //SKIP THE FIRST LINE OF THE DOCUMENT
            String readLine = "";
            while((readLine = reader.readLine()) != null)
            {
                fitnessProgramService.sendToSubs(readLine); //Reads the newly added programs one by one and will send an email about them to all users
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
