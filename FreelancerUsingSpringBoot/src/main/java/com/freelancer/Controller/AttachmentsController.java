package com.freelancer.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.freelancer.Entity.Attachments;
import com.freelancer.Entity.Project;
import com.freelancer.Entity.User;
import com.freelancer.Service.AttachmentsService;
import com.freelancer.Service.ProjectService;
import com.freelancer.Service.UserService;
import com.freelancer.Utility.ResultObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class AttachmentsController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AttachmentsService attachmentsService;
    @Autowired
    private ProjectService projectService;


    @RequestMapping(value="/imageUpload")
    public String singleUpload(){
        return "singleUpload";
    }


    @RequestMapping(value="/getProjectDocument", method=RequestMethod.POST )
    public ResponseEntity<?> getProjectDocument(@RequestBody String user){
        logger.info("Inside getProjectDocument " + user) ;
        JSONObject jsonObject = new JSONObject(user);
        ResultObject ro = new ResultObject("Error Fetching Document","",null);
        try{
            Attachments attachments = attachmentsService.findById(jsonObject.getLong("id"));
            ro.setSuccessMsg("Image uploaded Successfully");
            ro.setErrorMsg("");
            ro.setData(attachments);
            return new ResponseEntity(ro, HttpStatus.OK);
        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("File not found" + e.getMessage());
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value="/imageUpload", method=RequestMethod.POST )
    public ResponseEntity<?> singleSave(@RequestParam("file") MultipartFile file, @RequestParam("id") int id ){
        System.out.println("File Description:"+id);
        String fileName = null;
        ResultObject ro = new ResultObject("Error Uploading Image","",null);
        if (!file.isEmpty()) {
            try {
                fileName = new Date().getTime()+file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File("../react-client/src/images/" + fileName)));
                buffStream.write(bytes);
                buffStream.close();
                logger.info("Image saved Successfully " + fileName) ;

                Attachments attachments = attachmentsService.save(new Attachments(fileName));
                User user = userService.findById(id);
                user.setProfile_id(attachments.getId());
                userService.updateUser(user);
                ro.setSuccessMsg("Image uploaded Successfully");
                ro.setErrorMsg("");
                return new ResponseEntity(ro, HttpStatus.OK);
            } catch (Exception e) {
                ro.setErrorMsg("Failed to upload " + fileName + ": " + e.getMessage());
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);

            }
        } else {
            ro.setErrorMsg("Unable to upload. File is empty.");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/postProject", method=RequestMethod.POST )
    public ResponseEntity<?> postProject(@RequestParam("file") MultipartFile file, @RequestParam String title,
                                         @RequestParam String description,@RequestParam String skill,
                                         @RequestParam Long id,@RequestParam String period,@RequestParam String budget){
        String fileName = null;
        ResultObject ro = new ResultObject("Error Posting Project","",null);

        if (!file.isEmpty()) {
            try {
                fileName = new Date().getTime()+file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File("../react-client/src/files/" + fileName)));
                buffStream.write(bytes);
                buffStream.close();
                logger.info("File Copied Successfully" + fileName) ;
                Attachments attachments = attachmentsService.save(new Attachments(fileName));
                if(attachments.getId()!=null){
                    logger.info("File Saved Successfully" + fileName) ;

                    Project project = projectService.save(new Project(id,title,description,skill,budget,period,attachments.getId()));
                    if(project.getId()!=null){
                        ro.setSuccessMsg("Project Posted Successfully");
                        ro.setErrorMsg("");
                        return new ResponseEntity(ro, HttpStatus.OK);
                    }
                    else{
                        ro.setSuccessMsg("");
                        ro.setErrorMsg("Error Posting Project");
                        return new ResponseEntity(ro, HttpStatus.OK);
                    }

                }else{
                    ro.setSuccessMsg("");
                    ro.setErrorMsg("Error Saving File");
                    return new ResponseEntity(ro, HttpStatus.OK);
                }
            } catch (Exception e) {
                ro.setErrorMsg("Failed to upload " + fileName + ": " + e.getMessage());
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }
        } else {
            ro.setErrorMsg("File is empty.");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }




    @RequestMapping(value="/multipleUpload")
    public String multiUpload(){
        return "multipleUpload";
    }
    @RequestMapping(value="/multipleSave", method=RequestMethod.POST )
    public @ResponseBody String multipleSave(@RequestParam("file") MultipartFile[] files){
        String fileName = null;
        String msg = "";
        if (files != null && files.length >0) {
            for(int i =0 ;i< files.length; i++){
                try {
                    fileName = files[i].getOriginalFilename();
                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(new File("F:/cp/" + fileName)));
                    buffStream.write(bytes);
                    buffStream.close();
                    msg += "You have successfully uploaded " + fileName +"<br/>";
                } catch (Exception e) {
                    return "You failed to upload " + fileName + ": " + e.getMessage() +"<br/>";
                }
            }
            return msg;
        } else {
            return "Unable to upload. File is empty.";
        }
    }
}