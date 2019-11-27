package com.feture.learnfilter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OpenAPISignUtil {
    private static Logger logger = LoggerFactory.getLogger(OpenAPISignUtil.class);

    public static void main(String[] args) {
        String source = "123456";

        String target = OpenAPISignUtil.sha256(source);

        System.out.println(target);
    }

    public static String sha256(String source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(source.getBytes("UTF-8"));
            return byte2Hex(messageDigest.digest());

        } catch (NoSuchAlgorithmException e) {
            logger.error("Sha256 encypt error(AlgorithmException) : {}", e);

        } catch (UnsupportedEncodingException e) {
            logger.error("Sha256 encypt error(UnsupportedEncodingException) : {}", e);
        }


        return "";
    }

    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(plainText.getBytes("UTF-8"));
            byte[] secretBytes = md.digest();
            return byte2Hex(secretBytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5 encypt error(NoSuchAlgorithmException) : {}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("md5 encypt error(UnsupportedEncodingException) : {}", e);
        }

        return "";
    }


    private static String byte2Hex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }

        return sb.toString();
    }
}
