package com.myproject.blog.dto;

import com.arychand.base.dto.SearchDto;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StorySearchDto extends SearchDto {

    private String title;

}
