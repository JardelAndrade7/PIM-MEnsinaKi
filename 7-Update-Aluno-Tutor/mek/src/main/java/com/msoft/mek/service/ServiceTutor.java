package com.msoft.mek.service;

import com.msoft.mek.repository.RepositoryTutor;
import com.msoft.mek.general.Util;
import com.msoft.mek.exception.CryptoExistsException;
import com.msoft.mek.exception.EMailExistsException;
import com.msoft.mek.exception.ServiceTutorException;
import com.msoft.mek.model.ModelTutor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class ServiceTutor {
    @Autowired
    RepositoryTutor repositoryTutor;
    public void saveSignin (ModelTutor modelTutor) throws Exception {
        try {
            // Verificar tamanho da senha
            String passw = modelTutor.getPassw();
            if (passw.length() < 8 || passw.length() > 16) {
                String illegalMessage = "A senha deve ter entre 8 e 16 caracteres.";
                throw new IllegalArgumentException(illegalMessage);
            }
            else {
                // Verificar se o E-Mail está cadastrado
                String tutorEmail = modelTutor.getTutorEmail();
                ModelTutor modelTutorSaved = repositoryTutor.findByEMail(tutorEmail);
                if (modelTutorSaved != null) {
                    String emailMessage = "O E-Mail " + tutorEmail + " já está cadastrado.";
                    throw new EMailExistsException(emailMessage);
                }
                else {
                    String passwMd5 = Util.md5(passw);
                    modelTutor.setPassw(passwMd5);
                }
            }
        } catch (NoSuchAlgorithmException noSuch) {
            String cryptoMessage = "Erro na criptografia da senha.";
            throw new CryptoExistsException(cryptoMessage);
        }
        repositoryTutor.save(modelTutor);
    } // saveSignin

    public ModelTutor findSignin (String tutorEmail, String passw) throws ServiceTutorException {
        ModelTutor modelTutorSignin = repositoryTutor.acharPorCadastro(tutorEmail, passw);
        return modelTutorSignin;
    }

    public static String gererateToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void updateToken(String tutorEmail, String token) {
        repositoryTutor.updateTokenByEmail(token, tutorEmail);
    }

    public void updateProfile (ModelTutor updating) throws Exception {
        try {
            String updatingPassw = updating.getPassw();
            if (updatingPassw.length() < 8 || updatingPassw.length() > 16) {
                String illegalMessage = "A senha deve ter entre 8 e 16 caracteres.";
                throw new IllegalArgumentException(illegalMessage);
            }
            String updatingEmail = updating.getTutorEmail();
            long updatingCodigo = updating.getTutorId();
            ModelTutor saved = repositoryTutor.findByEMailAndCodigo(updatingEmail, updatingCodigo);
            long savedCodigo = saved.getTutorId();
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
        repositoryTutor.save(updating);
    } // updateProfile

}
