package com.baseball_root.crawler;


public enum TeamName {
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
    public static TeamName fromKoreanName(String koreanName) {
        for (TeamName teamName : values()) {
            if (teamName.koreanName.equals(koreanName)) {
                return teamName;
            }
        }
        return UNKNOWN; // 매칭되지 않는 경우 기본값
    }

    TeamName(String teamName) {
        this.koreanName = teamName;
    }

    public String getTeamName() {
        return koreanName;
    }
}
