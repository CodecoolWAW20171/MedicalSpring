package com.medbis.mail;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MailCfg {

//    @Value("${mail.username}")
    private String username;
  //  @Value("${mail.password}")
    private String password;
    //@Value("${mail.host}")
    private String host;
    //@Value("${mail.port}")
    private String port;


    public MailCfg(){
        this.username = "office.medicalspring@gmail.com";
        this.password = "kbks2019";
        this.port = "587";
        this.host = "smtp.gmail.com";

    }

}
