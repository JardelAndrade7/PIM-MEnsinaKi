package com.msoft.mek.repository;

import com.msoft.mek.model.ModelArea;

import org.springframework.data.repository.CrudRepository;

public interface RepositoryArea extends CrudRepository<ModelArea, Long> {

    public ModelArea findByAreaId(Long areaId);

}