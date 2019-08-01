package com.myproject.blog.service;

import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StorySearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface StoryService {

    Optional<Story> findDetail(String id);

    Story save(Story story);

    Page<Story> findSearch(StorySearchDto storySearchDto);

    List<Story> findDroplist();

    Story update(Story story);

    Story delete(String id);

//    ResponseEntity<byte[]> uploadImage(MultipartFile image, String alt);

}
