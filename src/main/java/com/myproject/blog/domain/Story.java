package com.myproject.blog.domain;

import com.arychand.base.domain.Updatable;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "app_story")
public class Story extends Updatable {

    @Id
    @Column(name = "id", length = 40)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    @Column(name = "title", length = 1000)
    private String title;

    @Column(name = "summary", length = 5000)
    private String summary;

    @Column(name = "content", length = 1000000)
    private String content;

    @Column(name = "delete_flag")
    private boolean deleteFlag;

    public Story() {

    }

    public Story(String id) {
        setId(id);
    }

}
