package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Otprepo extends JpaRepository<Otp, Long> {
    boolean existsByEmail(String email);

    Optional<Otp> findByEmail(String email);

    Optional<Otp> findByEmailAndType(String email, String pwdreset);

}
