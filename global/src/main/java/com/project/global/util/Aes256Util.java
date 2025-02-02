package com.project.global.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * 필요시 사용, 아니면 삭제
 */
public class Aes256Util {
    public static String alg = "AES/CBC/PKCS5Padding";
    private static final String KEY = "ZEROBASEKEYISZEROBASEKEY"; // KEY에 underline 허용 안됨
    private static final String IV = KEY.substring(0, 16);

    /**
     * 문자열을 받아 암호화 하는 메서드
     * @return String 암호화된 문자
     */
    public static String encrypt(String text) {
        try {

            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec((KEY.getBytes()), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
            // 위에서 지정한 keyspec, ivspec을 가지고 암호화를 진행할 수 있는 cipher 객체를 생성한다.
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

            byte[] encrypted =
                    cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)); // 암호화객체를 이용하여 dofial(bytes[]) 하면 암호화 끝
            return Base64.encodeBase64String(encrypted); //암호화된 것을 또 Base64로 변환을 해서 사용

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("암호화 실패");
            return null;
        }
    }

    /**
     * 문자열을 받아 복호화 하는 메서드
     * @return String 복호화 문자
     */
    public static String decrypt(String cipherText) {
        try {
            // encoding의 역순으로
            byte[] decodedBytes = Base64.decodeBase64(cipherText);

            Cipher cipher = Cipher.getInstance(alg);
            SecretKeySpec keySpec = new SecretKeySpec((KEY.getBytes()), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decrypted =
                    cipher.doFinal(decodedBytes);
            return new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("복호화 실패");
            return null;
        }
    }
}

