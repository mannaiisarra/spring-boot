package com.spring.pfe.repository;

import com.spring.pfe.models.Cours;
import com.spring.pfe.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("Select d from Video d where d.etape.idEtape = :etape_id_etape ")
    List<Video> getAllVideoByEtape(@Param("etape_id_etape") Long etape_id_etape);

    @Query("Select d from Video d where   d.progress = :progress and d.etape.idEtape = :idEtape ")
    List<Video> progress(@Param("idEtape") Long idEtape,@Param("progress") Boolean progress);
}
