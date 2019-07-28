package com.myproject.blog.service.impl;

import com.arychand.base.RecordNotFoundException;
import com.arychand.base.util.SearchUtils;
import com.arychand.base.util.StringUtils;
import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StorySearchDto;
import com.myproject.blog.repository.StoryRepository;
import com.myproject.blog.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
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
        return storyRepository.findByTitleContainingAndContentContainingAndDeleteFlagOrderByDateCreatedDesc(
                StringUtils.nullToEmpty(storySearchDto.getTitle()),
                StringUtils.nullToEmpty(storySearchDto.getContent()),
                false,
                SearchUtils.toPageable(storySearchDto));
    }

    @Override
    public List<Story> findDroplist() {
        return storyRepository.findAllByDeleteFlag(false);
    }

    @Override
    public Story update(Story story) {
        Story currentStory = storyRepository.findByIdAndDeleteFlag(story.getId(), false).orElseThrow(RecordNotFoundException::new);
        currentStory.setTitle(story.getTitle());
        currentStory.setSummary(story.getSummary());
        currentStory.setContent(story.getContent());
        currentStory.setUpdatedBy(story.getUpdatedBy());
        currentStory.setLastUpdated(OffsetDateTime.now());
        return storyRepository.save(currentStory);
    }

    @Override
    public Story delete(String id) {
        Story currentStory = storyRepository.findByIdAndDeleteFlag(id, false).orElseThrow(RecordNotFoundException::new);
        currentStory.setDeleteFlag(true);
        return storyRepository.save(currentStory);
    }


}
