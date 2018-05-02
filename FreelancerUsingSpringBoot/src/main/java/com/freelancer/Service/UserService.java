package com.freelancer.Service;

import com.freelancer.Entity.User;
import com.freelancer.Repository.UserRepository;
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

    public void addUser(User user){
        userRepository.save(user);
    }

    public List<User> login(String email, String password){
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        System.out.println(hashedPassword);
        return userRepository.findByEmailAndPassword(email,password );
    }
}