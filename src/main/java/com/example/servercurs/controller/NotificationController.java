package com.example.servercurs.controller;

import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.Student;
import com.example.servercurs.service.NotificationService;
import com.example.servercurs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final StudentService studentService;

    ///update-notifications
    @GetMapping("/update-notifications")
    public ResponseEntity<Integer> updateNotifications(@RequestParam int studentId) {
        Student student = studentService.findById(studentId);
        List<Notification> notificationList = notificationService.findById_userAndAndCheckNotificationTrue(student.getId_user().getId_user());
        for(Notification nt:notificationList){
            if(!nt.isCheckNotification()){
                nt.setCheckNotification(true);
                notificationService.save(nt);
            }
        }
        Integer unreadCount = notificationService.countUnreadNotifications(student.getId_user().getId_user());
        return ResponseEntity.ok(unreadCount);
       // notificationService.updateNotificationsForStudent(studentId);
    }
}
