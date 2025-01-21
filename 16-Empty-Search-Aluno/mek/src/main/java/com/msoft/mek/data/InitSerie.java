package com.msoft.mek.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msoft.mek.model.ModelSerie;
import com.msoft.mek.repository.RepositorySerie;

@Configuration
public class InitSerie {

    @Bean
    public CommandLineRunner loadInitialSerie(RepositorySerie repositorySerie) {
        return args -> {
            long quantidadeAreas = repositorySerie.count();
            if (quantidadeAreas == 0) {
                String serieName = "1o Ano (Fundamental)";
                ModelSerie modelSerie1 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie1);

                serieName = "2o Ano (Fundamental)";
                ModelSerie modelSerie2 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie2);

                serieName = "3o Ano (Fundamental)";
                ModelSerie modelSerie3 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie3);

                serieName = "4o Ano (Fundamental)";
                ModelSerie modelSerie4 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie4);

                serieName = "5o Ano (Fundamental)";
                ModelSerie modelSerie5 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie5);

                serieName = "6o Ano (Fundamental)";
                ModelSerie modelSerie6 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie6);

                serieName = "7o Ano (Fundamental)";
                ModelSerie modelSerie7 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie7);

                serieName = "8o Ano (Fundamental)";
                ModelSerie modelSerie8 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie8);

                serieName = "9o Ano (Fundamental)";
                ModelSerie modelSerie9 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie9);

                serieName = "1o Ano (Médio)";
                ModelSerie modelSerie10 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie10);

                serieName = "2o Ano (Médio)";
                ModelSerie modelSerie11 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie11);

                serieName = "3o Ano (Médio)";
                ModelSerie modelSerie12 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie12);

                serieName = "Superior";
                ModelSerie modelSerie13 = new ModelSerie(serieName);
                repositorySerie.save(modelSerie13);
            }
        };
    }
}