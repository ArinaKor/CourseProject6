package com.example.servercurs.service;

import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.Trainee;
import com.example.servercurs.entities.User;
import com.example.servercurs.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification findById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void delete(int id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> findById_userAndAndCheckNotificationTrue(int user){
        return notificationRepository.findById_userAndAndCheckNotificationTrue(user);
    }
    public Integer countUnreadNotifications(int user){
        return notificationRepository.countUnreadNotifications(user);
    }
}
