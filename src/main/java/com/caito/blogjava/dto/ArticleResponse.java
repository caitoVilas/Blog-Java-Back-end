package com.caito.blogjava.dto;

import com.caito.blogjava.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class ArticleResponse implements Serializable {
    private Long id;
    private String title;
    private UserResponse user;
    private String content;
    private String imageURL;
    private String imageID;
}