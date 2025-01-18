package com.msoft.mek.repository;

import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelArea;
import com.msoft.mek.model.ModelSolicitacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RepositorySolicitacao extends JpaRepository<ModelSolicitacao, Long> {

    ModelSolicitacao findBySolicitacaoId (Long solicitacaoId);

    // @Query("select s from ModelSolicitacao s where s.aluno = :aluno")
    Page<ModelSolicitacao> findByAluno (ModelAluno aluno, Pageable pageable);

    @Query("select s from ModelSolicitacao s where s.title = :title and s.modelo = :modelo")
    ModelSolicitacao findByTitleAndModelo (String title, String modelo);

    Page<ModelSolicitacao> findByArea (ModelArea modelArea, Pageable pageable);
}