package com.baseball_root.winRate;

import com.baseball_root.diary.domain.Diary;
import com.baseball_root.diary.repository.DiaryRepository;
import com.baseball_root.member.Member;
import com.baseball_root.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WinRateCalculatorService {
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public WinRateCalculatorDto.Response calculateWinRate(Long memberId, String location, String season, String team) {
        log.info("calculateWinRate 호출 memberId = " + memberId + " location = " + location + " season = " + season + " team = " + team);
        //totalGame 구하기 (
        List<Diary> diaryList = diaryRepository.findByLocationAndMemberIdAndCreatedAt(location, memberId, season);//TODO: 메소드 이름 수정하기
        int totalGame = diaryList.size();

        if (team.equals("우리팀")) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. id=" + memberId));
            String ourTeam = member.getFavoriteTeam();
            //관람한 우리팀의 승리 횟수 구하기
            long winCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("WIN"))
                    .filter(diary -> diary.getHome().equals(ourTeam) || diary.getAway().equals(ourTeam))
                    .count();
            long loseCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("LOSE"))
                    .filter(diary -> diary.getHome().equals(ourTeam) || diary.getAway().equals(ourTeam))
                    .count();
            long tieCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("TIE"))
                    .filter(diary -> diary.getHome().equals(ourTeam) || diary.getAway().equals(ourTeam))
                    .count();
            return getResponse(totalGame, winCount, loseCount, tieCount);
        } else {
            //전체팀
            long winCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("WIN"))
                    .count();
            long loseCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("LOSE"))
                    .count();
            long tieCount = diaryList.stream()
                    .filter(diary -> diary.getGameResult().equals("TIE"))
                    .count();

            return getResponse(totalGame, winCount, loseCount, tieCount);

        }

    }

    private WinRateCalculatorDto.Response getResponse(int totalGame, long winCount, long loseCount, long tieCount) {
        int winRate;
        if (totalGame == 0) {
            throw new IllegalArgumentException("해당하는 경기가 없습니다.");
        } else if (winCount == 0) {
            winRate = 0;
        } else {
            winRate = (int)(winCount * 100) / totalGame;
            //System.out.println("winRate = " + winRate);
        }
        long guessToWin = winCount;
        return WinRateCalculatorDto.Response.toResponse(totalGame, winCount, loseCount, tieCount, guessToWin, winRate);
    }


}
