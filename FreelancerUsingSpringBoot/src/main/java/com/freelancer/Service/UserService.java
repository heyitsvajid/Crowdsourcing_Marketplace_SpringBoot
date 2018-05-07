package com.freelancer.Service;

import com.freelancer.Controller.UserController;
import com.freelancer.Entity.User;
import com.freelancer.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findById(long id){
        Optional<User> exist = userRepository.findById(id);

        if(exist.isPresent()){
            return exist.get();
        }else{
            return null;
        }
    }

    public User updateUser(User user){
        User exist = userRepository.findByEmail(user.getEmail());
        if(exist!=null){
            logger.info(exist.toString());
            logger.info("User exists with same email....Check if the same user");
            if(exist.getId()==user.getId()){
             User updatedUser= userRepository.save(user);
                return updatedUser;
            }else{
                logger.error("Same email exist for other user");
                return null;
            }
        }else{
            logger.info("User does not exists with same email");
            logger.info("Updating User");
            User updatedUser=userRepository.save(user);
            return updatedUser;
        }
    }

    public User addUser(User user){
        User exist = userRepository.findByEmail(user.getEmail());

        if(exist!=null){
            logger.info("User exists with same email");
            logger.info(exist.toString());
            return null;
        }else{
            logger.info("User does not exists with same email");
            logger.info("Creating User");
            String hashedPass = new String(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setPassword(hashedPass);
            userRepository.save(user);
            return user;
        }
    }

    public User login(String email, String password){
        User exist = userRepository.findByEmail(email);
        if(exist!=null){
            logger.info("User exists with email...checking password");
            logger.info(exist.toString());
            if(bCryptPasswordEncoder.matches(password,exist.getPassword())){
                return exist;
            }
            else{
                logger.info("User name password incorrect");
                return null;
            }
        }else{
            logger.info("User does not exists given email");
            return null;
        }
    }
}