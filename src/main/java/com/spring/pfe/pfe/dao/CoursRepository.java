package com.spring.pfe.repository;

import com.spring.pfe.models.Cours;
import com.spring.pfe.models.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    @Query("Select d from Cours d where d.etape.idEtape = :etape_id_etape ")
    List<Cours> getAllUserParFormation(@Param("etape_id_etape") Long etape_id_etape);
}
