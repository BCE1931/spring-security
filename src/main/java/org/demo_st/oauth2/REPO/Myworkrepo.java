package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Mywork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Myworkrepo extends JpaRepository<Mywork,Long> {
    List<Mywork> findByUsernameAndTopic(String username, String dsa);

    List<Mywork> findByUsernameAndId(String username, Long id);

    List<Mywork> findByEmailAndTopic(String email, String dsa);

    List<Mywork> findByEmailAndId(String email, Long id);

    Optional<Mywork> findByEmailAndIdAndTopic(String name, Long id, String topic);

}
