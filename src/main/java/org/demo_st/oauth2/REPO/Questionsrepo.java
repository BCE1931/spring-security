package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Questionsrepo extends JpaRepository<Questions,Long> {

    List<Questions> findByUsername(String username);

    List<Questions> findByEmail(String email);
}
