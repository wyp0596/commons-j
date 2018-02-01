package io.github.wyp0596.util;

/**
 * Created by wyp0596 on 28/12/2017.
 */
public abstract class KeyUtils {

    private KeyUtils() {
        throw new UnsupportedOperationException();
    }

    public static String generateKey(int length) {
        if (length < 1 || length > 32) {
            throw new IllegalArgumentException();
        }
        return DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis())).substring(0, length);
    }

    /**
     * 生成随机主键,长度为10,全小写字母
     *
     * @return 随机主键
     */
    public static String generateKey() {
        return generateKey(10);
    }

    public static void main(String[] args) {
        System.out.println(generateKey());
    }
}
