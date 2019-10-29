package com.medbis.mail;

import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Setter
public class MailBox  {

    private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    public static JavaMailSenderImpl getInstance() {
        return mailSender;
    }

    private MailBox(MailCfg cfg) {
        addSettings(cfg);
    }

    private void addSettings(MailCfg mailCfg) {
        mailSender.setUsername(mailCfg.getUsername());
        mailSender.setPassword(mailCfg.getPassword());
        mailSender.setHost(mailCfg.getHost());
        mailSender.setPort(Integer.parseInt(mailCfg.getPort()));
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.starttls.enable", "true");
    }





}
