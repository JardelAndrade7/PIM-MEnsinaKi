package com.msoft.mek.repository;

import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelSolicitacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositorySolicitacao extends CrudRepository<ModelSolicitacao, Long> {

    ModelSolicitacao findBySolicitacaoId (Long solicitacaoId);

    @Query("select s from ModelSolicitacao s where s.aluno = :aluno")
    List<ModelSolicitacao> findByAluno (ModelAluno aluno);

    @Query("select s from ModelSolicitacao s where s.title = :title and s.modelo = :modelo")
    ModelSolicitacao findByTitleAndModelo (String title, String modelo);
}