package com.myproject.blog.rest;

import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StoryDto;
import com.myproject.blog.dto.StorySearchDto;
import com.myproject.blog.dto.UploadFileResponseDto;
import com.myproject.blog.service.StoryFileService;
import com.myproject.blog.service.StoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/story")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private StoryFileService storyFileService;

    @GetMapping(value = "/{id}")
    public StoryDto findDetail(@PathVariable String id) throws Exception {
        Optional<Story> storyOptional = storyService.findDetail(id);
        if (storyOptional.isPresent()) {
            Story story = storyOptional.get();
            story.setContent(formattingEmbed(story.getContent()));
            return toDto(story);
        } else {
            throw new Exception("Record Not Found for id: " + id);
        }
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StoryDto save(@RequestBody StoryDto storyDto) {
        return toDto(storyService.save(toModel(storyDto)));
    }

    @GetMapping(value = "")
    public Page<StoryDto> findSearch(StorySearchDto storySearchDto) {
        return storyService.findSearch(storySearchDto).map(this::toDto);
    }

    @GetMapping(value = "/droplist")
    public List<StoryDto> findDroplist() {
        List<StoryDto> storyDtoList = new ArrayList<>();
        storyService.findDroplist().forEach(story -> storyDtoList.add(toDto(story)));
        return storyDtoList;
    }

    @PatchMapping(value = "/")
    public StoryDto update(@RequestBody StoryDto storyDto) {
        return toDto(storyService.update(toModel(storyDto)));
    }

    @DeleteMapping(value = "/{id}")
    public StoryDto delete(@PathVariable String id) {
        return toDto(storyService.delete(id));
    }

    private Story toModel(StoryDto storyDto) {
        Story story = new Story();
        BeanUtils.copyProperties(storyDto, story);
        return story;
    }

    private StoryDto toDto(Story story) {
        StoryDto storyDto = new StoryDto();
        BeanUtils.copyProperties(story, storyDto);

        List<UploadFileResponseDto> uploadFileResponseDtoList = new ArrayList<>();
        storyFileService.findByStoryId(story.getId()).forEach(storyFile -> {
            UploadFileResponseDto uploadFileResponseDto = new UploadFileResponseDto();
            uploadFileResponseDto.setId(storyFile.getId());
            uploadFileResponseDto.setFileName(storyFile.getFileName());
            uploadFileResponseDto.setFileDownloadUri(storyFile.getFileDownloadUri());
            uploadFileResponseDto.setFileType(storyFile.getFileType());
            uploadFileResponseDto.setSize(storyFile.getFileSize());
            uploadFileResponseDtoList.add(uploadFileResponseDto);
        });

        storyDto.setUploadFileResponseDtoList(uploadFileResponseDtoList);
        return storyDto;
    }

    private String formattingEmbed(String detailStory) {
        detailStory = detailStory.replaceAll("<figure class=\"media\"><oembed url=\"", "<iframe src=\"");
        detailStory = detailStory.replaceAll("\"></oembed></figure>", "\" width=\"560\" height=\"315\" frameborder=\"0\" allowfullscreen></iframe>");
        return detailStory;
    }

}
