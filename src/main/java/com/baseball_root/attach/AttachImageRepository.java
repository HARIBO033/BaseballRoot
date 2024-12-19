package com.baseball_root.attach;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachImageRepository extends JpaRepository<AttachImage, Long> {
    List<AttachImage> findByDiaryId(Long id);
}
