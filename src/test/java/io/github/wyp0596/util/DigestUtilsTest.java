package io.github.wyp0596.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by wyp0596 on 02/02/2018.
 */
public class DigestUtilsTest {

    private static final String ORIGIN_TEXT = "test digestUtils";
    private static final byte[] ORIGIN_TEXT_BYTES = ORIGIN_TEXT.getBytes(StandardCharsets.UTF_8);

    private static final byte[] RIGHT_MD5_BYTES = new byte[]{-104, 111, 15, 67, -56, 90, 101, 45, 72, -43, 79, -117, 70, 22, -17, -116};
    private static final byte[] RIGHT_SHA_BYTES = new byte[]{5, 88, -58, -78, -120, 41, 66, 3, 71, 122, 113, -58, -84, 97, 109, -30, 47, 66, -3, -77};
    private static final byte[] RIGHT_SHA256_BYTES = new byte[]{-118, 126, -1, -71, -80, 28, -17, 32, 29, -105, -36, -63, 8, -58, -54, -18, -37, -29, 101, 45, -104, -51, 122, 127, -53, 3, 80, -73, -55, -17, 104, 81};

    private static final String RIGHT_MD5_STR = "986f0f43c85a652d48d54f8b4616ef8c";
    private static final String RIGHT_SHA_STR = "0558c6b288294203477a71c6ac616de22f42fdb3";
    private static final String RIGHT_SHA256_STR = "8a7effb9b01cef201d97dcc108c6caeedbe3652d98cd7a7fcb0350b7c9ef6851";

    @Test
    public void digest() throws IOException {
        digestTest("MD5", RIGHT_MD5_BYTES);
        digestTest("SHA", RIGHT_SHA_BYTES);
        digestTest("SHA-256", RIGHT_SHA256_BYTES);
    }

    @Test
    public void digestAsHexString() throws IOException {
        digestAsHexStringTest("MD5", RIGHT_MD5_STR);
        digestAsHexStringTest("SHA", RIGHT_SHA_STR);
        digestAsHexStringTest("SHA-256", RIGHT_SHA256_STR);
    }

    private void digestTest(String algorithm, byte[] right) throws IOException {
        byte[] digested1 = DigestUtils.digest(algorithm, ORIGIN_TEXT_BYTES);
        byte[] digested2 = DigestUtils.digest(algorithm, new ByteArrayInputStream(ORIGIN_TEXT_BYTES));
        Assert.assertArrayEquals(digested1, right);
        Assert.assertArrayEquals(digested2, right);
    }

    private void digestAsHexStringTest(String algorithm, String right) throws IOException {
        String digested1 = DigestUtils.digestAsHexString(algorithm, ORIGIN_TEXT_BYTES);
        String digested2 = DigestUtils.digestAsHexString(algorithm, ORIGIN_TEXT);
        String digested3 = DigestUtils.digestAsHexString(algorithm, new ByteArrayInputStream(ORIGIN_TEXT_BYTES));
        Assert.assertEquals(digested1, right);
        Assert.assertEquals(digested2, right);
        Assert.assertEquals(digested3, right);
    }

}
