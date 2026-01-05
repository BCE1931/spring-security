package org.demo_st.oauth2.Service;

import jakarta.transaction.Transactional;
import org.demo_st.oauth2.ENTITY.*;
import org.demo_st.oauth2.REPO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Autowired
    private Daily_work_repo daily_work_repo;

    @Autowired
    private LevelsRepo levelsRepo;

    @Autowired
    private Revise_topics_repo revise_topics_repo;

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
        Mywork mywork1 = myworkrepo.save(mywork);
        Daily_work dailyWork = new Daily_work();
        dailyWork.setDate(LocalDate.now());
        dailyWork.setTopic("DSA");
        String workname = "hey you added DSA on" + mywork.getSubtopic();
        dailyWork.setWorkname(workname);
        dailyWork.setEmail(mywork.getEmail());
        dailyWork.setQuesid(mywork1.getId());
        daily_work_repo.save(dailyWork);
        System.out.println(mywork);
        System.out.println("adding difficulty level : " + AddToLevel(mywork.getEmail(), mywork.getLevel()));
        if(mywork.getImportant()){
            ReviseTopics reviseTopics = new ReviseTopics();
            reviseTopics.setQuestioninfo(mywork.getQuestioninfo());
            reviseTopics.setEmail(mywork.getEmail());
            reviseTopics.setTopic(mywork.getTopic());
            reviseTopics.setQuesId(mywork1.getId());
            revise_topics_repo.save(reviseTopics);
        }
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
        Mywork mywork1 = myworkrepo.save(mywork);
        Daily_work dailyWork = new Daily_work();
        dailyWork.setEmail(mywork.getEmail());
        dailyWork.setDate(LocalDate.now());
        dailyWork.setTopic(mywork.getTopic());
        String workname = "hey you added " + mywork.getTopic();
        dailyWork.setWorkname(workname);
        dailyWork.setQuesid(mywork1.getId());
        daily_work_repo.save(dailyWork);
        System.out.println("adding difficulty level : " + AddToLevel(mywork.getEmail(), mywork.getLevel()));
        if(mywork.getImportant()){
            ReviseTopics reviseTopics = new ReviseTopics();
            reviseTopics.setQuestioninfo(mywork.getQuestioninfo());
            reviseTopics.setEmail(mywork.getEmail());
            reviseTopics.setTopic(mywork.getTopic());
            reviseTopics.setQuesId(mywork1.getId());
            revise_topics_repo.save(reviseTopics);
        }
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
        myworks.getFirst().setQuestion(mywork.getQuestion());
        myworks.getFirst().setQuestioninfo(mywork.getQuestioninfo());
        myworks.getFirst().setImportant(mywork.isImportant());
        myworks.getFirst().setLogic(mywork.getLogic());
        myworks.getFirst().setCode(mywork.getCode());
        myworks.getFirst().setAttempted(mywork.isAttempted());
        myworks.getFirst().setSubtopic(mywork.getSubtopic());
        myworks.getFirst().setLink(mywork.getLink());
        myworkrepo.save(myworks.getFirst());
        return ResponseEntity.ok(Collections.singletonMap("message","updated and saved in db"));
    }

    public ResponseEntity<?> delete(String name, Mywork mywork) {
        Optional<Mywork> mywork1 = myworkrepo.findByEmailAndIdAndTopic(name , mywork.getId(),mywork.getTopic()); // getting question from my work repo
        if(mywork1.isPresent()){
            Optional<Daily_work> dailyWork = daily_work_repo.findByQuesidAndEmailAndTopic(mywork1.get().getId() , name , mywork.getTopic());
            myworkrepo.delete(mywork1.get());
            if(dailyWork.isPresent()) {
                dailyWork.get().setDeleted(true);
                daily_work_repo.save(dailyWork.get());
            }
            return new ResponseEntity<>("question deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("question not found", HttpStatus.OK);
    }

    public boolean AddToLevel(String name , String level){
        Optional<Levels> levels = levelsRepo.findByEmail(name);
        if(levels.isPresent()){
            Levels levels1 = levels.get();
            return FindLevel(level, levels1 , name);
        }
        else{
            Levels levels1 = new Levels();
            return FindLevel(level, levels1 , name);
        }
    }

    private boolean FindLevel(String level, Levels levels1, String name) {
        if(Objects.equals(level, "hard")){
            levels1.setHardLevelCount(levels1.getHardLevelCount() + 1);
        }
        else if(Objects.equals(level, "easy")){
            levels1.setEasyLevelCount(levels1.getEasyLevelCount() + 1);
        }
        else if(Objects.equals(level, "medium")){
            levels1.setMediumLevelCount(levels1.getMediumLevelCount() + 1);
        }
        levels1.setEmail(name);
        levelsRepo.save(levels1);
        return true;
    }

    public ResponseEntity<?> get_one_question(Mywork mywork, String name) {
        Optional<Mywork> mywork1 = myworkrepo.findByEmailAndIdAndTopic(name, mywork.getId(),mywork.getTopic());
        if(mywork1.isPresent()){
            return ResponseEntity.ok(mywork1.get());
        }
        return new ResponseEntity<>("record not found", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> toggle_revise(Mywork mywork, String name) {
        Optional<Mywork> mywork1 = myworkrepo.findByEmailAndIdAndTopic(name, mywork.getId(), mywork.getTopic());
        if (mywork1.isPresent()) {
            Mywork mywork2 = mywork1.get();
            if (mywork2.getImportant()) {
                Optional<ReviseTopics> reviseTopics = revise_topics_repo.findByQuesIdAndTopicAndEmail(
                        mywork2.getId(),
                        mywork2.getTopic(),
                        name
                );
                if (reviseTopics.isPresent()) {
                    revise_topics_repo.delete(reviseTopics.get());
                    System.out.println("Deleted revise topic for ID: " + mywork2.getId()); // Debug log
                } else {
                    System.out.println("Could not find revise topic to delete for ID: " + mywork2.getId());
                }
                mywork2.setImportant(false);
                myworkrepo.save(mywork2);
            } else {
                mywork2.setImportant(true);
                myworkrepo.save(mywork2);
                ReviseTopics reviseTopics = new ReviseTopics();
                reviseTopics.setQuestioninfo(mywork2.getQuestioninfo());
                reviseTopics.setEmail(name);
                reviseTopics.setTopic(mywork2.getTopic());
                reviseTopics.setQuesId(mywork2.getId());
                revise_topics_repo.save(reviseTopics);
            }
            return new ResponseEntity<>("question updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("question not found", HttpStatus.OK);
    }

    public ResponseEntity<?> get_All(String name) {
        List<Daily_work> dailyWorks = daily_work_repo.findByEmail(name);
        Hero hero = new Hero();
        hero.setDaily_work(dailyWorks);
        Optional<Levels> levels = levelsRepo.findByEmail(name);
        hero.setLevelsRepo(levels.orElse(null));
        List<ReviseTopics> reviseTopics = revise_topics_repo.findAllByEmail(name);
        hero.setRevise_topics(reviseTopics);
        return new ResponseEntity<>(hero , HttpStatus.OK);
    }
}

