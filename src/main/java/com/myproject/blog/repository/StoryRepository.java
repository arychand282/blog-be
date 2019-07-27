package com.myproject.blog.repository;

import com.myproject.blog.domain.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends PagingAndSortingRepository<Story, String> {

    Optional<Story> findByIdAndDeleteFlag(String id, boolean deleteFlag);

    Page<Story> findByTitleContainingAndDeleteFlag(String title, boolean deleteFlag, Pageable pageable);

    List<Story> findAllByDeleteFlag(boolean deleteFlag);

}
