package com.msoft.mek.repository;

import com.msoft.mek.model.ModelTutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryTutor extends CrudRepository<ModelTutor, Long> {

    ModelTutor findByTutorId (Long tutorId);

    @Query("select t from ModelTutor t where t.tutorEmail = :tutorEmail and t.passw = :passw")
    ModelTutor acharPorCadastro(String tutorEmail, String passw);
}