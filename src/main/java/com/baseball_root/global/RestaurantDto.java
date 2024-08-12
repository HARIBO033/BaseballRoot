package com.baseball_root.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private String restaurantsImgUrl;
    private String restaurantsName;
    private String restaurantsUserScore;
    private String restaurantsWorkingTime;
    private String restaurantsKeyword;


    public String[] toStringArray() {
        String[] strings = new String[]{
                restaurantsImgUrl,
                restaurantsName,
                restaurantsUserScore,
                restaurantsWorkingTime,
                restaurantsKeyword
        };
        return strings;
    }
}
