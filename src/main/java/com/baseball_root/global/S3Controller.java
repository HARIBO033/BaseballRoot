package com.baseball_root.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class S3Controller {

    private final S3Service s3Service;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(name = "file") MultipartFile file) {
        s3Service.uploadFile(file);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        String url = s3Service.getFileUrl(file.getOriginalFilename());
        return ResponseEntity.ok(url);
    }

    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploadMultiFile(@RequestParam(name = "file") List<MultipartFile> files) {
        List<String> urlList = s3Service.uploadMultiFile(files);
        return ResponseEntity.ok(urlList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam(name = "delete") String fileName){
        s3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }

    /*@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(name = "file") MultipartFile file) throws IOException {
        try {
            // 파일을 임시 경로에 저장
            String tempDir = System.getProperty("java.io.tmpdir");
            File tempFile = new File(tempDir, file.getOriginalFilename());
            file.transferTo(tempFile);
            String key = file.getOriginalFilename();

            // 파일 경로 전달
            String result = s3Service.uploadFile("baseball-root-images", key, tempFile.getAbsolutePath());

            // 임시 파일 삭제
            tempFile.delete();

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 실패: " + e.getMessage());
        }
    }*/
}
