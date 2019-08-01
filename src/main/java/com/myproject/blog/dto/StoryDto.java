package com.myproject.blog.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class StoryDto {

    private String id;

    private String title;

    private String summary;

    private String content;

    private String createdBy;

    private OffsetDateTime dateCreated;

    private String updatedBy;

    private OffsetDateTime lastUpdated;

    private List<UploadFileResponseDto> uploadFileResponseDtoList;

}
