package com.idotpet.dto;

public class UploadResponse {
    public String fileName;
    public String url;
    public String contentType;
    public long size;

    public UploadResponse(String fileName, String url, String contentType, long size) {
        this.fileName = fileName;
        this.url = url;
        this.contentType = contentType;
        this.size = size;
    }
}
