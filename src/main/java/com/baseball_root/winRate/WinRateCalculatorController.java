package com.baseball_root.winRate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WinRateCalculatorController {

    private final WinRateCalculatorService winRateCalculatorService;

    @GetMapping("/win-rate/calculates/{memberId}")
    public ResponseEntity<WinRateCalculatorDto.Response> calculateWinRate(@PathVariable(name = "memberId") Long memberId,
                                                                          @RequestParam(name = "location") String location,
                                                                          @RequestParam(name = "season") String season,
                                                                          @RequestParam(name = "team") String team) {
        WinRateCalculatorDto.Response response = winRateCalculatorService.calculateWinRate(memberId, location, season, team);
        log.info("calculateWinRate 호출 response = " + response);
        return ResponseEntity.ok(response);
    }

}
