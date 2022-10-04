package com.spring.pfe.repository;

import com.spring.pfe.models.Etape;
import com.spring.pfe.models.QuizResultResponse;
import com.spring.pfe.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapeRepository extends JpaRepository<Etape, Long> {
    @Query("Select c from Etape c where c.theme.id = :id ")
    List<Etape> getAllEtapeByTheme(@Param("id") Long id);

    @Query("Select d from Etape d where   d.theme.id = :id and d.progressByEtape = :progressByEtape ")
    List<Etape> getEtapeeeActive(@Param("id") Long id,@Param("progressByEtape") Boolean progressByEtape);


}
