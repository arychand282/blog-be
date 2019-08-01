package com.myproject.blog.domain;

import com.arychand.base.domain.Updatable;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_story_file")
public class StoryFile {

    @Id
    @Column(name = "id", length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "file_name", length = 100)
    private String fileName;

    @Column(name = "file_download_uri", length = 500)
    private String fileDownloadUri;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "file_size")
    private long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    private Story story;

}
