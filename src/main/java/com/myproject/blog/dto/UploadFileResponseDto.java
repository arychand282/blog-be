package com.myproject.blog.dto;

import lombok.Data;

@Data
public class UploadFileResponseDto {

    private String id;

    private String fileName;

    private String fileDownloadUri;

    private String fileType;

    private long size;

}
