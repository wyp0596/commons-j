package io.github.wyp0596.util;

import java.util.*;

/**
 * Created by wyp0596 on 31/01/2018.
 */
public abstract class LotteryUtils {

    private static final Random RANDOM = new Random();

    private LotteryUtils() {
        throw new UnsupportedOperationException();
    }

    private static int randomInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    /**
     * 随机取值
     *
     * @param idList id列表
     * @return 随机结果
     */
    public static Long luckyDraw(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }
        return idList.get(randomInt(idList.size()));
    }

    /**
     * 加权随机
     *
     * @param id2WeightMap id与权重的映射
     * @return 加权随机结果
     */
    public static Long luckyDraw(Map<Long, Integer> id2WeightMap) {
        if (id2WeightMap.isEmpty()) {
            return null;
        }
        NavigableMap<Integer, Long> lotteryMap = new TreeMap<>();
        Integer key = 0;
        for (Map.Entry<Long, Integer> entry : id2WeightMap.entrySet()) {
            Long id = entry.getKey();
            Integer weight = entry.getValue();
            lotteryMap.put(key, id);
            key += weight;
        }
        Integer randomKey = randomInt(key) + 1;
        return lotteryMap.lowerEntry(randomKey).getValue();
    }


}
