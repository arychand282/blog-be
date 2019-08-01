package com.myproject.blog.service;

import com.myproject.blog.domain.StoryFile;

import java.util.List;
import java.util.Optional;

public interface StoryFileService {

    Optional<StoryFile> findById(String id);

    StoryFile save(StoryFile storyFile);

    List<StoryFile> findByStoryId(String storyId);

    void delete(String id);

}
