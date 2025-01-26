package com.placement_portal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;

@Service
public class BucketService {
    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    private final S3Client s3Client;

    public BucketService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Method to create a bucket
    public String createBucket(String newBucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(newBucketName)
                .build();
        s3Client.createBucket(createBucketRequest);
        return "Bucket created successfully: " + newBucketName;
    }

    // Method to upload a file to S3
    public String uploadFile(MultipartFile file) throws IOException {
        String key = "resumes/" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return "File uploaded successfully: " + key;
    }

    // Method to generate a presigned URL for file download
    public URL generateDownloadLink(String key) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest);
    }

    // Method to delete a file
    public String deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        return "File deleted successfully: " + key;
    }
}
