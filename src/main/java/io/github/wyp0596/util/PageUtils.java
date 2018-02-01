package io.github.wyp0596.util;

/**
 * Created by wyp0596 on 22/01/2018.
 */
public abstract class PageUtils {

    private PageUtils() {
    }

    public static int getPageCount(long count, double pageSize) {
        return (int) Math.ceil(count / pageSize);
    }


}
