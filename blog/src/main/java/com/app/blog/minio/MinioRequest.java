package com.app.blog.minio;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;


@Service
public class MinioRequest {

    //minio bucket name
    @Autowired
    private MinioClient minioClient;


    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
    public InputStream downloadFile(String bucketName, String objectName) {
        try {
            GetObjectArgs obj = GetObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .build();
            InputStream inputStream = minioClient.getObject(obj);
            return inputStream;
    

        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }
    public void deleteFile(String bucketName, String objectName) {
        try {
            RemoveObjectArgs args = RemoveObjectArgs.builder()
            .bucket(bucketName)
            .object(objectName)
            .build();
            minioClient.removeObject(args);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }



}
