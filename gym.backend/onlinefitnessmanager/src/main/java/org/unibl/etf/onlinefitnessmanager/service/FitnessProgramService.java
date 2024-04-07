package org.unibl.etf.onlinefitnessmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.onlinefitnessmanager.additional.email.EmailSender;
import org.unibl.etf.onlinefitnessmanager.exception.FitnessProgramNotFoundException;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.FitnessProgramTypeEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.SubscriptionEntity;
import org.unibl.etf.onlinefitnessmanager.model.entities.UserEntity;
import org.unibl.etf.onlinefitnessmanager.repositories.FitnessProgramRepository;
import org.unibl.etf.onlinefitnessmanager.repositories.SubscriptionRepository;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FitnessProgramService {

    private final FitnessProgramRepository programRepository;
    private final EmailSender emailSender;
    private final SubscriptionService subscriptionService;
    private final FitnessProgramTypeService fitnessProgramTypeService;

    private final UserService userService;

    @Autowired
    public FitnessProgramService(FitnessProgramRepository programRepository,
                                 FitnessProgramTypeService fitnessProgramTypeService,
                                 UserService userService,
                                 SubscriptionService subscriptionService,
                                 EmailSender emailSender) {
            this.programRepository = programRepository;
            this.fitnessProgramTypeService = fitnessProgramTypeService;
            this.userService = userService;
            this.subscriptionService = subscriptionService;
            this.emailSender = emailSender;
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

    /**
     * Method that is specific only for file types created by this application, to read contents accordingly and send relevant info to subscribers
     * @param fullLine the full line of code including '#' symbol seperators
     */
    public void sendToSubs(String fullLine)
    {
        String[] tokens         =   fullLine.split("#");
        Integer typeId          =   Integer.parseInt(tokens[0]);
        String  type            =   tokens[1];
        Integer programId       =   Integer.parseInt(tokens[2]);
        String  programName     =   tokens[3];
        Integer programDuration =   Integer.parseInt(tokens[4]);
        Double  programPrice    =   Double.parseDouble(tokens[5]);
        Integer trainerId          =   Integer.parseInt(tokens[6]);
        String  trainerName        =   tokens[7];
        String  trainerLocation    =   tokens[8];
        String  trainerEmail       =   tokens[9];
        LocalDateTime dateTime  =   LocalDateTime.parse(tokens[10]);

        System.out.println("typeId: " + typeId);
        System.out.println("type: " + type);
        System.out.println("programId: " + programId);
        System.out.println("programName: " + programName);
        System.out.println("programDuration: " + programDuration);
        System.out.println("programPrice: " + programPrice);
        System.out.println("trainerId: " + trainerId);
        System.out.println("trainerName: " + trainerName);
        System.out.println("trainerLocation: " + trainerLocation);
        System.out.println("trainerEmail: " + trainerEmail);
        System.out.println("dateTime: " + dateTime);

        List<Integer> subscribersForCategory = subscriptionService.findAllByProgramTypeId(typeId)
                                               .stream().map(entry -> entry.getUserId()).distinct().collect(Collectors.toList()); //Finds the ID's of all users subscribed to this category

        String emailToSend = buildEmail(type,programName,trainerName,trainerEmail, trainerLocation,programPrice.toString(),programDuration.toString(),dateTime.toString());
        String subjectForEmail = "SUBSCRIBER NEWS: Category " + type + " just got a new fitness program!";
        String emailFrom = "danilo.vrancic@student.etf.unibl.org";
        for(int foundUserId : subscribersForCategory) //for each userId, mail them
        {
            UserEntity foundUserEntity = userService.findUserById(foundUserId);

            try
            {
                emailSender.send(foundUserEntity.getEmail(), emailToSend, subjectForEmail, emailFrom);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }
    private String buildEmail(String category, String programName, String trainerName, String trainerEmail, String location, String price, String duration, String date) {

        StringBuilder textContentBuilder = new StringBuilder();
        StringBuilder htmlContentBuilder = new StringBuilder();

        textContentBuilder.append("A new fitness program that might interest you has been announced, for the category ").append(category).append(".\n\n")
                .append("Fitness program name: ").append(programName).append("\n")
                .append("Hosted by: ").append(trainerName).append("\n")
                .append("E-mail: ").append(trainerEmail).append("\n")
                .append("Location of program: ").append(location).append("\n")
                .append("Duration: ").append(duration).append("\n")
                .append("Price of joining: ").append(price).append("\n")
                .append("The program was added on: ").append(date).append("\n")
                .append("For more information visit us at our website!");

        htmlContentBuilder.append("<body>")
                .append("<p>A new fitness program that might interest you has been announced, for the category: ").append(category).append("</p>")
                .append("<p>Fitness program name: ").append(programName).append("</p>")
                .append("<p>Hosted by: ").append(trainerName).append("</p>")
                .append("<p>E-mail: ").append(trainerEmail).append("</p>")
                .append("<p>Location of program: ").append(location).append("</p>")
                .append("<p>Duration: ").append(duration).append("</p>")
                .append("<p>Price of joining: ").append(price).append("</p>")
                .append("<p style='color:orange'>The program was added on: ").append(date).append("</p>")
                .append("<p style='color:red'>For more information visit us at our website!</p>")
                .append("</body>");

        // Construct a wrapper object to hold both text and HTML content
        return textContentBuilder.toString() + "|||" + htmlContentBuilder.toString();
    }



}
