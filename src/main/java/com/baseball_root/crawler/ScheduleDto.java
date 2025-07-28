package com.baseball_root.crawler;

import lombok.Getter;
import lombok.Setter;

//@Getter
@Setter
@Getter
public class ScheduleDto {
    private final String currentDay;
    private final String time;
    private final KboTeamName team1;
    private String vs;
    private final KboTeamName team2;
    private final String location;

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
