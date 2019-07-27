package com.myproject.blog.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StoryDto {

    private String id;

    private String title;

    private String content;

    private String createdBy;

    private OffsetDateTime dateCreated;

    private String updatedBy;

    private OffsetDateTime lastUpdated;

}