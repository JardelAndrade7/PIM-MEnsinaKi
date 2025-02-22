package com.msoft.mek.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msoft.mek.model.ModelArea;
import com.msoft.mek.repository.RepositoryArea;

@Configuration
public class InitArea {

    @Bean
    public CommandLineRunner loadInitialArea(RepositoryArea repositoryArea) {
        return args -> {
            long quantidadeAreas = repositoryArea.count();
            if (quantidadeAreas == 0) {
                String areaName = "Matemática";
                String description = "Matemática";
                ModelArea modelArea = new ModelArea(areaName, description);
                repositoryArea.save(modelArea);

                areaName = "Química";
                description = "Química";
                ModelArea modelArea2 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea2);

                areaName = "Física";
                description = "Física";
                ModelArea modelArea3 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea3);

                areaName = "Biologia";
                description = "Biologia";
                ModelArea modelArea4 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea4);

                areaName = "História";
                description = "História";
                ModelArea modelArea5 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea5);

                areaName = "Geografia";
                description = "Geografia";
                ModelArea modelArea6 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea6);

                areaName = "Filosofia";
                description = "Filosofia";
                ModelArea modelArea7 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea7);

                areaName = "Sociologia";
                description = "Sociologia";
                ModelArea modelArea8 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea8);

                areaName = "Ensino Religioso";
                description = "Ensino Religioso";
                ModelArea modelArea9 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea9);

                areaName = "Português";
                description = "Português";
                ModelArea modelArea10 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea10);

                areaName = "Inglês";
                description = "Inglês";
                ModelArea modelArea11 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea11);

                areaName = "Espanhol";
                description = "Espanhol";
                ModelArea modelArea12 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea12);

                areaName = "Ed. Física";
                description = "Ed. Física";
                ModelArea modelArea13 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea13);

                areaName = "Arte";
                description = "Arte";
                ModelArea modelArea14 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea14);

                areaName = "Tecnologia";
                description = "Tecnologia";
                ModelArea modelArea15 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea15);

                areaName = "Música";
                description = "Música";
                ModelArea modelArea16 = new ModelArea(areaName, description);
                repositoryArea.save(modelArea16);
            }
        };
    }
}