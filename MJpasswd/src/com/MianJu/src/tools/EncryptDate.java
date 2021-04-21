package com.MianJu.src.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

import com.MianJu.config.Config;
import com.MianJu.src.core.UserPasswdClass;

import org.apache.commons.codec.binary.Hex;
public class EncryptDate {


    public static String AES_Decrypt(String encrypt,String Key){
        /*
        将密文解密为明文
         */
        try{
            return decrptDate(encrypt,Key);
        }catch (Exception e){
            System.out.println("解密失败");
            return null;
        }
    }

    public static String AES_Encrypt(String date,String Key){
        /*
        将明文转换为密文
         */
        try {
            return encrpt(date,Key);
        } catch (Exception e) {
            System.out.println("加密失败");
            return null;
        }
    }

    private static String encrpt(String data,String Key)  throws Exception {
        /*
        加密
         */
        // Encrypt
        final Cipher encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(Key, Config.AES_CODE));
        char[] code=  Hex.encodeHex(encryptCipher.doFinal(data.getBytes(Config.AES_CODE)));
        StringBuilder builder = new StringBuilder();
        for(char d:code) {
            builder.append(d);
        }
        return builder.toString();

    }
    private static String decrptDate(String data,String Key)  throws Exception {
        /*
        解密
         */
        // Decrypt
        final Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(Key, Config.AES_CODE));
        return new String(decryptCipher.doFinal(Hex.decodeHex(data.toCharArray())));
    }

    private static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        /*
        创建密钥方法
         */
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding))
                finalKey[i++%16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
