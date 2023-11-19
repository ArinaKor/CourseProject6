package com.example.servercurs.repository;

import com.example.servercurs.entities.Language;
import com.example.servercurs.entities.Skills;
import com.example.servercurs.entities.TraineeReply;
import com.example.servercurs.entities.enums.EnglishLevel;
import com.example.servercurs.entities.enums.Location;
import com.example.servercurs.entities.enums.StatusReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraineeReplyRepository extends JpaRepository<TraineeReply, Integer> {

    @Query("SELECT t FROM TraineeReply t WHERE " +
            "t.surname = :surname AND " +
            "t.name = :name AND " +
            "t.location = :location AND " +
            "t.town = :town AND " +
            "t.dateBirth = :dateBirth AND " +
            "t.mail = :mail AND " +
            "t.englishLevel = :englishLevel AND " +
            "t.education = :education AND " +
            "t.statusReply = :statusReply AND " +
            "t.idSkills = :idSkills AND " +
            "t.idLanguage = :idLanguage")
    Optional<TraineeReply> findExactMatch(@Param("surname") String surname,
                                         @Param("name") String name,
                                         @Param("location") Location location,
                                         @Param("town") String town,
                                         @Param("dateBirth") String dateBirth,
                                         @Param("mail") String mail,
                                         @Param("englishLevel") EnglishLevel englishLevel,
                                         @Param("education") String education,
                                         @Param("statusReply") StatusReply statusReply,
                                         @Param("idSkills") Skills idSkills,
                                         @Param("idLanguage") Language idLanguage);

}
