package com.myproject.blog.service;

import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StorySearchDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface StoryService {

    Optional<Story> findDetail(String id);

    Story save(Story story);

    Page<Story> findSearch(StorySearchDto storySearchDto);

}
