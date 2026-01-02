package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Mytopcs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Mytopicsrepo extends JpaRepository<Mytopcs,Long> {

    boolean existsByUsernameAndTopicAndSubtopic(String username, String topic, String subtopic);

    Mytopcs findByUsernameAndTopicAndSubtopic(String username, String topic, String subtopic);


    boolean existsByTopicAndUsername(String topic, String username);

    List<Mytopcs> findByTopicAndUsername(String topic, String username);

    List<Mytopcs> findByTopicAndEmail(String topic, String email);

    Mytopcs findByEmailAndTopicAndSubtopic(String email, String topic, String subtopic);

    boolean existsByEmailAndTopicAndSubtopic(String email, String topic, String subtopic);

    boolean existsByTopicAndEmail(String topic, String email);
}
