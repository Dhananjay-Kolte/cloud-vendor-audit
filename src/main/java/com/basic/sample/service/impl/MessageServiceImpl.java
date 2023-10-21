package com.basic.sample.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.basic.sample.service.AppService;
import com.basic.sample.service.IMessageService;

@Service
public class MessageServiceImpl extends AppService implements IMessageService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(final String code, final String... params) {
    	return this.messageSource.getMessage(code, params, Locale.ENGLISH);
    }
}