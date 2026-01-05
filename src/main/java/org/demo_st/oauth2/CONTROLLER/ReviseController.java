package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.Daily_work;
import org.demo_st.oauth2.ENTITY.Mytopcs;
import org.demo_st.oauth2.ENTITY.Mywork;
import org.demo_st.oauth2.ENTITY.Questions;
import org.demo_st.oauth2.JWTTOKENS.Validatetoken;
import org.demo_st.oauth2.Service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviseController {
    @Autowired
    private Service1 service1;

    @Autowired
    private Validatetoken validatetoken;

    @GetMapping("/topics/{topic}")
    public ResponseEntity<?> topics(@PathVariable String topic, Authentication auth) {
        return ResponseEntity.ok(service1.gettopics(topic, auth.getName()));
    }

    @GetMapping("/questions") //USED TO FETCH QUESTIONS BASED ON EMAIL -> NOT USED DELETE IT
    public ResponseEntity<?> questions(Authentication auth) {
        return new ResponseEntity<>(service1.getquestions(auth.getName()), HttpStatus.OK);
    }

    @PostMapping("/adddsawork") // ONLY THING TO ADD DSA WORK
    public ResponseEntity<?> add_dsa_work(@RequestBody Mywork mywork, Authentication auth) {
        mywork.setEmail(auth.getName());
        return service1.add_dsa_work(mywork);
    }

    @PostMapping("/addotherwork") //TO ADD OTHER WORK NOT DSA
    public ResponseEntity<?> add_other_work(@RequestBody Mywork mywork, Authentication auth) {
        mywork.setEmail(auth.getName());
        return service1.add_other_work(mywork);
    }

    @GetMapping("/getdsawork") // MAIN FUNCTION TO GET ONLY DSA QUESTIONS
    public ResponseEntity<?> get_dsa_work(Authentication auth) {
        return service1.get_dsa_work(auth.getName());
    }

    @GetMapping("/getwork/{topic}") //GET WORK OTHER THAN DSA BASED ON TOPIC
    public ResponseEntity<?> get_other_work(@PathVariable String topic, Authentication auth) {
        return service1.get_other_work(auth.getName(), topic);
    }

    @GetMapping("/toglleattempt/{id}")
    public ResponseEntity<?> toglle_attempt(@PathVariable Long id, Authentication auth) {
        return service1.toggleattempt(auth.getName(), id);
    }

    @PostMapping("/modify") // TP MODIFY THE THING
    public ResponseEntity<?> modify(@RequestBody Mywork mywork, Authentication auth) {
        return service1.modifydata(auth.getName(), mywork);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Mywork mywork, Authentication auth) {
        return service1.delete(auth.getName(), mywork);
    }

    @PostMapping("/getonequestion")
    public ResponseEntity<?> get_one_question(@RequestBody Mywork mywork, Authentication auth) {
        return service1.get_one_question(mywork, auth.getName());
    }

    @PutMapping("/togglerevise")
    public ResponseEntity<?> toggle_revise(Authentication auth, @RequestBody Mywork mywork) {
        return service1.toggle_revise(mywork, auth.getName());
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> get_All(Authentication auth) {
        return service1.get_All(auth.getName());
    }

}


