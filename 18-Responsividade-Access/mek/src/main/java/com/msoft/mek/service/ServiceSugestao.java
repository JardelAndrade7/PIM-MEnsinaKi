package com.msoft.mek.service;
import com.msoft.mek.model.ModelSugestao;
import com.msoft.mek.repository.RepositorySugestao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceSugestao {

    @Autowired
    RepositorySugestao repositorySugestao;
    
    public void save (ModelSugestao sugestao) throws Exception {
        repositorySugestao.save(sugestao);
    } // save

    public void update (ModelSugestao updating) throws Exception {
        repositorySugestao.save(updating);
    } // update

} // ServiceSugestao
