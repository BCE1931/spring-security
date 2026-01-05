package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Daily_work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Daily_work_repo extends JpaRepository<Daily_work, Long> {

    Optional<Daily_work> findByQuesidAndEmailAndTopic(Long id, String name, String topic);

    List<Daily_work> findByEmail(String name);
}
