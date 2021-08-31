package com.caito.blogjava.service.implementation;

import com.caito.blogjava.constatnts.ConstantExeptionMessages;
import com.caito.blogjava.dto.NewUserMessage;
import com.caito.blogjava.dto.UserMessgeResponse;
import com.caito.blogjava.entity.UserMessage;
import com.caito.blogjava.exceptions.customs.BadRequestException;
import com.caito.blogjava.repository.UserMessageRepository;
import com.caito.blogjava.service.IUserMessageService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserMessageService implements IUserMessageService {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Override
    public UserMessgeResponse create(NewUserMessage messge) {
        if (messge.getEmail() == null || messge.getEmail() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_MESSAGE_EMAIL_EMPTY);
        }
        if (messge.getMessage() == null || messge.getMessage() == ""){
            throw new BadRequestException(ConstantExeptionMessages.MSG_MESSAGE_MESSAGE_EMPTY);
        }
        ModelMapper mapper = new ModelMapper();
        UserMessage userMessage = mapper.map(messge, UserMessage.class);
        userMessageRepository.save(userMessage);
        return mapper.map(userMessage, UserMessgeResponse.class);
    }

    @Override
    public UserMessgeResponse view(Long id) throws NotFoundException {
        UserMessage userMessage = userMessageRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_MESSAGE_MESAGE_NOT_FOUND));
        ModelMapper mapper = new ModelMapper();
        UserMessgeResponse response = mapper.map(userMessage, UserMessgeResponse.class);
        return response;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        UserMessage userMessage = userMessageRepository.findById(id).orElseThrow(()-> new NotFoundException(
                ConstantExeptionMessages.MSG_MESSAGE_MESAGE_NOT_FOUND));
        userMessageRepository.deleteById(id);
    }

    @Override
    public Page<UserMessage> viewAll(Pageable pageable) {
        return userMessageRepository.findAll(pageable);
    }


}
