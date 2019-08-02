package com.myproject.blog.rest;

import com.arychand.base.BusinessException;
import com.myproject.blog.domain.Story;
import com.myproject.blog.domain.StoryFile;
import com.myproject.blog.dto.StoryDto;
import com.myproject.blog.dto.UploadFileResponseDto;
import com.myproject.blog.service.FileStorageService;
import com.myproject.blog.service.StoryFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private StoryFileService storyFileService;

    @Autowired
    private StoryController storyController;

    @PostMapping(value = "/upload_file")
    public UploadFileResponseDto uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("storyId") String storyId) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/file/download_file/")
                .path(fileName)
                .toUriString();

        StoryFile storyFile = new StoryFile();
        storyFile.setFileName(fileName);
        storyFile.setFileDownloadUri(fileDownloadUri);
        storyFile.setFileType(file.getContentType());
        storyFile.setFileSize(file.getSize());
        storyFile.setStory(new Story(storyId));
        storyFileService.save(storyFile);

        UploadFileResponseDto uploadFileResponseDto = new UploadFileResponseDto();
        uploadFileResponseDto.setFileName(fileName);
        uploadFileResponseDto.setFileDownloadUri(fileDownloadUri);
        uploadFileResponseDto.setFileType(file.getContentType());
        uploadFileResponseDto.setSize(file.getSize());
        return uploadFileResponseDto;
    }

    @PostMapping(value = "/upload_multiple_files")
    public List<UploadFileResponseDto> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,
                                                           @RequestParam("storyId") String storyId) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file, storyId))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/delete_files", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public StoryDto deleteFiles(@RequestBody StoryDto storyDto) {
        try {
            storyDto.getUploadFileResponseDtoList().forEach(uploadFileResponseDto -> {
                storyFileService.delete(uploadFileResponseDto.getId());
            });
            return storyController.findDetail(storyDto.getId());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @GetMapping(value = "/download_file/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.info("Could not determine file type");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
