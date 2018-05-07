package com.freelancer.Controller;

import com.freelancer.Entity.Bid;
import com.freelancer.Entity.Project;
import com.freelancer.Entity.User;
import com.freelancer.Service.BidService;
import com.freelancer.Service.ObjectService;
import com.freelancer.Service.ProjectService;
import com.freelancer.Utility.ResultObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller    // This means that this class is a Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    BidService bidService;
    @Autowired
    private ObjectService objectService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
    @GetMapping(path = "/allProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Project> getAllProjects() {
        // This returns a JSON with the users
        return projectService.getAllProjects();
    }

    @PostMapping(path = "/getProjectById", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectById(@RequestBody String project, HttpSession session) {
        ResultObject ro = new ResultObject("Project not found", "", null);

        JSONObject jsonObject = new JSONObject(project);
        Project p = projectService.findById(jsonObject.getLong("id"));
        if (p != null) {
            ro.setErrorMsg("");
            ro.setSuccessMsg("Project found");
            ro.setData(p);
            logger.info("Project Found");
            return new ResponseEntity(ro, HttpStatus.OK);
        } else {
            ro.setErrorMsg("Project not found");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(path = "/hireFreelancer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hireFreelancer(@RequestBody String hireDetails, HttpSession session) {
        ResultObject ro = new ResultObject("Error Hiring Freelancer", "", null);

        JSONObject jsonObject = new JSONObject(hireDetails);
        try{
            long freelancer_id = jsonObject.getLong("freelancerId");
            Project p = projectService.findById(jsonObject.getLong("projectId"));
            if (p != null) {
                p.setEndDate(jsonObject.getString("endDate"));
                p.setFreelancerId(freelancer_id);
                projectService.save(p);
                logger.info("Project Details Updated");
                logger.info("Updating All Bids");

                List<Bid> bids = bidService.findByProjectId(jsonObject.getLong("projectId"));
                for (Bid bid:bids) {
                    logger.info("Updating Bid :"+ bid.toString());
                    if(bid.getUserId()==freelancer_id){
                        bid.setBid_status("BID_ACCEPTED");
                    }else{
                        bid.setBid_status("BID_REJECTED");
                    }
                    bidService.save(bid);
                }
                ro.setErrorMsg("");
                ro.setSuccessMsg("Freelancer detail added to project");
                ro.setData(p);
                logger.info("Freelancer Hire Update");
                return new ResponseEntity(ro, HttpStatus.OK);

            } else {
                ro.setErrorMsg("Project not found");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("Error Hiring Freelancer");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(path = "/getOpenProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOpenProjects(@RequestBody String userDetails, HttpSession session) {
        // This returns a JSON with the users
        logger.info("Under : getOpenProjects");
        ResultObject ro = new ResultObject("Error Fetching Open Projects", "", null);
        try{
            JSONObject jsonObject = new JSONObject(userDetails);
            if(jsonObject.has("id")){
                List data =  objectService.getOpenProjects(Long.parseLong(jsonObject.getString("id")));
                ro.setErrorMsg("");
                ro.setSuccessMsg("Open Projects Fetched");
                ro.setData(data);
                logger.info("Open Projects Fetched");
                return new ResponseEntity(ro, HttpStatus.OK);
            }else{
                ro.setErrorMsg("No id provided");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("Error Fetching Open Projects");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/getProjectDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProjectDetail(@RequestBody String projectDetails, HttpSession session) {
        // This returns a JSON with the users
        ResultObject ro = new ResultObject("Error Fetching Project Detail", "", null);
        try{
            JSONObject jsonObject = new JSONObject(projectDetails);
            if(jsonObject.has("id")){
                List data =  objectService.getProjectDetail(Long.parseLong(jsonObject.getString("id")));
                ro.setErrorMsg("");
                ro.setSuccessMsg("Project Detail Fetched");
                ro.setData(data);
                logger.info("Open Projects Fetched");
                return new ResponseEntity(ro, HttpStatus.OK);
            }else{
                ro.setErrorMsg("No id provided");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("Error Fetching Project Detail");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path = "/getUserProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserProjects(@RequestBody String userDetails, HttpSession session) {
        // This returns a JSON with the users
        ResultObject ro = new ResultObject("Error Fetching User Projects", "", null);
        try{
            JSONObject jsonObject = new JSONObject(userDetails);
            if(jsonObject.has("id")){
                List openData =  objectService.getUserOpenProjects(Long.parseLong(jsonObject.getString("id")));
                List progressData =  objectService.getUserProgressProjects(Long.parseLong(jsonObject.getString("id")));
                Object data[] = {openData, progressData};
                ro.setErrorMsg("");
                ro.setSuccessMsg("User Projects Fetched");
                ro.setData(data);
                logger.info("User Projects Fetched");
                return new ResponseEntity(ro, HttpStatus.OK);
            }else{
                ro.setErrorMsg("No id provided");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("Error Fetching User Projects");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/getUserBidProjects", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserBidProjects(@RequestBody String userDetails, HttpSession session) {
        // This returns a JSON with the users
        ResultObject ro = new ResultObject("Error Fetching User Projects", "", null);
        try{
            JSONObject jsonObject = new JSONObject(userDetails);
            if(jsonObject.has("id")){
                List data =  objectService.getUserBidProjects(Long.parseLong(jsonObject.getString("id")));
                ro.setErrorMsg("");
                ro.setSuccessMsg("User Projects Fetched");
                ro.setData(data);
                logger.info("User Projects Fetched");
                return new ResponseEntity(ro, HttpStatus.OK);
            }else{
                ro.setErrorMsg("No id provided");
                ro.setSuccessMsg("");
                return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            ro.setErrorMsg("Error Fetching User Projects");
            ro.setSuccessMsg("");
            return new ResponseEntity(ro, HttpStatus.BAD_REQUEST);
        }
    }

}


