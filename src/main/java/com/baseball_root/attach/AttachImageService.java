package com.baseball_root.attach;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AttachImageService {

    private final AttachImageRepository attachImageRepository;

    // 이미지 저장
    public AttachImage saveImage(MultipartFile file) throws IOException {
        AttachImage imageEntity = new AttachImage();
        imageEntity.setName(file.getOriginalFilename());
        imageEntity.setImageData(Base64.getEncoder().encodeToString(file.getBytes())); // Base64 인코딩

        return attachImageRepository.save(imageEntity);
    }

    // 이미지 가져오기
    public String getImage(Long id) {
        AttachImage imageEntity = attachImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
        return imageEntity.getImageData(); // Base64 디코딩은 클라이언트 측에서 처리
    }
}
