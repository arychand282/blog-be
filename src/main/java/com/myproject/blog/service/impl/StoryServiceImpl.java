package com.myproject.blog.service.impl;

import com.arychand.base.util.SearchUtils;
import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StorySearchDto;
import com.myproject.blog.repository.StoryRepository;
import com.myproject.blog.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public Optional<Story> findDetail(String id) {
        return storyRepository.findByIdAndDeleteFlag(id, false);
    }

    @Override
    public Story save(Story story) {
        story.setDeleteFlag(false);
        story.setDateCreated(OffsetDateTime.now());
        return storyRepository.save(story);
    }

    @Override
    public Page<Story> findSearch(StorySearchDto storySearchDto) {
        return storyRepository.findByTitleContainingAndDeleteFlag(storySearchDto.getTitle(), false,
                SearchUtils.toPageable(storySearchDto));
    }


}
