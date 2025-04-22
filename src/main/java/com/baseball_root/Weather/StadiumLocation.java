package com.baseball_root.Weather;

import java.util.Map;

public class StadiumLocation {
    private static final Map<String, double[]> STADIUM_COORDS = Map.of(
            "고척스카이돔", new double[]{37.4981, 126.8675},
            "잠실종합운동장", new double[]{37.5143, 127.0725},
            "대구삼성라이온즈파크", new double[]{35.8703, 128.5912},
            "광주기아챔피언스필드", new double[]{35.1595, 126.8526},
            "수원KT위즈파크", new double[]{37.2636, 127.0287},
            "창원NC파크", new double[]{35.2281, 128.6812},
            "인천SSG랜더스필드", new double[]{37.4553, 126.7056},
            "대전한화생명이글스파크", new double[]{36.3504, 127.3845},
            "부산사직야구장", new double[]{35.1632, 129.1254}
            // 추가 가능
    );

    public static double[] getCoordinates(String stadiumName) {
        return STADIUM_COORDS.getOrDefault(stadiumName, null);
    }
    public static Map<String, double[]> getAllStadiums() {
        return STADIUM_COORDS;
    }
}
