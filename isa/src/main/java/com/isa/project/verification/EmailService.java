package com.isa.project.verification;

import com.isa.project.model.AppUser;
import com.isa.project.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableAsync
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    @Async
    public void sendNotificaitionAsync(AppUser appUser, String token) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(appUser.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration Confirmation");
        mail.setText("http://localhost:8080/users/activate/" + token);
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    @Async
    public void sendNotificaitionOfApprovedRegistrationRequest(RegistrationRequest request) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(request.getUser().getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setText("Registration Confirmation");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    @Async
    public void sendNotificaitionOfDeclinedRegistrationRequest(RegistrationRequest request) throws MailException, InterruptedException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(request.getUser().getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setText("Your Registration Has Been Declined");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    public void sendNotificaitionAdminReg(AppUser appUser, String password) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(appUser.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setText("You have been successfully registered. Your temporarly password is: "+password+". Please change it on your first login.");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }
}
