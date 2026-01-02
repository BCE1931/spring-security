package org.demo_st.oauth2.REPO;

import org.demo_st.oauth2.ENTITY.Abouttokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Aboutokenrepo extends JpaRepository<Abouttokens,Integer> {

    @Query(value = "select username from abouttokens where refreshtoken =:oldtoken order by creat/iontime desc limit 1",nativeQuery = true)
    String getusername(String oldtoken);
}
