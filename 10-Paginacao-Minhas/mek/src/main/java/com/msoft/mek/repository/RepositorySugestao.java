package com.msoft.mek.repository;

import com.msoft.mek.model.ModelTutor;
import com.msoft.mek.model.ModelSugestao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositorySugestao extends CrudRepository<ModelSugestao, Long> {
    ModelSugestao findBySugestaoId (Long sugestaoId);

    @Query("select s from ModelSugestao s where s.tutor = :tutor")
    List<ModelSugestao> findByCodigoTutor (ModelTutor tutor);

    @Query("select s from ModelSugestao s where s.title = :title and s.modelo = :modelo")
    ModelSugestao findByTitleAndModelo (String title, String modelo);
} // RepositorySugestao