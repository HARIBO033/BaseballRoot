package com.baseball_root.crawler;

import lombok.Getter;
import lombok.Setter;

//@Getter
@Setter
@Getter
public class ScheduleDto {
    private String currentDay;
    private String time;
    private KboTeamName team1;
    private String vs;
    private KboTeamName team2;
    private String location;

    public ScheduleDto() { // Jackson 역직렬화용 기본 생성자
    }

    public ScheduleDto(String currentDay, String time, KboTeamName team1, String vs, KboTeamName team2, String location) {
        this.currentDay = currentDay;
        this.time = time;
        this.team1 = team1;
        this.vs = vs;
        this.team2 = team2;
        this.location = location;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "currentDay='" + currentDay + '\'' +
                ", time='" + time + '\'' +
                ", team1='" + team1 + '\'' +
                ", :='" + vs + '\'' +
                ", team2='" + team2 + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
