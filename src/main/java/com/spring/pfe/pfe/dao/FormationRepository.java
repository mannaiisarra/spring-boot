package com.spring.pfe.repository;

import com.spring.pfe.models.Demande;
import com.spring.pfe.models.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    @Query("Select d from Demande d where d.formationn.id = :id ")
    List<Demande> getAllDemandeByFormation(@Param("id") Long id);
  /*  @Query("Select d from Formation d where d.fin_date > :fin_date and d.archiver = :archiver ")
    List<Formation> getAllFormationActive(@Param("fin_date") Date fin_date,@Param("archiver") Boolean archiver);
    @Query("Select d from Formation d where d.fin_date < :fin_date ")
    List<Formation> getAllFormationNotActive(@Param("fin_date") Date fin_date);*/

    @Query("Select d from Formation d where d.archiver = :archiver ")
    List<Formation> getAllFormationByArchiver(@Param("archiver") Boolean archiver);
}
