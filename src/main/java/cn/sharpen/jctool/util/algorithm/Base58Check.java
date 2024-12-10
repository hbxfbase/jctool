package cn.sharpen.jctool.util.algorithm;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import cn.hutool.core.util.HexUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jcajce.provider.digest.SHA256.Digest;

public final class Base58Check {
    public static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf((long)"123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".length());

    public static String bytesToBase58(byte[] data) {
        return rawBytesToBase58(addCheckHash(data));
    }

    public static String hex2base58(String hex){
        if(StringUtils.isBlank(hex)) {
            return null;
        }
        return rawBytesToBase58(HexUtil.decodeHex(hex));
    }
    public static String base58Hex(String base58){
        if(StringUtils.isBlank(base58)) {
            return null;
        }
        return HexUtil.encodeHexStr(Base58Check.base58ToBytes(base58));
    }

    public static String rawBytesToBase58(byte[] data) {
        StringBuilder sb = new StringBuilder();

        BigInteger[] quotrem;
        for(BigInteger num = new BigInteger(1, data); num.signum() != 0; num = quotrem[0]) {
            quotrem = num.divideAndRemainder(ALPHABET_SIZE);
            sb.append("123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".charAt(quotrem[1].intValue()));
        }

        for(int i = 0; i < data.length && data[i] == 0; ++i) {
            sb.append("123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".charAt(0));
        }

        return sb.reverse().toString();
    }

    static byte[] addCheckHash(byte[] data) {
        try {
            Digest digest = new Digest();
            digest.update(data);
            byte[] hash0 = digest.digest();
            digest.reset();
            digest.update(hash0);
            byte[] hash = Arrays.copyOf(digest.digest(), 4);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            buf.write(data);
            buf.write(hash);
            return buf.toByteArray();
        } catch (IOException var5) {
            throw new AssertionError(var5);
        }
    }

    public static byte[] base58ToBytes(String s) {
        byte[] concat = base58ToRawBytes(s);
        byte[] data = Arrays.copyOf(concat, concat.length - 4);
        byte[] hash = Arrays.copyOfRange(concat, concat.length - 4, concat.length);
        Digest digest = new Digest();
        digest.update(data);
        byte[] hash0 = digest.digest();
        digest.reset();
        digest.update(hash0);
        byte[] rehash = Arrays.copyOf(digest.digest(), 4);
        if (!Arrays.equals(rehash, hash)) {
            throw new IllegalArgumentException("Checksum mismatch");
        } else {
            return data;
        }
    }

    static byte[] base58ToRawBytes(String s) {
        BigInteger num = BigInteger.ZERO;

        for(int i = 0; i < s.length(); ++i) {
            num = num.multiply(ALPHABET_SIZE);
            int digit = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".indexOf(s.charAt(i));
            if (digit == -1) {
                throw new IllegalArgumentException("Invalid character for Base58Check");
            }

            num = num.add(BigInteger.valueOf((long)digit));
        }

        byte[] b = num.toByteArray();
        if (b[0] == 0) {
            b = Arrays.copyOfRange(b, 1, b.length);
        }

        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();

            for(int i = 0; i < s.length() && s.charAt(i) == "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".charAt(0); ++i) {
                buf.write(0);
            }

            buf.write(b);
            return buf.toByteArray();
        } catch (IOException var5) {
            throw new AssertionError(var5);
        }
    }

    private Base58Check() {
    }
}
