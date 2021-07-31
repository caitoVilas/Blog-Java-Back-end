package com.caito.blogjava.service;

import com.caito.blogjava.dto.NewUserMessage;
import com.caito.blogjava.dto.UserMessgeResponse;
import com.caito.blogjava.entity.UserMessage;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserMessageService {
    public UserMessgeResponse create(NewUserMessage messge);
    public UserMessgeResponse view(Long id) throws NotFoundException;
    public void delete(Long id) throws NotFoundException;
    public Page<UserMessage> viewAll(Pageable pageable);
}
