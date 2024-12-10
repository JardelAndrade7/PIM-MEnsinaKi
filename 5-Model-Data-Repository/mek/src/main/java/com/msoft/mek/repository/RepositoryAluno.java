package com.msoft.mek.repository;

import com.msoft.mek.model.ModelAluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryAluno extends CrudRepository<ModelAluno, Long> {

    ModelAluno findByAlunoId(Long alunoId);

    @Query("select a from ModelAluno a where a.alunoEmail = :alunoEmail and a.passw = :passw")
    ModelAluno acharPorCadastro(String alunoEmail, String passw);
}