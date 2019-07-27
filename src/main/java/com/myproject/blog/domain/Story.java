package com.myproject.blog.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "app_story")
public class Story {

    @Id
    @Column(name = "id", length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "title", length = 40)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

    @Column(name = "date_created")
    private OffsetDateTime dateCreated;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

}
