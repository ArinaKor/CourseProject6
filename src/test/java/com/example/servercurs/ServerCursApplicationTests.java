package com.example.servercurs;

import com.example.servercurs.entities.User;
import com.example.servercurs.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ServerCursApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }
    @Test
    public void testAddUser(){
        List<User> list = userService.findAllUser();
        User user = new User();
        user.setMail("123@mail.ru");
        String password = "16Arin@012003";
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        user.setPassword(hashedPassword);
        List<User> users = userService.findAllUser();
        for(int i=0;i<users.size();i++){
            if(!(users.get(i).getMail().equals(user.getMail()))){
                userService.save(users.get(i));
            }
        }
        List<User> after = userService.findAllUser();
        Assert.assertEquals(after.size(),list.size());

    }
    @Test
    public void testCheckBalance(){
        List<User> list = userService.findAllUser();
        User user = new User();
        user.setMail("1234@mail.ru");
        String password = "16Arin@012003";
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);
        user.setPassword(hashedPassword);
        List<User> users = userService.findAllUser();
        for(int i=0;i<users.size();i++){
            if(!(users.get(i).getMail().equals(user.getMail()))){
                userService.save(users.get(i));
            }
        }
        List<User> after = userService.findAllUser();
        Assert.assertEquals(after.size(),list.size());

    }

}
