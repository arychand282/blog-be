package com.myproject.blog.rest;

import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StoryDto;
import com.myproject.blog.dto.StorySearchDto;
import com.myproject.blog.service.StoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @GetMapping(value = "/{id}")
    public StoryDto findDetail(@PathVariable String id) throws Exception {
        Optional<Story> storyOptional = storyService.findDetail(id);
        if (storyOptional.isPresent()) {
            return toDto(storyOptional.get());
        } else {
            throw new Exception("Record Not Found for id: " + id);
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StoryDto save(@RequestBody StoryDto storyDto) {
        return toDto(storyService.save(toModel(storyDto)));
    }

    @GetMapping(value = "")
    public Page<StoryDto> findSearch(StorySearchDto storySearchDto) {
        return storyService.findSearch(storySearchDto).map(this::toDto);
    }

    private Story toModel(StoryDto storyDto) {
        Story story = new Story();
        BeanUtils.copyProperties(storyDto, story);
        return story;
    }

    private StoryDto toDto(Story story) {
        StoryDto storyDto = new StoryDto();
        BeanUtils.copyProperties(story, storyDto);
        return storyDto;
    }

}
