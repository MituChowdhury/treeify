package com.javafest.treeify.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.GridFSUploadStream;
import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class GridFSService {

    private final GridFSBucket gridFSBucket;

    public GridFSService(GridFSBucket gridFSBucket) {
        this.gridFSBucket = gridFSBucket;
    }

    public ObjectId uploadFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(file.getOriginalFilename());
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                uploadStream.write(buffer, 0, bytesRead);
            }
            uploadStream.flush();
            uploadStream.close();
            return uploadStream.getObjectId();
        }
    }

    public byte[] downloadFile(ObjectId fileId) throws IOException {
        try (GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(fileId)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = downloadStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        }
    }
}
