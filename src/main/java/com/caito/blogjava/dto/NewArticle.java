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
public final class NewArticle implements Serializable {
    private String title;
    private User user;
    private String content;
}
