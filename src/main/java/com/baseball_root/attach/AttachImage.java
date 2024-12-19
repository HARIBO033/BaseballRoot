package com.baseball_root.attach;

import com.baseball_root.diary.domain.Diary;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "attach_image")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private AttachFileType fileType;
}
