package com.msoft.mek.service;

import com.msoft.mek.repository.RepositoryAluno;
import com.msoft.mek.general.Util;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.exception.ServiceAlunoException;
import com.msoft.mek.model.ModelAluno;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class ServiceAluno {
    @Autowired
    RepositoryAluno repositoryAluno;
    public void saveSignin (ModelAluno modelAluno) throws Exception {
        try {
            String passw = modelAluno.getPassw();
            if (passw.length() < 8 || passw.length() > 16) {
                String illegalMessage = "A senha deve ter entre 8 e 16 caracteres.";
                throw new IllegalArgumentException(illegalMessage);
            }
            String alunoEmail = modelAluno.getAlunoEmail();
            ModelAluno modelAlunoSaved = repositoryAluno.findByEMail(alunoEmail);
            if (modelAlunoSaved != null) {
                String emailMessage = "O E-Mail " + alunoEmail + " já está cadastrado.";
                throw new EMailExistsException(emailMessage);
            }
            String passwMd5 = Util.md5(passw);
            modelAluno.setPassw(passwMd5);
        } catch (NoSuchAlgorithmException noSuch) {
            String cryptoMessage = "Erro na criptografia da senha.";
            throw new CryptoExistsException(cryptoMessage);
        }
        repositoryAluno.save(modelAluno);
    } // saveSignin

    public ModelAluno findSignin (String alunoEmail, String passw) throws ServiceAlunoException {
        ModelAluno modelAlunoSignin = repositoryAluno.acharPorCadastro(alunoEmail, passw);
        return modelAlunoSignin;
    }

    public static String gererateToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void updateToken(String alunoEmail, String token) {
        repositoryAluno.updateTokenByEmail(token, alunoEmail);
    }

    public void updateProfile (ModelAluno updating) throws Exception {
        try {
            String updatingPassw = updating.getPassw();
            if (updatingPassw.length() < 8 || updatingPassw.length() > 16) {
                String illegalMessage = "A senha deve ter entre 8 e 16 caracteres.";
                throw new IllegalArgumentException(illegalMessage);
            }
            String updatingEmail = updating.getAlunoEmail();
            long updatingCodigo = updating.getAlunoId();
            ModelAluno saved = repositoryAluno.findByEMailAndCodigo(updatingEmail, updatingCodigo);
            long savedCodigo = saved.getAlunoId();
            if (saved != null && updatingCodigo != savedCodigo) {
                String emailMessage = "O E-Mail " + updatingEmail + " já está cadastrado.";
                throw new EMailExistsException(emailMessage);
            }
            String updatingPasswMd5 = Util.md5(updatingPassw);
            updating.setPassw(updatingPasswMd5);
        } catch (NoSuchAlgorithmException noSuch) {
            String cryptoMessage = "Erro na criptografia da senha.";
            throw new CryptoExistsException(cryptoMessage);
        }
        repositoryAluno.save(updating);
    } // updateProfile

}
