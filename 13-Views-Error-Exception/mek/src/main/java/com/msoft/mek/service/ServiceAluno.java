package com.msoft.mek.service;

import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.general.Util;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.exception.ServiceAlunoException;
import com.msoft.mek.model.ModelAluno;
import com.msoft.mek.model.ModelTutor;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;

@Service
public class ServiceAluno {

    @Autowired
    RepositoryAluno repositoryAluno;

    @Autowired
    RepositoryTutor repositoryTutor;
    
    public void validatePasswordSize(String password) {
        if (password.length() < 8 || password.length() > 16) {
            throw new IllegalArgumentException("A senha deve ter entre 8 e 16 caracteres.");
        }
    }
    
    public void validateEmailExistence(String email) throws Exception {
        ModelAluno modelAlunoSaved = repositoryAluno.findByEMail(email);
        ModelTutor modelTutorSaved = repositoryTutor.findByEMail(email);
        if (modelAlunoSaved != null || modelTutorSaved != null) {
            throw new EMailExistsException("O E-Mail " + email + " já está cadastrado.");
        }
    }

    public void saveSignin(ModelAluno modelAluno) throws CryptoExistsException {
        try {
            String passwMd5 = Util.md5(modelAluno.getPassw());
            modelAluno.setPassw(passwMd5);
            repositoryAluno.save(modelAluno);
        } catch (NoSuchAlgorithmException noSuch) {
            throw new CryptoExistsException("Erro na criptografia da senha.");
        }
    }

    public ModelAluno findSignin (String alunoEmail, String passw) throws ServiceAlunoException {
        ModelAluno modelAlunoSignin = repositoryAluno.acharPorCadastro(alunoEmail, passw);
        return modelAlunoSignin;
    }

    @Transactional
    public void updateToken(String alunoEmail, String token) {
        repositoryAluno.updateTokenByEmail(token, alunoEmail);
    }
}
