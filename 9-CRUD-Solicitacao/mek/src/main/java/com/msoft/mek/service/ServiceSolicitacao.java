package com.msoft.mek.service;
import com.msoft.mek.model.ModelSolicitacao;
import com.msoft.mek.repository.RepositorySolicitacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceSolicitacao {

    @Autowired
    RepositorySolicitacao repositorySolicitacao;

    public void save (ModelSolicitacao modelSolicitacao) throws Exception {
        repositorySolicitacao.save(modelSolicitacao);
    } // save

    public void update (ModelSolicitacao updating) throws Exception {
        repositorySolicitacao.save(updating);
    } // update

}
