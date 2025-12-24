package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Userrepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

}
