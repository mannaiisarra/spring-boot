package com.spring.pfe.pfe.dao;

import com.spring.pfe.pfe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUser  extends JpaRepository<User,Long> {

    /*@Query("{'username':?0}")*/
    @Query("select u from  User  u where u.username= :username")
    User findByUsername(@Param("username") String username);

}
