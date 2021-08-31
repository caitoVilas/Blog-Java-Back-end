package com.caito.blogjava.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
