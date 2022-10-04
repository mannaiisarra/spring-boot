package com.spring.pfe.repository;

import com.spring.pfe.models.Demande;
import com.spring.pfe.models.Etape;
import com.spring.pfe.models.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {
    @Query("Select d from Demande d where d.formationn.id = :id ")
    List<Demande> getAllUserParFormation(@Param("id") Long id);

    @Query("Select d from Demande d where d.formationn.id = :id and  d.active = :active")
    List<Demande> getAllUserActive(@Param("id") Long id,@Param("active") Boolean active);

    @Query("Select d from Demande d where d.users.id = :id ")
    List<Demande> getAllDemandeByUsers(@Param("id") Long id);

    @Query("Select d from Demande d where  d.active = :active")
    List<Demande> getAllNotActive(@Param("active") Boolean active);

    @Query("Select d from Demande d where d.users.id = :id ")
    Demande getAllsss(@Param("id") Long id);

    @Query("Select d from Demande d where d.users.id = :id and  d.active = :active ")
    List<Demande> getAllFormationActiveByUser(@Param("id") Long id,@Param("active") Boolean active);


    @Query("Select d from Demande d where  d.users.id = :id ")
    List<Demande> getAllDemandeBYUser(@Param("id") Long id);

    @Query("Select d from Demande d where  d.active = :active and d.vu = :vu ")
    List<Demande> getAllNotActiveVuFalse(@Param("active") Boolean active,@Param("vu") Boolean vu);
    @Query("Select d from Formation d where d.archiver = :archiver ")
    List<Formation> getAllFormationByArchiver(@Param("archiver") Boolean archiver);


}
