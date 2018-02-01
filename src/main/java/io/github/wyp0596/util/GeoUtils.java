package io.github.wyp0596.util;

/**
 * Created by wyp0596 on 08/01/2018.
 */
public abstract class GeoUtils {

    private static final double EARTH_RADIUS = 6378137;//赤道半径(单位m)

    private GeoUtils() {
        throw new UnsupportedOperationException();
    }

    // 转化为弧度(rad)
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离, 算法来自谷歌地图
     *
     * @return 单位米
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
