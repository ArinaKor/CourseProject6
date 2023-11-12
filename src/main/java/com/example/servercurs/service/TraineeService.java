package com.example.servercurs.service;

import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.Student;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.entities.User;
import com.example.servercurs.entities.enums.TraineeType;
import com.example.servercurs.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TraineeService {

    private final TraineeRepository traineeRepository;
    private final LanguageService languageService;
    private final SkillsService skillsService;
    private final StudentService studentService;
    private final NotificationService notificationService;
    private final UserService userService;
    private final EmailSenderService emailService;

    public List<Trainee> findAll() {
        return traineeRepository.findAll();
    }

    public Trainee findById(int id) {
        return traineeRepository.findById(id).orElse(null);
    }

    public Trainee save(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public void delete(int id) {
        traineeRepository.deleteById(id);
    }

    public List<Trainee> findWithAll() {
        return traineeRepository.findWithAll();
    }

    public void saveCourse(String location, TraineeType type, String skills, String lang) {
        Trainee trainee = new Trainee();
        trainee.setDuration(6);
        trainee.setId_language(languageService.findLanguageByName_language(lang));
        trainee.setId_skills(skillsService.findSkillsByName_skills(skills));
        trainee.setLocation(location);
        trainee.setType(type);
        traineeRepository.save(trainee);
    }

    public void update(int traineeId, String location, TraineeType type, String skills, String lang, int duration) {
        Trainee trainee = traineeRepository.findById(traineeId).get();
        trainee.setId_language(languageService.findLanguageByName_language(lang));
        trainee.setId_skills(skillsService.findSkillsByName_skills(skills));
        trainee.setLocation(location);
        trainee.setType(type);
        trainee.setDuration(duration);
        traineeRepository.save(trainee);
    }

    public void replyTrainee(int traineeId, int studentId) {

        Student student = studentService.findById(studentId);
        User user = userService.findById(student.getId_user().getId_user());
        Trainee trainee = traineeRepository.findById(traineeId).get();
        String text = "Спасибо за отклик на данную стажировку. ";
        String subject = "IT Company Education Courses";
        //create notification
        createNotificationForReplyOnTrainee(user, text, subject);

        //send mail
        sendMailForReplyOnTrainee(student, text, subject);
    }

    private void createNotificationForReplyOnTrainee(User user, String text, String subject) {
        Notification notification = new Notification();
        notification.setId_user(user);
        notification.setCheckNotification(false);
        notification.setTextNotification(text);
        notification.setSubject(subject);
        notificationService.save(notification);
    }

    private void sendMailForReplyOnTrainee(Student student, String body, String subject) {
        String mail = student.getId_user().getMail();
        String text = body + "\n Для дальнейшего рассмотрения Вашей кандидатуры, заполните форму по ссылке https://forms.gle/aLHaxTwiySM5WFes6" +
                "\nСпасибо, что выбрали нашу компанию для вашего будущего";
        emailService.sendSimpleEmail(mail, subject, text);
    }
}
