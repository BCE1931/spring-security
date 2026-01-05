package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Levels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LevelsRepo extends JpaRepository<Levels, Long> {
    Optional<Levels> findByEmail(String name);

}
