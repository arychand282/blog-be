package com.myproject.blog.service.impl;

import com.arychand.base.RecordNotFoundException;
import com.arychand.base.util.SearchUtils;
import com.arychand.base.util.StringUtils;
import com.myproject.blog.domain.Story;
import com.myproject.blog.dto.StorySearchDto;
import com.myproject.blog.repository.StoryRepository;
import com.myproject.blog.service.StoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
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

//    @Override
//    public byte[] uploadImage(MultipartFile image, String alt) {
//        System.out.println("alt: " + alt);
//        System.out.println("image: " + image);
////        try {
////            byte[] fileContent = FileUtils.readFileToByteArray(new File(image.getOriginalFilename()));
////            String encodedString = Base64.getEncoder().encodeToString(fileContent);
////            System.out.println("encodedString: " + encodedString);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        String strBase64 = "VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBlZCBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcGVkIG92ZXIgdGhlIGxhenkgZG9nLg0KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wZWQgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBlZCBvdmVyIHRoZSBsYXp5IGRvZy4NCg0K";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
//        headers.add("content-disposition","inline;filename="+uploadFile.getFileName());
//        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
//        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents,headers, HttpStatus.OK);
//
//        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
//        return decodedBytes;
//    }


}
