package com.medbis.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@Getter
@Setter
public class MailDto {
    @NotEmpty
    private String subject;
    @NotEmpty
    private String message;

}