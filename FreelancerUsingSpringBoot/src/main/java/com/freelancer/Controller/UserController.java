package com.freelancer.Controller;

import com.freelancer.Entity.Attachments;
import com.freelancer.Entity.User;
import com.freelancer.Service.AttachmentsService;
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
import javax.servlet.http.HttpSession;

@Controller
// This means that this class is a Controller
//@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class UserController {
    @Autowired
    private AttachmentsService attachmentsService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public ResponseEntity<?> addNewUser(@RequestBody User user, HttpSession session) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        ResultObject ro = new ResultObject("Email allready Exists", "", null);
        try {
            User u = userService.addUser(user);
            if (u != null) {
                logger.info("User Created");
                logger.info(u.toString());
                session.setAttribute("email", user.getEmail());
                session.setAttribute("name", user.getName());
                session.setAttribute("id", user.getId());
                ro.setErrorMsg("");
                ro.setSuccessMsg("Signup Success");
                ro.setData(u);
                return new ResponseEntity(ro, HttpStatus.OK);
            } else {
                logger.info("User allready exists");
                ro.setErrorMsg("Email allready Exists");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.OK);
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<User> getAllUsers() {
        // This returns a JSON with the users
        return userService.getAllUsers();
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String user, HttpSession session) {
        ResultObject ro = new ResultObject("Invalid username & password", "", null);
        try {
            JSONObject jsonObject = new JSONObject(user);
            User data = userService.login(jsonObject.getString("email"), jsonObject.getString("password"));
            if (data != null) {
                session.setAttribute("email", data.getEmail());
                session.setAttribute("name", data.getName());
                session.setAttribute("id", data.getId());
                ro.setErrorMsg("");
                ro.setSuccessMsg("User found...Credentials Valid");
                ro.setData(data);
                return new ResponseEntity(ro, HttpStatus.OK);
            } else {
                ro.setErrorMsg("Username or password incorrect");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);
        }
    }

    @PostMapping(path = "/getprofile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getprofile(@RequestBody String user, HttpSession session) {
        ResultObject ro = new ResultObject("User not found", "", null);

        JSONObject jsonObject = new JSONObject(user);
        User u = userService.findById(jsonObject.getInt("id"));
        if (u != null) {
            ro.setErrorMsg("");
            ro.setSuccessMsg("User found");
            ro.setData(u);
            logger.info("User Found");
            return new ResponseEntity(ro, HttpStatus.OK);
        } else {
            ro.setErrorMsg("UUser not found");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/getprofileImage", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getprofileImage(@RequestBody User user, HttpSession session) {
        JSONObject jsonObject = new JSONObject(user);
        ResultObject ro = new ResultObject("Error fetching image", "", null);
        User u = userService.findById(jsonObject.getLong("id"));
        if (u != null) {
            logger.info("getprofileImage User Found");
            if(u.getProfile_id()!=null){
                Attachments attachments = attachmentsService.findById(u.getProfile_id());
                if(attachments!=null){
                    ro.setErrorMsg("");
                    ro.setSuccessMsg("getprofileImage Image Found");
                    ro.setData(attachments.getLink());
                    return new ResponseEntity(ro, HttpStatus.OK);
                }
                else{
                    ro.setErrorMsg("getprofileImage Attachment not found");
                    return new ResponseEntity(ro, HttpStatus.NOT_FOUND);}
            }else{
                ro.setErrorMsg("No profile image available");
                return new ResponseEntity(ro, HttpStatus.NOT_FOUND);
            }

        } else {
            ro.setErrorMsg("User Not found");
            return new ResponseEntity(ro, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> logout(HttpSession session) {
        logger.info("");
        System.out.println(session.getAttribute("name"));
        session.removeAttribute("name");
        session.removeAttribute("email");
        session.removeAttribute("id");
        session.invalidate();

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/isLoggedIn")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> isLoggedIn(HttpSession session) {
        ResultObject ro = new ResultObject("Session Not Valid", "", null);
        logger.info("Under /isLoggedIn Post Request");
        logger.info(session.getAttribute("email")+"");

        if (session.getAttribute("email") != null) {
            ro.setErrorMsg("");
            ro.setSuccessMsg("User Allready Logged in");
            ro.setData(userService.findById(Long.parseLong(session.getAttribute("id")+"")));
            return new ResponseEntity(ro, HttpStatus.OK);
            } else {
            ro.setErrorMsg("Not Logged In");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro,HttpStatus.OK);
        }
    }

    @PostMapping(path = "/updateProfile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateprofile(@RequestBody String user, HttpSession session) {
        ResultObject ro = new ResultObject("Error updating user", "", null);
        try {
            JSONObject jsonObject = new JSONObject(user);
            if(!jsonObject.has("id")){
                ro.setErrorMsg("Please provide Id to update");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.OK);
            }
            User u = userService.findById(jsonObject.getLong("id"));
            if (u != null) {
                logger.info("User Found");
                if(jsonObject.has("name")){
                    u.setName(jsonObject.getString("name"));
                }
                if(jsonObject.has("email")){
                    u.setEmail(jsonObject.getString("email"));
                }
                if(jsonObject.has("about")){
                    u.setAbout(jsonObject.getString("about"));
                }
                if(jsonObject.has("phone")){
                    u.setPhone(jsonObject.getLong("phone"));
                }
                if(jsonObject.has("skills")){
                    u.setSkills(jsonObject.getString("skills"));
                }
                User updatedUser = userService.updateUser(u);
                if (updatedUser  != null) {
                    logger.info("User Updated");
                    logger.info(u.toString());
                    session.setAttribute("email", updatedUser.getEmail());
                    ro.setErrorMsg("");
                    ro.setSuccessMsg("Update Success");
                    ro.setData(u);
                    return new ResponseEntity(ro, HttpStatus.OK);
                } else {
                    logger.info("Email allready Exist");
                    ro.setErrorMsg("Email allready Exists");
                    ro.setSuccessMsg("");
                    return new ResponseEntity(ro, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);

        }

    }


}