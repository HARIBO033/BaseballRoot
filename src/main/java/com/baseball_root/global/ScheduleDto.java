package com.baseball_root.global;

import lombok.ToString;

public class ScheduleDto {
    private final String currentDay;
    private final String time;
    private final String team1;
    private final String vs;
    private final String team2;
    private final String location;

    public ScheduleDto(String currentDay, String time, String team1, String vs, String team2, String location) {
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
                ", vs='" + vs + '\'' +
                ", team2='" + team2 + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
