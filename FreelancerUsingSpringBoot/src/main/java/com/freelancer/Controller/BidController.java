package com.freelancer.Controller;

import com.freelancer.Entity.Attachments;
import com.freelancer.Entity.Bid;
import com.freelancer.Entity.User;
import com.freelancer.POJO.BidDetail;
import com.freelancer.Service.AttachmentsService;
import com.freelancer.Service.BidService;
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
import java.util.ArrayList;
import java.util.List;


@Controller    // This means that this class is a Controller
//@CrossOrigin(origins = "http://localhost:3000")
//@RequestMapping(path="/user") // This means URL's start with /demo (after Application path)
public class BidController {
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);


    @GetMapping(path = "/allBids", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Bid> getAllBids() {
        // This returns a JSON with the users
        return bidService.getAllBids();
    }


    @PostMapping(path = "/postBid", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postBid(@RequestBody Bid bid, HttpSession session) {
        ResultObject ro = new ResultObject("Error adding bid", "", null);
        try {
            bid.setBid_status("BID_SENT");

            if(bid.getId()!=null && bid.getId()==0){
                ro.setSuccessMsg("Bid Posted");
            }
            else{
                ro.setSuccessMsg("Bid Updated");
            }
            Bid b = bidService.save(bid);
            if (b != null) {
                logger.info(b.toString());
                ro.setErrorMsg("");
                if (bid.getId() != null) {
                    logger.info("Bid Updated");
                    ro.setSuccessMsg("Bid Updated");
                } else {
                    logger.info("Bid Added");
                    ro.setSuccessMsg("Bid Added");
                }
                ro.setData(b);
                return new ResponseEntity(ro, HttpStatus.OK);
            }else{
                return new ResponseEntity(ro, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);
        }
    }


    @PostMapping(path = "/getProjectBids/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkBid(@PathVariable(value = "id")  long id, HttpSession session) {
        ResultObject ro = new ResultObject("Error fetching bid", "", null);
        try {
            List<Bid> b = bidService.findByProjectId(id);
            List<BidDetail> bidDetail = new ArrayList<>();

            if (b.size() > 0) {
                logger.info("Bids Found");
                ro.setErrorMsg("");
                ro.setSuccessMsg("Bids Found");

                for (Bid bid:b) {
                    User u = userService.findById(bid.getUserId());
                    bidDetail.add(new BidDetail(bid.getId(),bid.getUserId(),bid.getProjectId(),bid.getBid_period(),bid.getBid_amount(),
                            bid.getBid_status(),u.getName()));
                }
                ro.setData(bidDetail);
                return new ResponseEntity(ro, HttpStatus.OK);
            } else {
                logger.info("No Bids found");
                ro.setSuccessMsg("");
                ro.setErrorMsg("No Bids found");
                return new ResponseEntity(ro, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(ro, HttpStatus.OK);
        }
    }
}