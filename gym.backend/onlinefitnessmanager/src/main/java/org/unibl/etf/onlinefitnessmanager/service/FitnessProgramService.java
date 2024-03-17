package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.FitnessProgramRepository;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FitnessProgramService {

    private final FitnessProgramRepository programRepository;

    @Autowired
    public FitnessProgramService(FitnessProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public FitnessProgramEntity addFitnessProgram(FitnessProgramEntity entity)
    {
        return this.programRepository.save(entity);
    }

    public FitnessProgramEntity updateFitnessProgram(FitnessProgramEntity entity)
    {
        return this.programRepository.save(entity);
    }

    public List<FitnessProgramEntity> findAllFitnessPrograms()
    {
        return this.programRepository.findAll();
    }

    public List<FitnessProgramEntity> findFitnessProgramsByName(String name)
    {
        return this.programRepository.findAll().stream()
                                               .filter(x -> x.getName().contains(name))
                                               .collect(Collectors.toList());
    }

    public FitnessProgramEntity findFitnessProgramById(Integer id)
    {
        return this.programRepository.findById(id).orElseThrow(() -> new FitnessProgramNotFoundException());
    }

    public void deleteFitnessProgramById(Integer id)
    {
        this.programRepository.deleteById(id);
    }

    public String saveBase64EncodedPhoto(String base64encodedPhoto, FitnessProgramEntity fitness_program) {
        String[] photoParts = base64encodedPhoto.split(",");
        String metadataAboutEncode = photoParts[0];
        String payload = photoParts[1];

        String regex = "image/(\\w*)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(metadataAboutEncode);

        String photoType;

        if (matcher.find()) {
            photoType = matcher.group(1); //sticks the found type of photo on the end of the file name (ex. .jpeg, .png...)
        } else {
            photoType = "";
        }

        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);

        Path directoryPath = Paths.get(".//fitness_programs//" + fitness_program.getId());
        Path outputPath = directoryPath.resolve("coverPhoto" + ((photoType.length() > 0) ? "." + photoType : photoType));

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


}
