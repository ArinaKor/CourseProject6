package com.example.servercurs.service;

import com.example.servercurs.Config.GoogleConfig;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.entities.TraineeReply;
import com.example.servercurs.entities.enums.EnglishLevel;
import com.example.servercurs.entities.enums.Location;
import com.example.servercurs.entities.enums.StatusReply;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private int lastReadRow = 1;
    private final SkillsService skillsService;
    private final LanguageService languageService;
    private final TraineeService traineeService;
    private final TraineeReplyService traineeReplyService;
    private final EmailSenderService emailService;

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public List<List<Object>> checkSheets() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "15mB5chdY8CpAekBtOZ9zpwJwywGulF_MpD_UoMsxubs";
        final String range = "ответы!B2" +/*" + (lastReadRow + 1) + "*/":O";  // Начните чтение с последней прочитанной строки + 1
        Sheets service =
                new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleConfig.getCredentials(HTTP_TRANSPORT))
                        .setApplicationName(APPLICATION_NAME)
                        .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        // Обновите lastReadRow после чтения
        List<List<Object>> values = response.getValues();
        //lastReadRow += values.size();
        return values;
    }

    public void saveTraineeReply() throws GeneralSecurityException, IOException {
        List<List<Object>> values = checkSheets();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                TraineeReply traineeReply = new TraineeReply();
                traineeReply.setName(String.valueOf(row.get(0)));
                traineeReply.setSurname(String.valueOf(row.get(1)));
                traineeReply.setLocation(Location.valueOf(String.valueOf(row.get(2))));
                traineeReply.setTown(String.valueOf(row.get(3)));
                traineeReply.setDateBirth(String.valueOf(row.get(4)));
                traineeReply.setMail(String.valueOf(row.get(6)));
                traineeReply.setEnglishLevel(EnglishLevel.valueOf(String.valueOf(row.get(8))));
                traineeReply.setEducation(String.valueOf(row.get(9)));
                traineeReply.setIdSkills(skillsService.findSkillsByName_skills(String.valueOf(row.get(12))));
                traineeReply.setIdLanguage(languageService.findLanguageByName_language(String.valueOf(row.get(13))));
                traineeReply.setTestResult(0);
                traineeReply.setStatusReply(checkStatus(traineeReply.getIdLanguage().getId_language(), traineeReply.getIdSkills().getId_skills()));
                if (!traineeReplyService.findExactMatch(traineeReply.getSurname(),
                        traineeReply.getName(),
                        traineeReply.getLocation(),
                        traineeReply.getTown(),
                        traineeReply.getDateBirth(),
                        traineeReply.getMail(),
                        traineeReply.getEnglishLevel(),
                        traineeReply.getEducation(),
                        traineeReply.getStatusReply(),
                        traineeReply.getIdSkills(),
                        traineeReply.getIdLanguage()
                ).isPresent()) {
                    traineeReplyService.save(traineeReply);
                }
                if (traineeReply.getStatusReply().equals(StatusReply.REJECTED)) {
                    checkUnSuccess(traineeReply);
                }
                else if( traineeReply.getStatusReply().equals(StatusReply.REVIEW)) {
                    checkSuccess(traineeReply);
                }
            }
        }
    }



    private StatusReply checkStatus(int lang, int skill) {
        return checkTrainee(lang, skill) ? StatusReply.REJECTED : StatusReply.REVIEW;
    }

    private boolean checkTrainee(int lang, int skill) {
        List<Trainee> trainees = traineeService.findByLangAndSkill(lang, skill);
        return trainees.isEmpty();
    }

    private void checkUnSuccess(TraineeReply traineeReply) {
        String subject = "IT Company Education Courses";
        String text = "Добрый день," + traineeReply.getSurname() + " " + traineeReply.getName() + "!\n"
                + "К сожалению, мы не можем предложить прохождение стажировки по направлению " + traineeReply.getIdSkills().getName_skills()
                + " " + traineeReply.getIdLanguage().getName_language() + ", так как мы временно прекратили набор на данное направление," +
                " как только набор освободиться мы будем рады с вами связаться!";
        emailService.sendSimpleEmail(traineeReply.getMail(), subject, text);
    }

    private void checkSuccess(TraineeReply traineeReply) {
        //Добавить про тг бот!
        String subject = "IT Company Education Courses";
        String text = "Добрый день," + traineeReply.getSurname() + " " + traineeReply.getName() + "!\n"
                + "Поздравляем, Ваша заявка будет рассмотрена для прохождения стажировки по направлению " + traineeReply.getIdSkills().getName_skills()
                + " " + traineeReply.getIdLanguage().getName_language() + ", для прохождения стажировки вам необходимо пройти тест в телеграм боте на проверку уровня знаний по данному направлению," +
                ". Вся необходимая информация прилагается в данном письме, как только результаты будут получены и обработаны мы с вами свяжемся!\nСпасибо, что выбрали нас для вашего будущего!)";
        emailService.sendSimpleEmail(traineeReply.getMail(), subject, text);
    }
}
