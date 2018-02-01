package io.github.wyp0596.util;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * Created by wyp0596 on 19/12/2017.
 */
public abstract class OrderUtils {

    private static final Random RANDOM = new Random();
    private static final String FORMAT = "%s%s%03d";
    private static final String PATTERN = "yyyyMMddHHmmssSSS";


    private OrderUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 生成订单id
     * 17(yyyyMMddHHmmssSSS) + uid + type
     *
     * @param uid  用户id
     * @param type 类型
     * @return 订单id
     */
    public static String generateOrderId(Long uid, Integer type) {
        if (type >= 1000) {
            type %= 1000;
        }
        return String.format(FORMAT, DateTime.now().toString(PATTERN),
                uid.toString(), type);
    }
}
