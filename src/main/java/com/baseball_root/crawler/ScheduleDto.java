package com.baseball_root.crawler;

import lombok.Getter;
import lombok.Setter;

//@Getter
@Setter
@Getter
public class ScheduleDto {
    private final String currentDay;
    private final String time;
    private final TeamName team1;
    private String vs;
    private final TeamName team2;
    private final String location;

    public ScheduleDto(String currentDay, String time, TeamName team1, String vs, TeamName team2, String location) {
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
