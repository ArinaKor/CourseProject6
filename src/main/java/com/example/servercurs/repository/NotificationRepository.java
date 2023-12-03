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

    @Query(" from Notification n where n.id_user.id_user=:user")
    List<Notification> findById_userAndAndCheckNotificationTrue(@Param("user") int user);

    @Query("SELECT count(n) FROM Notification n WHERE n.checkNotification = false and n.id_user.id_user=:user")
    Integer countUnreadNotifications(@Param("user") int user);
}
