package com.baseball_root.attach;

import com.baseball_root.diary.domain.Diary;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AttachImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    @Lob // 대용량 데이터 저장용
    @Column(columnDefinition = "LONGTEXT")
    private String imageData; // Base64 인코딩된 데이터

    /*@ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;*/

}
