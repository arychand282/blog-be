package com.myproject.blog.repository;

import com.myproject.blog.domain.StoryFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryFileRepository extends PagingAndSortingRepository<StoryFile, String> {

    Optional<StoryFile> findById(String id);

    List<StoryFile> findByStoryId(String storyId);

    List<StoryFile> findByFileName(String fileName);

}
