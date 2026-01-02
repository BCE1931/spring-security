package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Topicsrepo extends JpaRepository<Topics,Long> {

    @Query(value = "select * from topics where topic = :topic and username = :username",nativeQuery = true)
    List<Topics> gettopics(String topic, String username);
}