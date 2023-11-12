package com.example.servercurs.repository;

import com.example.servercurs.entities.Notification;
import com.example.servercurs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(" from Notification n where n.checkNotification=false")
    List<Notification> findById_userAndAndCheckNotificationTrue();
}
