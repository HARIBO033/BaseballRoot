package com.baseball_root.crawler;


public enum KboTeamName {
    UNKNOWN("기본팀"), // 기본값 처리용
    DOOSAN("두산"),
    HANHWA("한화"),
    KIA("KIA"),
    KIWOOM("키움"),
    KT("KT"),
    LG("LG"),
    LOTTE("롯데"),
    NC("NC"),
    SAMSUNG("삼성"),
    SSG("SSG");

    private final String koreanName;

    // 텍스트를 기반으로 TeamName 변환
    public static KboTeamName fromKoreanName(String koreanName) {
        for (KboTeamName teamName : values()) {
            if (teamName.koreanName.equals(koreanName)) {
                return teamName;
            }
        }
        return UNKNOWN; // 매칭되지 않는 경우 기본값
    }

    public static String getKoreanNameFromEnum(KboTeamName teamName) {
        if (teamName == null) {
            return UNKNOWN.koreanName;
        }
        return teamName.getKoreanName();
    }

    public String getKoreanName() {
        return koreanName;
    }

    KboTeamName(String teamName) {
        this.koreanName = teamName;
    }

    public String getTeamName() {
        return koreanName;
    }
}
