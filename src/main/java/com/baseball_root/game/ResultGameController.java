package com.baseball_root.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResultGameController {

    private final ResultGameService resultGameService;

    // 게임 결과 저장
    @PostMapping("/result-game/save")
    public void saveResultGame(){
        resultGameService.saveResultGame();
    }
}
