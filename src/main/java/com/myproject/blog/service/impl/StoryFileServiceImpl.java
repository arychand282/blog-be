package com.myproject.blog.service.impl;

import com.arychand.base.BusinessException;
import com.arychand.base.RecordNotFoundException;
import com.myproject.blog.domain.StoryFile;
import com.myproject.blog.repository.StoryFileRepository;
import com.myproject.blog.service.FileStorageService;
import com.myproject.blog.service.StoryFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StoryFileServiceImpl implements StoryFileService {

    @Autowired
    private StoryFileRepository storyFileRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public Optional<StoryFile> findById(String id) {
        return storyFileRepository.findById(id);
    }

    @Override
    public StoryFile save(StoryFile storyFile) {
        return storyFileRepository.save(storyFile);
    }

    @Override
    public List<StoryFile> findByStoryId(String storyId) {
        return storyFileRepository.findByStoryId(storyId);
    }

    @Override
    public void delete(String id) {
        StoryFile storyFile = storyFileRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        String fileName = storyFile.getFileName();

        storyFileRepository.deleteById(id);
        List<StoryFile> storyFilesWithSameFileResource = storyFileRepository.findByFileName(fileName);
        if (storyFilesWithSameFileResource.size() == 0) {
            Resource resource = fileStorageService.loadFileResource(storyFile.getFileName());
            try {
                resource.getFile().delete();
            } catch (IOException e) {
                log.error("delete file error ", e);
                throw new BusinessException(e.getMessage());
            }
        }
    }
}
