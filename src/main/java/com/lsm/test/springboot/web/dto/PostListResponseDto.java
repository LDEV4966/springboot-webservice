package com.lsm.test.springboot.web.dto;

import com.lsm.test.springboot.domain.posts.Posts;

import java.time.LocalDateTime;

public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
