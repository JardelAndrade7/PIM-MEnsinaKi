package com.msoft.mek.service;

import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.general.Util;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.exception.ServiceTutorException;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelTutor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class ServiceTutor {

    @Autowired
    RepositoryTutor repositoryTutor;

    @Autowired
    RepositoryAluno repositoryAluno;
    
    public void validatePasswordSize(String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 16 caracteres.");
        }
    }
    
    public void validateEmailExistence(String email) throws Exception {
        ModelTutor modelTutorSaved = repositoryTutor.findByEMail(email);
        ModelAluno modelAlunoSaved = repositoryAluno.findByEMail(email);
        if (modelTutorSaved != null || modelAlunoSaved != null) {
            throw new EMailExistsException("O E-Mail " + email + " já está cadastrado.");
        }
    }

    public void saveSignin(ModelTutor modelTutor) throws CryptoExistsException {
        try {
            String passwMd5 = Util.md5(modelTutor.getPassw());
            modelTutor.setPassw(passwMd5);
            repositoryTutor.save(modelTutor);
        } catch (NoSuchAlgorithmException noSuch) {
            throw new CryptoExistsException("Erro na criptografia da senha.");
        }
    }

    public ModelTutor findSignin (String tutorEmail, String passw) throws ServiceTutorException {
        ModelTutor modelTutorSignin = repositoryTutor.acharPorCadastro(tutorEmail, passw);
        return modelTutorSignin;
    }

    @Transactional
    public void updateToken(String tutorEmail, String token) {
        repositoryTutor.updateTokenByEmail(token, tutorEmail);
    }

}
