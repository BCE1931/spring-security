package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.ReviseTopics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Revise_topics_repo extends JpaRepository<ReviseTopics, Long> {
    Optional<ReviseTopics> findByQuesIdAndTopicAndEmail(Long id, String topic, String name);


    List<ReviseTopics> findAllByEmail(String email);
}
