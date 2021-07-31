package com.caito.blogjava.controller;

import com.caito.blogjava.dto.NewUserMessage;
import com.caito.blogjava.dto.UserMessgeResponse;
import com.caito.blogjava.entity.UserMessage;
import com.caito.blogjava.service.implementation.UserMessageService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-messages")
@CrossOrigin

public class UserMessageController {

    @Autowired
    private UserMessageService userMessageService;

    @PostMapping("/create")
    public ResponseEntity<UserMessgeResponse> create(@RequestBody NewUserMessage message){
        return new ResponseEntity<UserMessgeResponse>(userMessageService.create(message), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserMessage>> viewAll (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String order,
            @RequestParam(defaultValue = "false") boolean asc){


        Page<UserMessage> messages = userMessageService.viewAll(PageRequest.of(
                page,
                size,
                Sort.by(order)
        ));

        if (!asc) {
            messages = userMessageService.viewAll(PageRequest.of(
                    page,
                    size,
                    Sort.by(order).descending()
            ));
        }
        return new ResponseEntity<Page<UserMessage>>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMessgeResponse> get(@PathVariable("id") Long id) throws NotFoundException {
        return new ResponseEntity<UserMessgeResponse>(userMessageService.view(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws NotFoundException {
        userMessageService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
