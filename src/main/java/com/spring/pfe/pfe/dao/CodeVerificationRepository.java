package com.spring.pfe.repository;

import com.spring.pfe.models.CodeVerification;
import com.spring.pfe.models.Cours;
import com.spring.pfe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeVerificationRepository extends JpaRepository<CodeVerification, Long> {


    @Query("Select d from User d where d.code = :code ")
    User findUserByCode(@Param("code") String code);
}
