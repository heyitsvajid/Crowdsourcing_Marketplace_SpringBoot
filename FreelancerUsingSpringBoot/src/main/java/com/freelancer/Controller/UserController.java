package com.freelancer.Controller;

import com.freelancer.Entity.User;
import com.freelancer.Service.UserService;
import com.freelancer.Utility.ResultObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller    // This means that this class is a Controller
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path="/add",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public  ResponseEntity<?> addNewUser (@RequestBody User user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        userService.addUser(user);
        System.out.println("Saved");
        return new ResponseEntity(null,HttpStatus.CREATED);
    }

    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON with the users
        return userService.getAllUsers();
    }

    @PostMapping(path="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String user, HttpSession session)
    {
        ResultObject ro = new ResultObject("Invalid username and password","",null);
        try{
            JSONObject jsonObject = new JSONObject(user);
            List<User> data = userService.login(jsonObject.getString("email"),jsonObject.getString("password"));
            if(data.size()==1){
                session.setAttribute("email",jsonObject.getString("email"));
                ro.setErrorMsg("");
                ro.setSuccessMsg("User found");
                ro.setData(data.get(0));
                return new ResponseEntity(ro,HttpStatus.OK);
            }else{
                ro.setErrorMsg("Username or password incorrect");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro,HttpStatus.OK);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(ro,HttpStatus.OK);
        }
    }

    @PostMapping(path="/getprofile",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getprofile(@RequestBody String user, HttpSession session)
    {
        JSONObject jsonObject = new JSONObject(user);
        session.setAttribute("name",jsonObject.getString("username"));
        User u = userService.findById(jsonObject.getLong("id"));
        if(u!=null){
            logger.info("User Found");
            return new ResponseEntity(u,HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> logout(HttpSession session) {
        logger.info("");
        System.out.println(session.getAttribute("name"));
        session.invalidate();
        return  new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/isLoggedIn")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> isLoggedIn(HttpSession session) {
        logger.info("Under /isLoggedIn Post Request");

        System.out.println(session.getAttribute("name"));
        if(session.getAttribute("name")!=null){
            return  new ResponseEntity(HttpStatus.OK);
        }else{
            return  new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}