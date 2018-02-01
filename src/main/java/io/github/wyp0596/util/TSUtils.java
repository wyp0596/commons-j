package io.github.wyp0596.util;

/**
 * 时间戳工具类
 * Created by wyp0596 on 09/11/2017.
 */
public abstract class TSUtils {

    private TSUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳(精确到秒, 10位)
     */
    public static long secondTS() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳(精确到毫秒, 13位)
     */
    public static long millisTS() {
        return System.currentTimeMillis();
    }

}
