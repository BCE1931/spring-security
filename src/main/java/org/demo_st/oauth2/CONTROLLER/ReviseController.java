package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.Mytopcs;
import org.demo_st.oauth2.ENTITY.Mywork;
import org.demo_st.oauth2.ENTITY.Questions;
import org.demo_st.oauth2.JWTTOKENS.Validatetoken;
import org.demo_st.oauth2.Service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviseController {
    @Autowired
    private Service1 service1;

    @Autowired
    private Validatetoken validatetoken;

    @GetMapping("/topics/{topic}")  //GET SIDE BAR WORK TOPIC
    public ResponseEntity<?> topics(@PathVariable String topic, @RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        List<Mytopcs> topics = service1.gettopics(topic,validatetoken.extractEmail(authheader.substring(7)));
        return new ResponseEntity(topics, HttpStatus.OK);
    }

    @GetMapping("/questions") //USED TO FETCH QUESTIONS BASED ON EMAIL -> NOT USED DELETE IT
    public ResponseEntity<?> questions(@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        List<Questions> questions = service1.getquestions(validatetoken.extractEmail(authheader.substring(7)));
        return new ResponseEntity(questions,HttpStatus.OK);
    }

    @PostMapping("/adddsawork") // ONLY THING TO ADD DSA WORK
    public ResponseEntity<?> add_dsa_work(@RequestBody Mywork mywork, @RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        mywork.setEmail(validatetoken.extractEmail(authheader.substring(7)));
        return service1.add_dsa_work(mywork);
    }

    @PostMapping("/addotherwork") //TO ADD OTHER WORK NOT DSA
    public ResponseEntity<?> add_other_work(@RequestBody Mywork mywork,@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        mywork.setEmail(validatetoken.extractEmail(authheader.substring(7)));
        return service1.add_other_work(mywork);
    }

    @GetMapping("/getdsawork") // MAIN FUNCTION TO GET ONLY DSA QUESTIONS
    public ResponseEntity<?> get_dsa_work(@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        return service1.get_dsa_work(validatetoken.extractEmail(authheader.substring(7)));
    }

    @GetMapping("/getwork/{topic}") //GET WORK OTHER THAN DSA BASED ON TOPIC
    public ResponseEntity<?> get_other_work(@PathVariable String topic,@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        return service1.get_other_work(validatetoken.extractEmail(authheader.substring(7)),topic);
    }

//    @GetMapping("/toggleattempt")

    @GetMapping("/toglleattempt/{id}")
    public ResponseEntity<?> toglle_attempt(@PathVariable Long id,@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        return service1.toggleattempt(validatetoken.extractEmail(authheader.substring(7)),id);
    }

    @PostMapping("/modify") // TP MODIFY THE THING
    public ResponseEntity<?> modify(@RequestBody Mywork mywork,@RequestHeader("Authorization") String authheader){
        if(!validatetoken.validate(authheader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session expired please try again");
        }
        return service1.modifydata(validatetoken.extractEmail(authheader.substring(7)),mywork);
    }
}
