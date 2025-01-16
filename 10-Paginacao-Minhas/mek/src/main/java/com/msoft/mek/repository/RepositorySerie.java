package com.msoft.mek.repository;

import com.msoft.mek.model.ModelSerie;

import org.springframework.data.repository.CrudRepository;

public interface RepositorySerie extends CrudRepository<ModelSerie, Long> {

    public ModelSerie findBySerieId(Long serieId);

}