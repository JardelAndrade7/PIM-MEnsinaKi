package com.msoft.mek.repository;

import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSugestao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorySugestao extends JpaRepository<ModelSugestao, Long> {
    ModelSugestao findBySugestaoId (Long sugestaoId);

    // @Query("select s from ModelSugestao s where s.tutor = :tutor")
    Page<ModelSugestao> findByTutor (ModelTutor tutor, Pageable pageable);

    @Query("select s from ModelSugestao s where s.title = :title and s.modelo = :modelo")
    ModelSugestao findByTitleAndModelo (String title, String modelo);

    Page<ModelSugestao> findByArea (ModelArea modelArea, Pageable pageable);

    @Query("select s from ModelSugestao s where s.area.areaId = :areaId and lower(s.title) like lower(concat('%', :title, '%'))")
    Page<ModelSugestao> findByAreaIdAndTitle(Long areaId, String title, Pageable pageable);

    @Query("select s from ModelSugestao s where s.tutor = :tutor and lower(s.title) like lower(concat('%', :title, '%'))")
    Page<ModelSugestao> findByTutorAndTitle(ModelTutor tutor, String title, Pageable pageable);
} // RepositorySugestao