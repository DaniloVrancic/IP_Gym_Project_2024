package org.unibl.etf.onlinefitnessmanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.dynalink.linker.support.SimpleLinkRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.model.entities.RecommendedExcerciseEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.RecommendedExcerciseRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RecommendedExcerciseService {

    private RecommendedExcerciseRepository recommendedExcerciseRepository;


    private final String directory = "./todays_excercise/";
    @Autowired
    public RecommendedExcerciseService(RecommendedExcerciseRepository recommendedExcerciseRepository)
    {
        this.recommendedExcerciseRepository = recommendedExcerciseRepository;
    }

    public RecommendedExcerciseEntity findLatestExcercise()
    {
        return recommendedExcerciseRepository.findTopByOrderByDateUpdatedDesc().orElse(null);
    }

    public String findTodaysExcercise() throws IOException
    {
        String tempList = "";
        String todaysFileName = generateTodaysFileName();

        final String apiUrl = "https://api.api-ninjas.com/v1/exercises";

        String randomFilter = "";



       // if(tempList == null)
        //{
            if(!isDailyExcerciseUpdated())
            {
                double randomRange = Math.random();
                if(randomRange <= 0.20)
                {randomFilter = "?type=cardio";}
                else if(randomRange <= 0.40)
                {randomFilter = "?type=strength";}
                else if(randomRange <= 0.60)
                {randomFilter = "?type=stretching";}
                else if(randomRange <= 0.80)
                {randomFilter = "?type=plyometrics";}
                else{randomFilter = "";}


                URL url = new URL(apiUrl + randomFilter);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("X-Api-Key", "ZEt4k4Fc2SPWrhLiAI8OgQ==ZYdwJD0rymqAXQR5");

                InputStream responseStream = connection.getInputStream();


                File jsonFile = new File(todaysFileName);



                String json = new String(responseStream.readAllBytes());
                tempList = json;

                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(jsonFile)));
                writer.print(json);
                writer.close();
            }
            else
            {
                BufferedReader reader = new BufferedReader(new FileReader(new File(todaysFileName)));
                tempList = new String(Files.readAllBytes(Paths.get(todaysFileName)));
            }



       // }
        return tempList;
    }

    public boolean isDailyExcerciseUpdated()
    {
        if(Files.exists(Paths.get(generateTodaysFileName())))
        {
            return true; //if the file already exists, the daily excercise was already made
        }
        else
        {
            return false;
        }
    }

    private String generateTodaysFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_YYYY");
        String fileName = sdf.format(new Date()) + ".txt";

        return directory + fileName;
    }
}
