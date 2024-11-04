package com.baseball_root.winRate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class WinRateCalculatorDto {
@Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{
        private String location;
        private String season;
        private String team;

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private long totalGame;
        private long winCount;
        private long loseCount;
        private long tieCount;
        private long guessToWin;
        private int winRate;

        public static Response toResponse(int totalGame, long winCount, long loseCount, long tieCount, long guessToWin, int winRate) {
            return Response.builder()
                    .totalGame(totalGame)
                    .winCount(winCount)
                    .loseCount(loseCount)
                    .tieCount(tieCount)
                    .guessToWin(guessToWin)
                    .winRate(winRate)
                    .build();
        }
    }
}
