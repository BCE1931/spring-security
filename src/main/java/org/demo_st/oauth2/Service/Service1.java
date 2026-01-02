package org.demo_st.oauth2.Service;

import org.demo_st.oauth2.ENTITY.Mytopcs;
import org.demo_st.oauth2.ENTITY.Mywork;
import org.demo_st.oauth2.ENTITY.Questions;
import org.demo_st.oauth2.REPO.Mytopicsrepo;
import org.demo_st.oauth2.REPO.Myworkrepo;
import org.demo_st.oauth2.REPO.Questionsrepo;
import org.demo_st.oauth2.REPO.Topicsrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class Service1 {
    @Autowired
    private Topicsrepo topicsrepo;

    @Autowired
    private Questionsrepo questionsrepo;

    @Autowired
    private Myworkrepo myworkrepo;

    @Autowired
    private Mytopicsrepo mytopicsrepo;

    public List<Mytopcs> gettopics(String topic, String email){
        List<Mytopcs> topics = mytopicsrepo.findByTopicAndEmail(topic,email);
        return topics;
    }

    public List<Questions> getquestions(String email){
        List<Questions> questions = questionsrepo.findByEmail(email);
        return questions;
    }

    public ResponseEntity<?> add_dsa_work(Mywork mywork){
        mywork.setDate(java.sql.Date.valueOf(LocalDate.now()));
        mywork.setWork(true);
        mywork.setTopic("DSA");
        myworkrepo.save(mywork);
        System.out.println(mywork);
        if(mytopicsrepo.existsByEmailAndTopicAndSubtopic(mywork.getEmail(),mywork.getTopic(),mywork.getSubtopic())){
            Mytopcs mytopcs = mytopicsrepo.findByEmailAndTopicAndSubtopic(mywork.getEmail(),mywork.getTopic(),mywork.getSubtopic());
            int num = mytopcs.getQues();
            mytopcs.setQues(num+1);
            mytopicsrepo.save(mytopcs);
        }
        else{
            Mytopcs mytopcs = new Mytopcs();
            mytopcs.setSubtopic(mywork.getSubtopic());
            mytopcs.setTopic(mywork.getTopic());
            mytopcs.setQues(1);
            mytopcs.setEmail(mywork.getEmail());
            mytopcs.setEmail(mywork.getEmail());
            mytopicsrepo.save(mytopcs);
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Work Added Successfully"));
    }

    public ResponseEntity<?> add_other_work(Mywork mywork) {
        mywork.setCode("");
        mywork.setAttempted(true);
        mywork.setWork(true);
        mywork.setSubtopic("");
        mywork.setDate(java.sql.Date.valueOf(LocalDate.now()));
        mywork.setLink("");
        System.out.println(mywork);
        myworkrepo.save(mywork);
        if(mytopicsrepo.existsByTopicAndEmail(mywork.getTopic(),mywork.getEmail())){
            List<Mytopcs> mytopcsList = mytopicsrepo.findByTopicAndEmail(mywork.getTopic(), mywork.getEmail());
            for (Mytopcs mytopcs : mytopcsList) {
                int num = mytopcs.getQues();
                mytopcs.setQues(num + 1);
                mytopicsrepo.save(mytopcs);
            }
        }
        else{
            Mytopcs mytopcs = new Mytopcs();
            mytopcs.setSubtopic("");
            mytopcs.setTopic(mywork.getTopic());
            mytopcs.setQues(1);
            mytopcs.setEmail(mywork.getEmail());
            mytopcs.setEmail(mywork.getEmail());
            mytopicsrepo.save(mytopcs);
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "Work Added Successfully"));
    }

    public ResponseEntity<?> get_dsa_work(String email) {
        List<Mywork> myworks = myworkrepo.findByEmailAndTopic(email,"DSA");
        return new ResponseEntity<>(myworks, HttpStatus.OK);
    }

    public ResponseEntity<?> get_other_work(String email, String topic) {
        List<Mywork> myworks = myworkrepo.findByEmailAndTopic(email,topic);
        return  new ResponseEntity<>(myworks,HttpStatus.OK);
    }

    public ResponseEntity<?> toggleattempt(String email, Long id) {
        List<Mywork> myworks = myworkrepo.findByEmailAndId(email,id);
        myworks.get(0).setAttempted(true);
        return ResponseEntity.ok(Collections.singletonMap("message","attempted"));
    }

    public ResponseEntity<?> modifydata(String email, Mywork mywork) {
        List<Mywork> myworks = myworkrepo.findByEmailAndId(email,mywork.getId());
        myworks.get(0).setQuestion(mywork.getQuestion());
        myworks.get(0).setQuestioninfo(mywork.getQuestioninfo());
        myworks.get(0).setImportant(mywork.isImportant());
        myworks.get(0).setLogic(mywork.getLogic());
//        myworks.get(0).setDate(mywork.getDate());
        myworks.get(0).setCode(mywork.getCode());
        myworks.get(0).setAttempted(mywork.isAttempted());
//        myworks.get(0).setWork(mywork.isWork());
//        myworks.get(0).setTopic(mywork.getTopic());
        myworks.get(0).setSubtopic(mywork.getSubtopic());
//        myworks.get(0).setUsername(username);
        myworks.get(0).setLink(mywork.getLink());
        myworkrepo.save(myworks.get(0));
        return ResponseEntity.ok(Collections.singletonMap("message","updated and saved in db"));
    }
}

