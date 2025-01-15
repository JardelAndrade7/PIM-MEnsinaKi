package com.msoft.mek.repository;

import com.msoft.mek.model.ModelTutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositoryTutor extends CrudRepository<ModelTutor, Long> {

    ModelTutor findByTutorId (Long tutorId);

    @Query("select t from ModelTutor t where t.tutorEmail = :tutorEmail and t.passw = :passw")
    ModelTutor acharPorCadastro(String tutorEmail, String passw);

    @Query("select t from ModelTutor t where t.tutorEmail = :tutorEmail")
    ModelTutor findByEMail (String tutorEmail);

    @Query("select t from ModelTutor t where t.tutorEmail = :tutorEmail and t.tutorId = :tutorId")
    ModelTutor findByEMailAndCodigo (String tutorEmail, Long tutorId);

    @Modifying
    @Query("UPDATE ModelTutor t SET t.token = :token WHERE t.tutorEmail = :tutorEmail")
    void updateTokenByEmail(@Param("token") String token, @Param("tutorEmail") String tutorEmail);
}