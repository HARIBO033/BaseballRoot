package com.baseball_root.game;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "homeTeam", nullable = false)
    private String homeTeam;

    @Column(name = "awayTeam", nullable = false)
    private String awayTeam;

    @Column(name = "homeScore", nullable = false)
    private Integer homeScore;

    @Column(name = "awayScore", nullable = false)
    private Integer awayScore;

    @Column(name = "location", nullable = false)
    private String location;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
