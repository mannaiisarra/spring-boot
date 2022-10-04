package com.spring.pfe.repository;

import com.spring.pfe.models.Cours;
import com.spring.pfe.models.Role;
import com.spring.pfe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

   // Optional<User> findByEmaill(String email);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query("Select d from User d where d.email = :email ")
    User findUserByEmail(@Param("email") String email);

    @Query("Select d from User d where d.code = :code ")
    User findUserByCode(@Param("code") int code);

    @Query("Select d from User d where d.username = :username ")
    User getUserByNickname(@Param("username") String username);
//    boolean updatePassword(String email, String password, String newPAss);
}
