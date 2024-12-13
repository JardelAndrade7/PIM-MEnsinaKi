package com.msoft.mek.repository;

import com.msoft.mek.model.ModelAluno;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositoryAluno extends CrudRepository<ModelAluno, Long> {

    ModelAluno findByAlunoId(Long alunoId);

    @Query("select a from ModelAluno a where a.alunoEmail = :alunoEmail and a.passw = :passw")
    ModelAluno acharPorCadastro(String alunoEmail, String passw);

    @Query("select a from ModelAluno a where a.alunoEmail = :alunoEmail")
    ModelAluno findByEMail (String alunoEmail);

    @Query("select a from ModelAluno a where a.alunoEmail = :alunoEmail and a.alunoId = :alunoId")
    ModelAluno findByEMailAndCodigo (String alunoEmail, Long alunoId);

    @Modifying
    @Query("UPDATE ModelAluno a SET a.token = :token WHERE a.alunoEmail = :alunoEmail")
    void updateTokenByEmail(@Param("token") String token, @Param("alunoEmail") String alunoEmail);
}