package com.baseball_root.attach;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class AttachImageController {

    private final AttachImageService attachImageService;


    // 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            AttachImage savedImage = attachImageService.saveImage(file);
            return ResponseEntity.ok("Image uploaded successfully with ID: " + savedImage.getId());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Image upload failed: " + e.getMessage());
        }
    }

    // 이미지 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable(name = "id") Long id) {
        try {
            String imageData = attachImageService.getImage(id);
            return ResponseEntity.ok(imageData); // Base64 문자열 반환
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
