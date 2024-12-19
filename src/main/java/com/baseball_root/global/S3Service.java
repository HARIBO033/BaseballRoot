package com.baseball_root.global;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
    private final S3Client s3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * S3에 파일 업로드
     *
     * @param key 파일 이름
     * @return
     * @throws IOException
     */
    public String getFileUrl(String key) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder().bucket(bucket).key(key).build()).toString();
    }


    public String uploadFile(MultipartFile multipartFile){
        try {
            String key = multipartFile.getOriginalFilename();
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(multipartFile.getContentType())
//                    .metadata(metadata)
//                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            RequestBody requestBody = RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize());
            s3Client.putObject(objectRequest, requestBody);
        } catch (IOException e) {
            log.debug("s3 uploading problem.");
        }
        return multipartFile.getOriginalFilename();
    }

    public List<String> uploadMultiFile(List<MultipartFile> multipartFiles){
        List<String> fileNameList = new ArrayList<>();

        // forEach 구문을 통해 multipartFiles 리스트로 넘어온 파일들을 순차적으로 fileNameList 에 추가
        multipartFiles.forEach(file -> {
            String key;
            try {
                key = file.getOriginalFilename();
                PutObjectRequest objectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .contentType(file.getContentType())
                        .build();

                RequestBody requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
                s3Client.putObject(objectRequest, requestBody);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
            }
            fileNameList.add(key);
        });

        return fileNameList;
    }
    /**
     * S3에서 파일 삭제
     *
     * @param fileName 파일 이름
     */
    public void deleteFile(String key) {
        try {
            if (existsFile(key)) {
                s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucket).key(key).build());
            }
        } catch (S3Exception e) {
            log.error("S3 delete error: {}", e.getMessage());
        }
    }

    /**
     * 파일 존재 여부 확인
     *
     * @param fileName
     * @return
     */
    private boolean existsFile(String key) {
        try {
            s3Client.headObject(HeadObjectRequest.builder().bucket(bucket).key(key).build());
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }

    /*public String uploadFile(String bucketName, String key, String filePath) throws IOException {
        Path file = Path.of(filePath);
        // S3 업로드 진행
        RequestBody requestBody = RequestBody.fromFile(file);
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(request, requestBody);

        //return "업로드 성공";
        return "https://" + bucketName + ".s3.ap-northeast-2.amazonaws.com/" + key;
    }*/
}