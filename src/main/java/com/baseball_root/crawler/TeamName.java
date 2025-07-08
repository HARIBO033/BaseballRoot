package com.baseball_root.crawler;


public enum TeamName {
    UNKNOWN("기본팀"), // 기본값 처리용
    DOOSAN("두산 베어스"),
    HANHWA("한화 이글스"),
    KIA("기아 타이거즈"),
    KIWOOM("키움 히어로즈"),
    KT("KT 위즈"),
    LG("LG 트윈스"),
    LOTTE("롯데 자이언츠"),
    NC("NC 다이노스"),
    SAMSUNG("삼성 라이온즈"),
    SSG("SSG 랜더스");

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

    public static String getKoreanNameFromEnum(TeamName teamName) {
        if (teamName == null) {
            return UNKNOWN.koreanName;
        }
        return teamName.getKoreanName();
    }

    public String getKoreanName() {
        return koreanName;
    }

    TeamName(String teamName) {
        this.koreanName = teamName;
    }

    public String getTeamName() {
        return koreanName;
    }
}
