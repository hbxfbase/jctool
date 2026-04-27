package cn.sharpen.jctool.util.algorithm;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Keccak算法相关的工具。
 */
public class KeccakUtil {

    // keccak256签名
    public static String keccak256Sign(String privateKeyHex, String plaintext) {
        ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKeyHex, 16));
        return keccak256Sign(keyPair, plaintext);
    }
    public static String keccak256Sign(byte[] privateKey, String plaintext) {
        ECKeyPair keyPair = ECKeyPair.create(privateKey);
        return keccak256Sign(keyPair, plaintext);
    }

    // keccak256签名
    public static String keccak256Sign(ECKeyPair keyPair, String plaintext) {
        // ✅ 自动加 Ethereum 前缀
        Sign.SignatureData sig = Sign.signPrefixedMessage( plaintext.getBytes(StandardCharsets.UTF_8), keyPair);
        String signature = Numeric.toHexString( concat(sig.getR(), sig.getS(), sig.getV()) );
        return signature;
    }

    public static byte[] concat(byte[]... arrays) {
        int len = 0;
        for (byte[] a : arrays) len += a.length;

        byte[] result = new byte[len];
        int pos = 0;

        for (byte[] a : arrays) {
            System.arraycopy(a, 0, result, pos, a.length);
            pos += a.length;
        }
        return result;
    }
}
