package com.baseball_root.Weather;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherByLocationDto {
    private String location;
    //날짜
    private String date;
    //시간
    private String time;
    private String weather;//날씨
    private String temperature;//기온
    private String humidity;//습도
}
