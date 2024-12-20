package com.baseball_root.winRate;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WinRateCalculatorController {

    private final WinRateCalculatorService winRateCalculatorService;

    @GetMapping("/win-rate/calculates/{memberId}")
    public ResponseEntity<WinRateCalculatorDto.Response> calculateWinRate(@PathVariable(name = "memberId") Long memberId,
                                                                          @RequestParam(name = "location") String location,
                                                                          @RequestParam(name = "season") String season,
                                                                          @RequestParam(name = "team") String team) {
        WinRateCalculatorDto.Response response = winRateCalculatorService.calculateWinRate(memberId, location, season, team);

        return ResponseEntity.ok(response);
    }

    /*@GetMapping("/win-rate/calculates/{memberId}")
    public ResponseEntity<Integer> calculateWinRate(@PathVariable(name = "memberId") Long memberId,
                                                      @RequestParam(name = "location") String location,
                                                      @RequestParam(name = "season") String season,
                                                      @RequestParam(name = "team") String team) {
        System.out.println("@@@@@@@@@@@@@" + memberId + " " + location+ " " + season+ " " + team+ " ");
        int response = winRateCalculatorService.calculateWinRate(memberId, location, season, team);

        return ResponseEntity.ok(response);
    }*/
}
