package com.msoft.mek.general;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
    public static String md5 (String passw) throws NoSuchAlgorithmException {
        MessageDigest message = MessageDigest.getInstance("md5");
        BigInteger hash = new BigInteger(1, message.digest(passw.getBytes()));
        String hashString = hash.toString(16);
        return hashString;
    }
}
