package com.wyq.firehelper.security.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 最原始的解解密函数整理
 * 注释参数名字配合自动化反射
 */
public class Encryption {

    /**
     * Return the bytes of MD2 security.
     *
     * @param data The data.
     * @return the bytes of MD2 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptMD2(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * Return the bytes of MD5 security.
     *
     * @param data The data.
     * @return the bytes of MD5 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }


    /**
     * Return the bytes of SHA1 security.
     *
     * @param data The data.
     * @return the bytes of SHA1 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * Return the bytes of SHA224 security.
     *
     * @param data The data.
     * @return the bytes of SHA224 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptSHA224(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * Return the bytes of SHA256 security.
     *
     * @param data The data.
     * @return the bytes of SHA256 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * Return the bytes of SHA384 security.
     *
     * @param data The data.
     * @return the bytes of SHA384 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptSHA384(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * Return the bytes of SHA512 security.
     *
     * @param data The data.
     * @return the bytes of SHA512 security
     */
    @MethodParamName(paramName = {"data"})
    public static byte[] encryptSHA512(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }



    /**
     * Return the bytes of hash security.
     *
     * @param data      The data.
     * @param algorithm The name of hash security.
     * @return the bytes of hash security
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

/*************************************************************
 * DES
 */

    /**
     * Return the bytes of DES security.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES security
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] encryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }

    /**
     * Return the bytes of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] decryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    /**
     * Return the bytes of 3DES security.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES security
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] encrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true);
    }

    /**
     * Return the bytes of 3DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] decrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    /**
     * Return the bytes of AES security.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES security
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] encryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }

    /**
     * Return the bytes of AES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of AES decryption
     */
    @MethodParamName(paramName = {"data","key","transformation","iv"})
    public static byte[] decryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }


    /**
     * Return the bytes of symmetric security or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The name of algorithm.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of symmetric security or decryption
     */
    private static byte[] symmetricTemplate(final byte[] data,
                                            final byte[] key,
                                            final String algorithm,
                                            final String transformation,
                                            final byte[] iv,
                                            final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}
