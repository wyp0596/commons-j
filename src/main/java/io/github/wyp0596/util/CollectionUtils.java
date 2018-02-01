package io.github.wyp0596.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyp0596 on 28/01/2018.
 */
public abstract class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 对列表按数量进行分组, 使用subList方法
     *
     * @param src      待分组列表
     * @param quantity 每组数量
     * @param <E>      类型
     * @return 分组后的列表
     * @see List#subList(int, int)
     */
    public static <E> List<List<E>> groupList(List<E> src, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException();
        }
        int size = src.size();
        List<List<E>> result = new ArrayList<>();
        int fromIndex = 0;
        while (fromIndex < size) {
            int toIndex = fromIndex + quantity;
            if (toIndex > size) {
                toIndex = size;
            }
            result.add(src.subList(fromIndex, toIndex));
            fromIndex = toIndex;
        }
        return result;
    }
}
